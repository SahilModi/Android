package com.smapps.cs196hackerspace;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        final ListView listView = (ListView) findViewById(R.id.listView);

        Intent myIntent = getIntent();

        ArrayList<Person> people = myIntent.getParcelableArrayListExtra(MainActivity.PEOPLE);

        MyAdapter mAdapter = new MyAdapter(this, people);
        listView.setAdapter(mAdapter);

    }
}
