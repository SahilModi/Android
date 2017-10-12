package com.smapps.cs196hackerspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Key value to pass onto the new activity
    //Global declaration allows MainActivity.EXTRA_EXTRA_STRING call
    public static final String LIST_VIEW_CONTENTS = "List View Contents";
    public static final String LIST_VIEW_TITLE = "List View Title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button myButton = (Button) findViewById(R.id.button);
        final CheckBox myCheckbox = (CheckBox) findViewById(R.id.checkBox);
        final TextView myTextView = (TextView) findViewById(R.id.textView);
        final Switch mySwitch = (Switch) findViewById(R.id.switch1);

        mySwitch.setChecked(true);
        myCheckbox.setChecked(true);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText("Loading...");
                Intent myIntent = new Intent(MainActivity.this, ListViewActivity.class);
                String[] listViewContents = {"String 1", "String 2", "String 3"};
                String listViewTitle = "MY LIST VIEW";
                myIntent.putExtra(LIST_VIEW_CONTENTS, listViewContents);
                myIntent.putExtra(LIST_VIEW_TITLE, listViewTitle);
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

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myButton.setEnabled(true);
                    myTextView.setText(R.string.text_view_default);
                } else {
                    myTextView.setText(R.string.text_view_disabled);
                    myButton.setEnabled(false);
                }
            }
        });

        myCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!mySwitch.isChecked()) {
                        mySwitch.performClick();
                    }
                    mySwitch.setEnabled(true);
                } else {
                    if (mySwitch.isChecked()) {
                        mySwitch.performClick();
                    }
                    mySwitch.setEnabled(false);
                }
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
