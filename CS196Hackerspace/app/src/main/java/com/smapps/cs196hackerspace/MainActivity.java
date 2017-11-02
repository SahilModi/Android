package com.smapps.cs196hackerspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Key value to pass onto the new activity
    //Global declaration allows MainActivity.EXTRA_EXTRA_STRING call
    public static final String PEOPLE = "PEOPLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myButton = (Button) findViewById(R.id.button);
        final Button addPerson = (Button) findViewById(R.id.addButton);
        final EditText nameET = (EditText) findViewById(R.id.name);
        final EditText phoneET = (EditText) findViewById(R.id.phoneN);
        final EditText emailET = (EditText) findViewById(R.id.email);
        final EditText addressET = (EditText) findViewById(R.id.address);

        final ArrayList<Person> people = new ArrayList<>();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListViewActivity.class);
                myIntent.putParcelableArrayListExtra(PEOPLE, people);

                startActivity(myIntent);
            }
        });

        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, "Full Guns Blazing!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                String address = addressET.getText().toString();
                Person newPerson = new Person(name, phone, email, address);
                people.add(newPerson);

                nameET.setText("");
                phoneET.setText("");
                emailET.setText("");
                addressET.setText("");

                Toast.makeText(MainActivity.this, "New Person added.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onPause() { //Activity is no longer visible
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
