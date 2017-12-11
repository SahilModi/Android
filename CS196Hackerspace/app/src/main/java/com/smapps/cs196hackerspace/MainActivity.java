package com.smapps.cs196hackerspace;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Key value to pass onto the new activity
    //Global declaration allows MainActivity.EXTRA_EXTRA_STRING call
    public static final String PEOPLE = "PEOPLE";
    static final int REQUEST_IMAGE_CAPTURE = 1; //identify camera intent
    static final int REQUEST_GALLERY_ACCESS = 2; //identify gallery intent
    public static Bitmap cameraImage; //current image
    private ImageView myImageView;

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
                Person newPerson = new Person(name, phone, email, address, cameraImage);
                people.add(newPerson);

                nameET.setText("");
                phoneET.setText("");
                emailET.setText("");
                addressET.setText("");
                myImageView.setImageResource(android.R.drawable.ic_menu_camera);

                Toast.makeText(MainActivity.this, "New Person added.", Toast.LENGTH_SHORT).show();

            }
        });

        /* CAMERA STUFF RIGHT HERE */
        myImageView = (ImageView) findViewById(R.id.cameraImageView);

        checkPermissions(); //checks permissions

        myImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //make camera intent
                if(cameraIntent.resolveActivity(getPackageManager()) != null){ //makes sure camera is available
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE); //start camera
                }
            }
        });

        myImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(galleryIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(galleryIntent, REQUEST_GALLERY_ACCESS); //start Gallery
                }
                return false; //if true then it ALSO TRIGGERS onClick();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.activity2Menu:
                Intent i = new Intent(MainActivity.this, UIActivity.class);
                startActivity(i);
                return true;
            case R.id.activity3Menu:
                Intent j = new Intent(MainActivity.this, FirebaseActivity.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() { //Activity is no longer visible
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //constructor

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras(); // get the image from camera activity
                    cameraImage = (Bitmap) extras.get("data"); //used for putting image into Person object
                    myImageView.setImageBitmap(cameraImage); // set image on main activity
                    break;

                case REQUEST_GALLERY_ACCESS:
                    Uri imageUri = data.getData(); //get image from gallery
                    Bitmap galleryImage;
                    try{
                        galleryImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        galleryImage = Bitmap.createScaledBitmap(galleryImage, 400, 300, false);
                        myImageView.setImageBitmap(galleryImage);
                        cameraImage = galleryImage;
                    } catch (FileNotFoundException notFound){
                            notFound.printStackTrace();
                        Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public void checkPermissions(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 42);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 43);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 44);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 45);
        }


    }
}
