package com.example.sahil.appbartest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
//import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    private MenuItem tbScan;
    private Button tbBtn;
    private int var1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbScan = (MenuItem)findViewById(R.id.action_scan);

        tbBtn = (Button)findViewById(R.id.tbBtn);
        tbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //changeText();
                invalidateOptionsMenu();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /*public void changeText(){
        //Toast.makeText(MainActivity.this, "Test1", Toast.LENGTH_SHORT).show();
        if(var1==0){
            tbScan.setTitle("Stop");
            Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
            var1 = 1;
        }
        else{
            tbScan.setTitle("Scan");
            Toast.makeText(MainActivity.this, "Scan", Toast.LENGTH_SHORT).show();
            var1 = 0;
        }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(var1==0){
            menu.findItem(R.id.action_scan).setTitle("STOP");
            Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
            var1 = 1;
        }
        else{
            menu.findItem(R.id.action_scan).setTitle("SCAN");
            Toast.makeText(MainActivity.this, "Scan", Toast.LENGTH_SHORT).show();
            var1 = 0;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
