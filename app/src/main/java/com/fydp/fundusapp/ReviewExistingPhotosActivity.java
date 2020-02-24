package com.fydp.fundusapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fydp.fundusapp.Objects.ExamImage;
import com.fydp.fundusapp.Objects.Patient;

public class ReviewExistingPhotosActivity extends AppCompatActivity {
    ImageView image;
    Patient currentPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_existing_photos);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE);
        String patientId = prefs.getString(MainActivity.PATIENT_ID, "");
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());//TODO probably save this context
        currentPatient = databaseHelper.getPatient(patientId);

        image = findViewById(R.id.image_test);


        if(currentPatient!=null) {
            ExamImage examImage = databaseHelper.getExamImage(patientId);
            Log.i("EyeLR", examImage.getEyeLR());
            Log.i("EyeSection", examImage.getEyeSection());
            Log.i("ExaminerID", examImage.getExaminerId());
            Log.i("Bitmap", examImage.getCombinedImageData().toString());

            Bitmap imageBitmap = examImage.getCombinedImageData();
            image.setImageBitmap(imageBitmap);
        }

    }


}
