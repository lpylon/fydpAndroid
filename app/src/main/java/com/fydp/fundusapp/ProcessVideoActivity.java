package com.fydp.fundusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;


public class ProcessVideoActivity extends AppCompatActivity {

    String videoPath;
    Button saveVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_video);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra("video_path");

        processImage();
    }

    private void processImage() {



    }
}
