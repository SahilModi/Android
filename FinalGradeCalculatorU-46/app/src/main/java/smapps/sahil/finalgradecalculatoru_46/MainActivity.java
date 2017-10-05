package smapps.sahil.finalgradecalculatoru_46;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText weight;
    private EditText originalGrade;
    private EditText desiredGrade;
    private TextView finalGrade;
    private Button switchBtn;

    float w = 0;
    float oG = 0;
    float dG = 0;
    float w1 = 0;
    float fG = 0;
    double fGRounded = 0;

    int switchVar = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weight = (EditText) findViewById(R.id.Weight);
        originalGrade = (EditText) findViewById(R.id.CurrentGrade);
        desiredGrade = (EditText) findViewById(R.id.GradeWanted);

        finalGrade = (TextView) findViewById(R.id.gradeText);
        switchBtn = (Button) findViewById(R.id.switchBtn);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();

            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchVar = switchVar*-1;
                changescreen();
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    void submit() {

        oG = Float.parseFloat(originalGrade.getText().toString());
        dG = Float.parseFloat(desiredGrade.getText().toString());
        w1 = Float.parseFloat(weight.getText().toString());
        if(switchVar==1) {
            if (w1 > 1) {
                w = w1 / 100;
            }
            if (w != 0) {
                fG = (dG - (oG * (1 - w))) / w;
                fGRounded = Math.round(fG * 100.0) / 100.0;
            }
            finalGrade.setText("Grade Needed: " + fGRounded);
        }
        else if(switchVar==-1){
            if (w1 > 1) {
                w = w1 / 100;
            }
            if (w != 0) {
                fG = (dG*w) + (oG*(1-w));
                fGRounded = Math.round(fG * 100.00) / 100.00;
            }
            finalGrade.setText("Final Grade: " + fGRounded);
        }
    }

    void changescreen(){
        if(switchVar==-1){
            desiredGrade.setHint("Grade Received");
            switchBtn.setText("Final Not Graded?");
            finalGrade.setText("Final Grade: ");
            clear();


        }
        else if(switchVar == 1){
            desiredGrade.setHint("Desired Grade");
            switchBtn.setText("Final Already Graded?");
            finalGrade.setText("Grade Needed: ");
            clear();
        }

    }

    void clear(){
        desiredGrade.setText("");
        originalGrade.setText("");
        weight.setText("");
    }
}

