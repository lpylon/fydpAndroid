package com.fydp.fundusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fydp.fundusapp.Objects.Patient;

//import org.opencv.video.Video;

public class EyeExamActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageButton rightOpticNerve;
    ImageButton rightMacula;
    ImageButton leftOpticNerve;
    ImageButton leftMacula;
    Button processButton;
    String sendPath;


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
                //Intent rightON = new Intent(this, VideoActivity.class);
                AlertDialog.Builder alertadd = new AlertDialog.Builder(this);
                LayoutInflater factory = LayoutInflater.from(this);
                final View view = factory.inflate(R.layout.alert_dialog_with_image, null);



                alertadd.setView(view);

                TextView dialogText = view.findViewById(R.id.dialog_text);
                ImageView dialogImage = view.findViewById(R.id.dialog_imageview);
                dialogText.setText("Align with patient's right eye:");
                alertadd.setPositiveButton("Ready!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        //TODO Do something here
                        Intent rigthM = new Intent(getApplicationContext(), VideoActivity.class);
                        startActivityForResult(rigthM, 0);
                    }
                });

                alertadd.show();

                //startActivityForResult(rightON, 0);
                break;
            case R.id.right_macula:
                //Intent rightON = new Intent(this, VideoActivity.class);
                AlertDialog.Builder alertadd2 = new AlertDialog.Builder(this);
                LayoutInflater factory2 = LayoutInflater.from(this);
                final View view2 = factory2.inflate(R.layout.alert_dialog_with_image, null);



                alertadd2.setView(view2);

                TextView dialogText2 = view2.findViewById(R.id.dialog_text);
                ImageView dialogImage2 = view2.findViewById(R.id.dialog_imageview);
                dialogText2.setText("Align with patient's right eye:");
                alertadd2.setPositiveButton("Ready!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        //TODO Do something here
                        Intent rigthM = new Intent(getApplicationContext(), VideoActivity.class);
                        startActivityForResult(rigthM, 1);
                    }
                });

                alertadd2.show();

                break;

            case R.id.left_optic_nerve:
                AlertDialog.Builder alertadd3 = new AlertDialog.Builder(this);
                LayoutInflater factory3 = LayoutInflater.from(this);
                final View view3 = factory3.inflate(R.layout.alert_dialog_with_image, null);

                alertadd3.setView(view3);

                TextView dialogText3 = view3.findViewById(R.id.dialog_text);
                ImageView dialogImage3 = view3.findViewById(R.id.dialog_imageview);
                dialogText3.setText("Align with patient's left eye:");
                alertadd3.setPositiveButton("Ready!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        //TODO Do something here
                        Intent rigthM = new Intent(getApplicationContext(), VideoActivity.class);
                        startActivityForResult(rigthM, 2);
                    }
                });

                alertadd3.show();



                //Intent leftON = new Intent(this, VideoActivity.class);
                //startActivityForResult(leftON, 2);
                break;
            case R.id.left_macula:
                AlertDialog.Builder alertadd4 = new AlertDialog.Builder(this);
                LayoutInflater factory4 = LayoutInflater.from(this);
                final View view4 = factory4.inflate(R.layout.alert_dialog_with_image, null);



                alertadd4.setView(view4);

                TextView dialogText4 = view4.findViewById(R.id.dialog_text);
                ImageView dialogImage4 = view4.findViewById(R.id.dialog_imageview);
                dialogText4.setText("Align with patient's left eye:");
                alertadd4.setPositiveButton("Ready!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {
                        //TODO Do something here
                        Intent rigthM = new Intent(getApplicationContext(), VideoActivity.class);
                        startActivityForResult(rigthM, 3);
                    }
                });

                alertadd4.show();
                break;

            case R.id.process:
                Intent process = new Intent(this, ProcessVideoActivity.class);
                Intent intent = getIntent();
                Log.i("Intnet", "intent:" + intent.getStringExtra("result"));
                process.putExtra("video_path", intent.getStringExtra("result"));
                startActivity(process);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                rightOpticNerve.setImageResource(R.drawable.image_completed_rough);
                Intent intent = getIntent();
                sendPath = intent.getStringExtra("video_path");
                Log.i("Laurel", "sendPath:" +sendPath);


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
