package com.smapps.cs196hackerspace;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class UIActivity extends AppCompatActivity {

    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < 100) {
                    progress++;
                    bar.setProgress(progress);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }); // .start() to start progress bar

        final RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean fromUser) {
                Toast.makeText(UIActivity.this,
                        "Rating: " + value, Toast.LENGTH_SHORT).show();
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> spinnerStrings = new ArrayList<>();
        spinnerStrings.add("Hello");
        spinnerStrings.add("World");
        spinnerStrings.add("Sahil Modi");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerStrings);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UIActivity.this,
                        "Item: " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* AsyncTask Code */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 45);
        }
        String[] urls = {"https://i.redd.it/sszz9kqn1t001.jpg", "https://i.redd.it/ue5crvrqes001.jpg",
                "https://i.imgur.com/oUDO6Dz.jpg", "https://i.redd.it/kxepc7ccft001.jpg", "https://i.redd.it/r74ah1ykft001.png"};
        new GetImagesTask(this).execute(urls);
    }
}
