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

public class MainActivity extends AppCompatActivity {

    //Key value to pass onto the new activity
    //Global declaration allows MainActivity.EXTRA_EXTRA_STRING call
    public static final String LIST_VIEW_CONTENTS = "List View Contents";
    public static final String LIST_VIEW_TITLE = "List View Title";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myButton = (Button) findViewById(R.id.button);
        final EditText nameET = (EditText) findViewById(R.id.name);
        final EditText phoneET = (EditText) findViewById(R.id.phoneN);
        final EditText emailET = (EditText) findViewById(R.id.email);
        final EditText addressET = (EditText) findViewById(R.id.address);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ListViewActivity.class);
                String[] listViewContents = {"String 1", "String 2", "String 3"};
                String listViewTitle = "MY LIST VIEW";
                String name = nameET.getText().toString();
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                String address = addressET.getText().toString();
                myIntent.putExtra(LIST_VIEW_CONTENTS, listViewContents);
                myIntent.putExtra(LIST_VIEW_TITLE, listViewTitle);
                myIntent.putExtra(NAME, name);
                myIntent.putExtra(PHONE, phone);
                myIntent.putExtra(EMAIL, email);
                myIntent.putExtra(ADDRESS, address);
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
