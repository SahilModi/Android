package com.smapps.hemohealth;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    EditText weight, minorDose, majorDose, severeDose;
    TextView minorDoseTotal, majorDoseTotal, severeDoseTotal;
    Spinner weightUnit;
    Switch presetDosage;
    Button submit;

    Float weightKg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        weight = (EditText) findViewById(R.id.weight);
        weightUnit = (Spinner) findViewById(R.id.weightUnits);
        submit = (Button) findViewById(R.id.submitSettings);
        presetDosage = (Switch) findViewById(R.id.presetDosage);
        minorDose = (EditText) findViewById(R.id.minorDose);
        majorDose = (EditText) findViewById(R.id.majorDose);
        severeDose = (EditText) findViewById(R.id.severeDose);
        minorDoseTotal = (TextView) findViewById(R.id.minorDoseTotal);
        majorDoseTotal = (TextView) findViewById(R.id.majorDoseTotal);
        severeDoseTotal = (TextView) findViewById(R.id.severeDoseTotal);

        weightUnit.setSelection(1);
        weight.setText(String.valueOf(preferences.getFloat("Weight", 0)));

        if (preferences.getBoolean("Preset Dosage", false)) {
            presetDosage.setChecked(true);
            findViewById(R.id.dosageTable).setVisibility(View.VISIBLE);
            setAllDoseValues();
        } else {
            presetDosage.setChecked(false);
            findViewById(R.id.dosageTable).setVisibility(View.GONE);
        }

        presetDosage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.dosageTable).setVisibility(View.VISIBLE);
                    editor.putBoolean("Preset Dosage", true);
                    setAllDoseValues();
                } else {
                    findViewById(R.id.dosageTable).setVisibility(View.GONE);
                    editor.putBoolean("Preset Dosage", false);
                }
                editor.apply();
            }
        });

        minorDose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weightKg = convertToKg(weight, weightUnit);
                weight.setText(weightKg.toString());

                int minorDoseVal;
                if (isEmpty(minorDose)) {
                    minorDoseVal = 0;
                } else {
                    minorDoseVal = Integer.parseInt(minorDose.getText().toString());
                }
                editor.putFloat("Weight", weightKg);
                editor.putInt("Minor Dose", minorDoseVal);
                editor.putInt("Minor Dose Total", Math.round(minorDoseVal * weightKg));
                editor.apply();
                minorDoseTotal.setText(preferences.getInt("Minor Dose Total", 0) + " units");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        majorDose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weightKg = convertToKg(weight, weightUnit);
                weight.setText(weightKg.toString());

                int majorDoseVal;
                if (isEmpty(majorDose)) {
                    majorDoseVal = 0;
                } else {
                    majorDoseVal = Integer.parseInt(majorDose.getText().toString());
                }
                editor.putFloat("Weight", weightKg);
                editor.putInt("Major Dose", majorDoseVal);
                editor.putInt("Major Dose Total", Math.round(majorDoseVal * weightKg));
                editor.apply();
                majorDoseTotal.setText(preferences.getInt("Major Dose Total", 0) + " units");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        severeDose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weightKg = convertToKg(weight, weightUnit);
                weight.setText(weightKg.toString());

                int severeDoseVal;
                if (isEmpty(severeDose)) {
                    severeDoseVal = 0;
                } else {
                    severeDoseVal = Integer.parseInt(severeDose.getText().toString());
                }
                editor.putFloat("Weight", weightKg);
                editor.putInt("Severe Dose", severeDoseVal);
                editor.putInt("Severe Dose Total", Math.round(severeDoseVal * weightKg));
                editor.apply();
                severeDoseTotal.setText(preferences.getInt("Severe Dose Total", 0) + " units");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(weight)) {
                    Toast.makeText(Settings.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                } else if (presetDosage.isChecked() && (isEmpty(minorDose) || isEmpty(majorDose))) {
                    Toast.makeText(Settings.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                } else {
                    setAllDoseValues();
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                submit.performClick();
                break;
        }
        return true;
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().length() <= 0;
    }

    public float convertToKg(EditText editText, Spinner unit) {
        if (!isEmpty(editText)) {
            if (unit.getSelectedItemPosition() == 0) {//pounds
                unit.setSelection(1);
                return Double.valueOf(Math.round(Float.parseFloat(editText.getText().toString()) / 2.2 * 100.0) / 100.0).floatValue();
            } else {
                return Double.valueOf(Math.round(Float.parseFloat(editText.getText().toString()) * 100.0) / 100.0).floatValue();
            }
        } else {
            return 0.00f;
        }
    }

    public void setAllDoseValues() {
        minorDose.setText(String.valueOf(preferences.getInt("Minor Dose", 0)));
        majorDose.setText(String.valueOf(preferences.getInt("Major Dose", 0)));
        severeDose.setText(String.valueOf(preferences.getInt("Severe Dose", 0)));
        minorDoseTotal.setText(preferences.getInt("Minor Dose Total", 0) + " units");
        majorDoseTotal.setText(preferences.getInt("Major Dose Total", 0) + " units");
        severeDoseTotal.setText(preferences.getInt("Severe Dose Total", 0) + " units");
    }
}