package com.fydp.fundusapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class VideoReviewActivity extends AppCompatActivity implements View.OnClickListener {
    VideoView videoView;
    Button retakeButton;
    Button acceptAndProcessButton;
    String videoPath;

    boolean videoPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_review);


        Intent intent = getIntent();
        videoPath = intent.getStringExtra("video_path");

        videoView = findViewById(R.id.VideoView);
        videoView.setVideoPath(videoPath);
        videoView.setOnClickListener(this);
        videoView.canPause();
        videoView.start();

        retakeButton = findViewById(R.id.retake_button);
        retakeButton.setOnClickListener(this);
        acceptAndProcessButton = findViewById(R.id.process_button);
        acceptAndProcessButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VideoView: //TODO check out what is wrong with this onclick listener
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                else {
                    videoView.resume();
                }
                break;
            case R.id.retake_button:
                showAreYouSureMessage();
                break;
            case R.id.process_button:
                Intent processVideoIntent = new Intent(this, ProcessVideoActivity.class);
                processVideoIntent.putExtra("video_path", videoPath);
                startActivity(processVideoIntent);
                break;
        }
    }

    private void showAreYouSureMessage(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Are you sure you want to retake?");
        builder1.setMessage("If you selecte yes, the current video will be deleted and this cannot be undone.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes, retake.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent retakeVideoActivity = new Intent(getApplicationContext(), VideoActivity.class);
                        //TODO add video location path to intent
                        startActivity(retakeVideoActivity);
                    }
                });

        builder1.setNegativeButton(
                "No.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //TODO delete the previous video
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
