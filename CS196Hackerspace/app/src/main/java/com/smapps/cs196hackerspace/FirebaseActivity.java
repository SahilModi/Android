package com.smapps.cs196hackerspace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, world!");
        final TextView tv = (TextView) findViewById(R.id.fbTV);
        final EditText et = (EditText) findViewById(R.id.fbET);
        final Button bt = (Button) findViewById(R.id.fbBT);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("DATABASE", "VALUE IS: " + value);
                tv.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DATABASE ERROR", "Failed to read value.", databaseError.toException());
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = et.getText().toString();
                myRef.setValue(text);
            }
        });
    }
}
