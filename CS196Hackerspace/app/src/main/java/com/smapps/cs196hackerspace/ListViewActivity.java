package com.smapps.cs196hackerspace;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        final TextView textView = (TextView) findViewById(R.id.textView);
        final ListView listView = (ListView) findViewById(R.id.listView);

        Intent myIntent = getIntent();
        String listViewTitle = myIntent.getStringExtra(MainActivity.LIST_VIEW_TITLE);
        String[] listViewContents = myIntent.getStringArrayExtra(MainActivity.LIST_VIEW_CONTENTS);

        textView.setText(listViewTitle);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                listViewContents));
    }
}
