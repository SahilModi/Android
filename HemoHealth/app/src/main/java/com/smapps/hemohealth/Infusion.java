package com.smapps.hemohealth;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class Infusion extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner medicine, doseSelector;
    EditText date, time, doseTaken, notes;
    Button importImage, takeImage;
    ImageView imageView, imageView2, imageView3, imageView4;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateSet;

    TimePickerDialog.OnTimeSetListener timeSet;

    static final int REQUEST_IMAGE_CAPTURE = 1, SELECT_PHOTO = 2;
    int dosage;

    ArrayList<Bitmap> images = new ArrayList<>();
    ArrayList<ImageView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infusion);

        medicine = (Spinner) findViewById(R.id.medicine);
        doseSelector = (Spinner) findViewById(R.id.doseSelector);
        doseTaken = (EditText) findViewById(R.id.doseTaken);
        notes = (EditText) findViewById(R.id.notes);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        importImage = (Button) findViewById(R.id.importImage);
        takeImage = (Button) findViewById(R.id.takeImage);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        imageViews.add(imageView);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);

        medicine.setSelection(preferences.getInt("Medicine", 0)); //sets default medicine
        date.setText(preferences.getString("Date", "01/01/0000"));
        time.setText(preferences.getString("Time", "00:00 AM"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("Medicine", medicine.getSelectedItemPosition());
                editor.putInt("Dose Taken", Integer.parseInt(doseTaken.getText().toString()));

                editor.apply();
                finish();
            }
        });

        myCalendar = Calendar.getInstance();

        dateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editor.putString("Date", String.format("%d/%d/%d", month, dayOfMonth, year));
                editor.apply();
                date.setText(preferences.getString("Date", "01/01/0000"));
            }
        };
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    date.setShowSoftInputOnFocus(false);
                    new DatePickerDialog(Infusion.this, dateSet, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        timeSet = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                editor.putString("Time", String.format("%d:%02d %s", hourOfDay % 12, minute, (hourOfDay >= 12) ? "PM" : "AM"));
                editor.apply();
                time.setText(preferences.getString("Time", "00:00 AM"));
            }
        };
        time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    time.setShowSoftInputOnFocus(false);
                    new TimePickerDialog(Infusion.this, timeSet, myCalendar.get(Calendar.HOUR_OF_DAY),
                            myCalendar.get(Calendar.MINUTE), false).show();
                }
                return false;
            }
        });

        doseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doseTaken.setVisibility(View.VISIBLE);
                if (position != 3) {
                    doseTaken.setFocusable(false);
                } else {
                    doseTaken.setFocusableInTouchMode(true);
                    doseTaken.requestFocusFromTouch();
                }
                switch (position) {
                    case 0:
                        doseTaken.setText(String.valueOf(preferences.getInt("Minor Dose Total", 0)));
                        break;
                    case 1:
                        doseTaken.setText(String.valueOf(preferences.getInt("Major Dose Total", 0)));
                        break;
                    case 2:
                        doseTaken.setText(String.valueOf(preferences.getInt("Severe Dose Total", 0)));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        importImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, SELECT_PHOTO);
            }
        });
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    //imageBitmap = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth()/2, imageBitmap.getHeight()/2, true);
                    setImages(imageBitmap);
                    break;
                case SELECT_PHOTO:
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        selectedImage = Bitmap.createScaledBitmap(selectedImage, selectedImage.getWidth() / 2, selectedImage.getHeight() / 2, true);
                        setImages(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    void setImages(Bitmap newImage) {
        images.add(newImage);
        if(images.size() > 4){images.remove(0);}
        for (int i = 0; i < images.size(); i++) {
            imageViews.get(i).setImageBitmap(images.get(i));
        }
    }
}