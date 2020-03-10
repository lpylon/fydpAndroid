package com.fydp.fundusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.warnyul.android.widget.FastVideoView;

import java.io.IOException;

public class VideoReviewActivity extends AppCompatActivity implements View.OnClickListener {
    //VideoView videoView;
    FastVideoView videoView;
    //TextureView textureView;
    private MediaPlayer mediaPlayer;
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


        /*

        textureView = findViewById(R.id.playback_video);


        try {
            mediaPlayer.setSurface(textureView);
            mediaPlayer.setDataSource(videoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        MediaController_2 mediaController = new MediaController_2(this);
        mediaController.setAnchorView(videoView);
       // mediaController.requestFocus();

        videoView = findViewById(R.id.video2);
        videoView.setVideoPath(videoPath);
        videoView.setRotation(90);
        videoView.setMediaController(mediaController);

        videoView.setMediaController(mediaController);


        videoView.setOnClickListener(this);
        videoView.canPause();
        videoView.start();


        //mediaController.show();



        retakeButton = findViewById(R.id.retake_button);
        retakeButton.setOnClickListener(this);
        acceptAndProcessButton = findViewById(R.id.process_button);
        acceptAndProcessButton.setOnClickListener(this);
    }

    public class MediaController_2 extends MediaController{
        public MediaController_2(Context context) {
            super(context);
        }
        public void hide() {
        }
        public void hidecontroller()    {
            super.hide();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video2: //TODO check out what is wrong with this onclick listener
                //if(videoView.isPlaying()){
                //    videoView.pause();
                //}
                //else {
                //    videoView.resume();
                //}
                break;
            case R.id.retake_button:
                showAreYouSureMessage();
                break;
            case R.id.process_button:
                Intent returnIntent = new Intent(this, EyeExamActivity.class);
                Log.i("LAUREL", "intent:" +videoPath.toString());
                returnIntent.putExtra("video_path",videoPath);
                setResult(Activity.RESULT_OK,returnIntent);
                //startActivity()
                finish();

                //Intent processVideoIntent = new Intent(this, ProcessVideoActivity.class);
                //processVideoIntent.putExtra("video_path", videoPath);
                //startActivity(processVideoIntent);
                break;
        }
    }

    private void showAreYouSureMessage(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Are you sure you want to retake?");
        builder1.setMessage("If you select yes, the current video will be deleted and this cannot be undone.");
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
