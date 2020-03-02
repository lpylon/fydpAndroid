package com.fydp.fundusapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.fydp.fundusapp.Objects.ExamImage;

import java.util.List;

public class ViewExamResultsActivity extends AppCompatActivity {

    List<ExamImage> examImages;
    DatabaseHelper databaseHelper;
    String examId;
    ImageView initialCombinedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam_results);

        Intent intent = getIntent();
        examId = intent.getStringExtra("exam_id");

        initialCombinedImage = findViewById(R.id.left_macula_image);
        ImageView leftOpticNerveImage = findViewById(R.id.left_optic_nerve_image);


        databaseHelper = new DatabaseHelper(this);
        examImages = databaseHelper.getExamImages(examId);

        ExamImage examImage1 = null;

        for(ExamImage examImage : examImages){
            examImage1 = examImage;

            Log.i("examImageLR", examImage.getEyeLR());
            Log.i("examImageSection", examImage.getEyeSection());
        }

        initialCombinedImage.setImageBitmap(examImage1.getCombinedImageData());
        leftOpticNerveImage.setImageBitmap(examImage1.getCombinedImageData());

    }
}
