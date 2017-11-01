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

        final TextView listTitleTV = (TextView) findViewById(R.id.listTitleTextView);
        final ListView listView = (ListView) findViewById(R.id.listView);

        Intent myIntent = getIntent();
        String listViewTitle = myIntent.getStringExtra(MainActivity.LIST_VIEW_TITLE);
        String[] contents = myIntent.getStringArrayExtra(MainActivity.LIST_VIEW_CONTENTS);
        String name = myIntent.getStringExtra(MainActivity.NAME);
        String phone = myIntent.getStringExtra(MainActivity.PHONE);
        String email = myIntent.getStringExtra(MainActivity.EMAIL);
        String address = myIntent.getStringExtra(MainActivity.ADDRESS);


        ArrayList<String> listViewContents = new ArrayList<>();
        //listViewContents.addAll(Arrays.asList(contents));
        listViewContents.add(name);
        listViewContents.add(phone);
        listViewContents.add(email);
        listViewContents.add(address);


        listTitleTV.setText(listViewTitle);
        listTitleTV.setTypeface(Typeface.DEFAULT_BOLD);

        MyAdapter mAdapter = new MyAdapter(this, listViewContents);
        listView.setAdapter(mAdapter);

    }
}
