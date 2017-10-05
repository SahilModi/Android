package com.smapps.cs196hackerspace;

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

    int count = 0; //test

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
                count++;
                myTextView.setText("You have clicked the button " + count + " times!");
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
                    count = 0;
                }
            }
        });

        myCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(!mySwitch.isChecked()){
                        mySwitch.performClick();
                    }
                    mySwitch.setEnabled(true);
                } else {
                    if(mySwitch.isChecked()){
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
