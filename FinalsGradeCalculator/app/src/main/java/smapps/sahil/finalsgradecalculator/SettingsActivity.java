package smapps.sahil.finalsgradecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Switch changeLayout, selectionColors, allColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        changeLayout = (Switch) findViewById(R.id.changeLayout);
        selectionColors = (Switch) findViewById(R.id.selectionColors);
        allColors = (Switch) findViewById(R.id.allColors);

        if(preferences.getInt("Mode", 1) == 1){
            changeLayout.setChecked(true); //change based on state
        } else{
            changeLayout.setChecked(false); //change based on state
        }

        if(preferences.getBoolean("dSelectionColors", false) || preferences.getBoolean("dAllColors", false)){
            selectionColors.setChecked(true);
        } else {
            selectionColors.setChecked(false);
        }

        if(preferences.getBoolean("dAllColors", false)){
            allColors.setChecked(true);
        } else {
            allColors.setChecked(false);
        }

        changeLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putInt("Mode", 0);
                }
                else{
                    editor.putInt("Mode", 1);
                }
                editor.apply();
            }
        });

        selectionColors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("dSelectionColors", true);
                } else {
                    allColors.setChecked(false);
                    editor.putBoolean("dSelectionColors", false);
                }
                editor.apply();
            }
        });

        allColors.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectionColors.setChecked(true);
                    editor.putBoolean("dAllColors", true);
                } else {
                    editor.putBoolean("dAllColors", false);
                }
                editor.apply();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}