package com.fydp.fundusapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fydp.fundusapp.Objects.Patient;

//import org.opencv.video.Video;

public class EyeExamActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageButton rightOpticNerve;
    ImageButton rightMacula;
    ImageButton leftOpticNerve;
    ImageButton leftMacula;
    Button processButton;


    private Patient currentPatient;
    private TextView patientNameEditText;
    private TextView patientPhoneNumberEditText;
    private TextView patientDOBEdiitText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_exam);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE);
        String patientId = prefs.getString(MainActivity.PATIENT_ID, "");
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());//TOdo probably save this context
        currentPatient = databaseHelper.getPatient(patientId);

        patientNameEditText = findViewById(R.id.patient_name);
        patientPhoneNumberEditText = findViewById(R.id.phone_number);
        patientDOBEdiitText = findViewById(R.id.dob);

        if(currentPatient!=null) {
            patientNameEditText.setText(currentPatient.getFirstName() + " " + currentPatient.getLastName());
            patientPhoneNumberEditText.setText(currentPatient.getPhoneNumber());
            patientDOBEdiitText.setText(currentPatient.getDateOfBirth());
        }

        rightOpticNerve = findViewById(R.id.right_optic_nerve);
        rightMacula = findViewById(R.id.right_macula);
        leftOpticNerve = findViewById(R.id.left_optic_nerve);
        leftMacula = findViewById(R.id.left_macula);
        processButton = findViewById(R.id.process);

        rightOpticNerve.setOnClickListener(this);
        rightMacula.setOnClickListener(this);
        leftOpticNerve.setOnClickListener(this);
        leftMacula.setOnClickListener(this);
        processButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_optic_nerve:
                Intent rightON = new Intent(this, VideoActivity.class);
                startActivityForResult(rightON, 0);
                break;
            case R.id.right_macula:
                Intent rigthM = new Intent(this, VideoActivity.class);
                startActivityForResult(rigthM, 1);
                break;

            case R.id.left_optic_nerve:
                Intent leftON = new Intent(this, VideoActivity.class);
                startActivityForResult(leftON, 2);
                break;
            case R.id.left_macula:
                Intent leftM = new Intent(this, VideoActivity.class);
                startActivityForResult(leftM, 3);
                break;

            case R.id.process:
                Intent process = new Intent(this, ProcessVideoActivity.class);
                startActivity(process);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                rightOpticNerve.setImageResource(R.drawable.image_completed_rough);
                //String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        else if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                rightMacula.setImageResource(R.drawable.image_completed_rough);
                //String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                leftOpticNerve.setImageResource(R.drawable.image_completed_rough);
                //String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == 3) {
            if(resultCode == Activity.RESULT_OK){
                leftMacula.setImageResource(R.drawable.image_completed_rough);
                //String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
