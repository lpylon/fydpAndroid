package com.fydp.fundusapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
//import org.opencv.core.Core;
//import org.opencv.imgproc.Imgproc;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import java.io.File;


public class ProcessVideoActivity extends AppCompatActivity {

    String videoPath;
    Button saveVideo;
    Mat imageMat;
    VideoCapture cap;

    static {
//        System.loadLibrary("opencv_java4");
        OpenCVLoader.initDebug();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_video);

        Intent intent = getIntent();
        videoPath = intent.getStringExtra("video_path"); //TODO actually send a path
        //videoPath = new File(getFilesDir() + "/" + "testVid1.mp4").getPath();

        //videoPath = getFilesDir().getAbsolutePath() +"/" + "testVid2.avi";
        videoPath = getFilesDir().getAbsolutePath() +"/" + "testVid1.mp4";

        imageMat=new Mat();
        Log.i("videoPath", videoPath);

        processImage();

       // imageMat=new Mat();
        /*
        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
*/

        //Log.i("OpenCV", "OpenCV loaded successfully");
        //imageMat=new Mat();
        //cap = new VideoCapture();
        /*
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);

        }
        */


        //processImage();
    }

    @Override
    public void onResume() {

        super.onResume();

        /*
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        */

    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    imageMat=new Mat();
                    cap = new VideoCapture();
                    processImage();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    private void processImage() {

        //VideoCapture cap = new VideoCapture();

        //VideoCapture cap = new VideoCapture(videoPath);
        //cap.open(videoPath);
        //Mat frame = new Mat();

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        Bitmap bitmap = retriever.getFrameAtTime(100000000);


        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.threshold(mat, mat, 120, 255,Imgproc.THRESH_BINARY);


        Utils.matToBitmap(mat, bitmap);
        ImageView testImage = (ImageView) findViewById(R.id.test_image);
        testImage.setImageBitmap(bitmap);

        Long videoLength = Long.parseLong(time);



        Log.i("LAUREL", videoLength.toString());


        /*
        FrameGrabber videoGrabber = new FFmpegFrameGrabber(videoPath);
        videoGrabber.setFormat("mp4");//mp4 for example
        try {
            videoGrabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        */


        /*
        int frame_number=0;
        if (cap.isOpened())
        {
            while(cap.read(imageMat)) //the last frame of the movie will be invalid. check for it !
            {
                Log.i("HEY", "LAUREL");
                //Imgcodecs.imwrite(hey + "/" + frame_number +".jpg", frame);
                frame_number++;
            }
            cap.release();
        }
        */





        //String videoPath = new File(getFilesDir() + "/" + "testVid1.mp4").getPath();

        //FrameGrabber videoGrabber = new FFmpegFrameGrabber(videoPath);


        /*
        FrameGrabber videoGrabber = new FFmpegFrameGrabber(videoPath);

        try
        {
            videoGrabber.setFormat("mp4");//mp4 for example
            videoGrabber.start();
        } catch (FrameGrabber.Exception e)
        {
            Log.e("javacv", "Failed to start grabber" + e);
            //return -1;
        }
        */


        /*
        try
        {
            videoGrabber.setFormat("mp4");//mp4 for example
            videoGrabber.start();
        } catch (FrameGrabber.Exception e)
        {
            Log.e("javacv", "Failed to start grabber" + e);
            //return -1;
        }

        Frame vFrame = null;

        do
        {
            try
            {
                vFrame = videoGrabber.grabFrame();
                if(vFrame != null){
                    Log.i("FRAME", "FRAME");
                }
                //do your magic here
            } catch (FrameGrabber.Exception e)
            {
                Log.e("javacv", "video grabFrame failed: "+ e);
            }
        }while(vFrame != null);

        try
        {
            videoGrabber.stop();
        }catch (FrameGrabber.Exception e)
        {
            Log.e("javacv", "failed to stop video grabber", e);
            //return -1;
        }

*/





/*

        //TEST ONLY
       // String videopath ="android.resource://" + getPackageName()+ ;
        String videoPath = new File(getFilesDir() + "/" + "testVid1.mp4").getPath();



        File moviesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
            );

            String filePrefix = "extract_picture";
            String fileExtn = ".jpg";
            File src = new File(videoPath).getAbsoluteFile();
            Video video = new Video();


            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();

            for (int i = 0; i < 30; i++) {

                Bitmap bArray = mediaMetadataRetriever.getFrameAtTime(1000000 * i,
                    MediaMetadataRetriever.OPTION_CLOSEST);


                //savebitmap(bArray, 33333 * i);

            }




           // FFmpegMediaMetadataRetriever med = new FFmpegMediaMetadataRetriever();

           // med.setDataSource("your data source");
           // Bitmap bmp = med.getFrameAtTime(i*1000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);

            //File appDir=new File(moviesDir,"/"+app_name+"/");
            //if(!appDir.exists())
            //    appDir.mkdir();
            //DataModel.appDir=appDir;
            //File dir = new File(appDir, "testVideo");
            int fileNo = 0;
            //while (dir.exists()) {
                fileNo++;
               // dir = new File(moviesDir+"/"+app_name+"/", "testVideo" + fileNo);

            //}
            //dir.mkdir();
            //DataModel.dir = dir;

//            resultList = new ArrayList<String>(256);

//            filePath = dir.getAbsolutePath();
 //           File dest = new File(dir, filePrefix + "%03d" + fileExtn);




        //    String[] complexCommand = { "-i",""+src.toString(),"-qscale:v", "2","-vf", "fps=fps=20/1",dest.getAbsolutePath()};
            //"-qscale:v", "2","-vf", "fps=fps=20/1",//
            //works fine with speed and
           // execFFmpegBinary(complexCommand);


           */

        }

}
