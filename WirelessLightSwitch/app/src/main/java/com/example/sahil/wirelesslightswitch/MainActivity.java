package com.example.sahil.wirelesslightswitch;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address;

    private BluetoothAdapter myBluetoothAdapter;
    ListView pairedList;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayAdapter<String> BTArrayAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private String BTdevice;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    private Button onBtn;
    private Button offBtn;
    private FloatingActionButton fab;

    private int connectVar = 0;

    String newName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title); //Deletes Title on toolbar
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(connectVar == 1) {
                    try {
                        btSocket.close();
                    } catch (IOException e2) {
                        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
                    }
                    disconnect();
                }
                else{
                    Toast.makeText(MainActivity.this, "No Device Connected",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth",
                    Toast.LENGTH_LONG).show();
            MainActivity.this.finish();
        } else if (!myBluetoothAdapter.isEnabled()) {
            Toast.makeText(MainActivity.this, "Please turn on Bluetooth",
                    Toast.LENGTH_SHORT).show();
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is ON",
                    Toast.LENGTH_SHORT).show();
        }

        pairedList = (ListView) findViewById(R.id.pairedListView);
        BTArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        pairedList.setAdapter(BTArrayAdapter);
        buildList();

        pairedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (connectVar == 0) {
                    BTdevice = BTArrayAdapter.getItem(position); //takes data from ListView
                    Scanner scanner = new Scanner(BTdevice);
                    while (scanner.hasNextLine()) {
                        address = scanner.nextLine();
                    }
                    scanner.close();//gets 2nd row from string BTDevice
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Connect")
                            .setMessage("Connect to " + address + "?")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    connectToServer();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .show();
                }
            }
        });

        pairedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                final EditText input = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Rename")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                newName = input.getEditableText().toString();
                                Toast.makeText(MainActivity.this, newName, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            }
        });

        onBtn = (Button) findViewById(R.id.onBtn);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Turning Light ON", Toast.LENGTH_SHORT).show();
                sendData("1");
            }
        });

        offBtn = (Button) findViewById(R.id.offBtn);
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Turning Light OFF", Toast.LENGTH_SHORT).show();
                sendData("0");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if (myBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplicationContext(), "Bluetooth is ON",
                        Toast.LENGTH_SHORT).show();
               buildList();
            } else {
                MainActivity.this.finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_end:

                myBluetoothAdapter.cancelDiscovery();
                myBluetoothAdapter.disable();
                MainActivity.this.finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void connectToServer() {
        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = myBluetoothAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address (obtained above)
        //   A Service ID or UUID
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        myBluetoothAdapter.cancelDiscovery();

            // Establish the connection.  This will block until it connects.
            try {

                btSocket.connect();

                Toast.makeText(MainActivity.this, "Connected",
                        Toast.LENGTH_SHORT).show();
                onBtn.setEnabled(true); //CONNECTION ESTABLISHED
                onBtn.setVisibility(View.VISIBLE);
                offBtn.setEnabled(true);
                offBtn.setVisibility(View.VISIBLE);
                fab.setImageResource(R.drawable.ic_bluetooth_connected_white_24dp);
                connectVar = 1;
                pairedList.setVisibility(View.GONE);
                pairedList.setEnabled(false);


        } catch (IOException e) {
            try {
                btSocket.close();
                Toast.makeText(MainActivity.this, "Connection Failed",
                        Toast.LENGTH_LONG).show();
                fab.setImageResource(R.drawable.ic_bluetooth_searching_white_24dp);
            } catch (IOException e2) {
                errorExit("Fatal Error", "Unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
        }
    }


    private void errorExit(String title, String message) {
        Toast msg = Toast.makeText(MainActivity.this,
                title + " - " + message, Toast.LENGTH_SHORT);
        msg.show();
        connectVar = 0;
        finish();
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            //String msg = "Exception occurred during write: " + e.getMessage();
            //errorExit("Failed To Send", msg);
            disconnect();
        }
    }
    
    private void disconnect(){
        connectVar = 0;
        onBtn.setEnabled(false);
        onBtn.setVisibility(View.INVISIBLE);
        offBtn.setEnabled(false);
        offBtn.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.ic_bluetooth_searching_white_24dp);
        buildList();
        Toast.makeText(MainActivity.this, "Disconnected",
                Toast.LENGTH_LONG).show();
    }
    
    private void buildList(){
        pairedDevices = myBluetoothAdapter.getBondedDevices();
        BTArrayAdapter.clear();
        for (BluetoothDevice device : pairedDevices) {
            BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());}
        pairedList.setVisibility(View.VISIBLE);
        pairedList.setEnabled(true);
    }

}