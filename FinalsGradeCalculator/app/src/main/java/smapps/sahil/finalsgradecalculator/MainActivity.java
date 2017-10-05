package smapps.sahil.finalsgradecalculator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText weight, originalGrade;
    private Spinner desiredGrade;
    private TextView gradeDesiredTV, finalGrade;
    private LinearLayout currentGradeLL, weightLL, desiredGradeLL;

    float w = 0, oG = 0, dG = 0, fG = 0;
    double fGRounded = 0;

    int backgroundColor = Color.parseColor("#FAFAFA"), accentColor;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accentColor = ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        weight = (EditText) findViewById(R.id.Weight);
        originalGrade = (EditText) findViewById(R.id.currentGrade);
        desiredGrade = (Spinner) findViewById(R.id.dropdown);
        gradeDesiredTV = (TextView) findViewById(R.id.gradeDesiredTV);
        currentGradeLL = (LinearLayout) findViewById(R.id.currentGradeLL);
        weightLL = (LinearLayout) findViewById(R.id.weightLL);
        desiredGradeLL = (LinearLayout) findViewById(R.id.gradeDesiredLL);
        finalGrade = (TextView) findViewById(R.id.gradeText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.getInt("Mode", 1) == 1) {
                    submit();
                }
                else{
                    changeScreen();
                }
            }
        });

        weight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_ENTER:
                            submit();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    weightLL.setBackgroundColor(backgroundColor);
                }
                else{
                    setAllBackgroundColor(backgroundColor);
                    animateBackgroundColor(weightLL);
                }
            }
        });

        originalGrade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    currentGradeLL.setBackgroundColor(backgroundColor);
                }
                else{
                    setAllBackgroundColor(backgroundColor);
                    animateBackgroundColor(currentGradeLL);
                }
            }
        });

        desiredGrade.setFocusableInTouchMode(true);
        desiredGrade.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    desiredGradeLL.setBackgroundColor(backgroundColor);
                }
                else{
                    setAllBackgroundColor(backgroundColor);
                    animateBackgroundColor(desiredGradeLL);
                    desiredGrade.performClick();
                }
            }
        });

        if(preferences.getInt("Mode", 1) == 1){
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_done_all_white_24dp));
        }
        else{
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_swap_horiz_white_24dp));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(preferences.getInt("Mode", 1) == 1){
            menu.findItem(R.id.menu_submit).setIcon(R.drawable.ic_swap_horiz_white_24dp);
        }
        else{
            menu.findItem(R.id.menu_submit).setIcon(R.drawable.ic_done_all_white_24dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_submit) {
            if(preferences.getInt("Mode", 1) == 1){
                changeScreen();
            }
            else {
                submit();
            }
            return true;
        }
        if(id == R.id.action_settings){
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void submit() {
        String oGText = originalGrade.getText().toString();
        String dGText = desiredGrade.getSelectedItem().toString().substring(3, 7);
        String wText = weight.getText().toString();

        if (oGText.matches("") || dGText.matches("") || wText.matches("")) {
            finalGrade.setText(R.string.Incomplete);
        } else {
            hideKeyboard();
            animateAllBackgroundColor(accentColor);

            oG = Float.parseFloat(oGText);
            dG = Float.parseFloat(dGText);
            w = Float.parseFloat(wText);
            if (gradeDesiredTV.getText().equals(getResources().getString(R.string.GradeDesired))) {
                if (w > 1) {
                    w = w / 100;
                }
                fG = (dG - (oG * (1 - w))) / w;
               // fGRounded = Math.ceil(fG);
                fGRounded = Math.round(fG * 100.00) / 100.00; //uncomment code above for rounded results
                finalGrade.setText("Grade Needed: " + fGRounded);
            } else {

                if (w > 1) {
                    w = w / 100;
                }
                fG = (dG * w) + (oG * (1 - w));
                fGRounded = Math.round(fG * 100.00) / 100.00;

                finalGrade.setText("Final Grade: " + fGRounded);
            }
        }
    }

    void changeScreen() {
        hideKeyboard();
        animateAllBackgroundColor(backgroundColor);
        if(gradeDesiredTV.getText().equals(getResources().getString(R.string.GradeDesired))){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.dropdownArray2, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            desiredGrade.setAdapter(adapter);

            gradeDesiredTV.setText(R.string.GradeReceived);
            finalGrade.setText(R.string.finalText);
            clear();
        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.dropdownArray, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            desiredGrade.setAdapter(adapter);

            gradeDesiredTV.setText(R.string.GradeDesired);
            finalGrade.setText(R.string.gradeText);
            clear();
        }

    }

    void clear() {
        desiredGrade.setSelection(0);
        originalGrade.setText("");
        weight.setText("");
    }

    void animateBackgroundColor(final LinearLayout LL) {
        if (!preferences.getBoolean("dSelectionColors", false)) {
            int colorFrom = backgroundColor;
            int colorTo = accentColor;
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(500); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    LL.setBackgroundColor((int) animator.getAnimatedValue());
                }
            });
            colorAnimation.start();
        }
    }

    void animateAllBackgroundColor(int color){
        final View activity = findViewById(R.id.gradeDesiredLL).getRootView();
        final ColorDrawable bColor = (ColorDrawable) activity.getBackground();
        int colorFrom, colorTo;

        if(color == accentColor) {
            colorFrom = backgroundColor;
            colorTo = color;
        } else{
            colorTo = color;
            if (bColor.getColor() == backgroundColor) {
                colorFrom = backgroundColor;
            }
            else {
                colorFrom = accentColor;
            }
        }
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(500); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (int) animator.getAnimatedValue();
                if(!preferences.getBoolean("dAllColors", false)) {
                    weightLL.setBackgroundColor(value);
                    desiredGradeLL.setBackgroundColor(value);
                    currentGradeLL.setBackgroundColor(value);
                    activity.setBackgroundColor(value);
                }
            }
        });
        colorAnimation.start();
    }

    public void setAllBackgroundColor(int color){
        if(preferences.getBoolean("dSelectionColors", false)) {
            weightLL.setBackgroundColor(backgroundColor);
            desiredGradeLL.setBackgroundColor(backgroundColor);
            currentGradeLL.setBackgroundColor(backgroundColor);
        } else {
            weightLL.setBackgroundColor(color);
            desiredGradeLL.setBackgroundColor(color);
            currentGradeLL.setBackgroundColor(color);
        }
        if(preferences.getBoolean("dAllColors", false)) {
            findViewById(R.id.gradeDesiredLL).getRootView().setBackgroundColor(backgroundColor);
        }
        else{
            findViewById(R.id.gradeDesiredLL).getRootView().setBackgroundColor(color);
        }
    }

    void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }
}