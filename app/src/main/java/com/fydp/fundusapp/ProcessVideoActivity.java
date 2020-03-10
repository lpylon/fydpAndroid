package com.fydp.fundusapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.fydp.fundusapp.Objects.Exam;
import com.fydp.fundusapp.Objects.ExamImage;
import com.fydp.fundusapp.Objects.Patient;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Feature2D;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGRA2RGB;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGBA2RGB;
import static org.opencv.core.Core.BORDER_CONSTANT;
import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.NORM_HAMMING;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_64FC1;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;


public class ProcessVideoActivity extends AppCompatActivity implements View.OnClickListener  {

    String videoPath;
    Button saveVideo;
    Mat imageMat;
    VideoCapture cap;
    Patient currentPatient;
    ExamImage currentExamImage; //TODO could potentially need to be updated to 4 images
    Button saveButton;
    DatabaseHelper databaseHelper;
    ImageView testImage;
    pl.droidsonroids.gif.GifImageView loader;
    String exam_id;




    static {
//        System.loadLibrary("opencv_java4");
        OpenCVLoader.initDebug();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_video);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE);
        String patientId = prefs.getString(MainActivity.PATIENT_ID, "");
        databaseHelper = new DatabaseHelper(getApplicationContext());//TOdo probably save this context
        currentPatient = databaseHelper.getPatient(patientId);
        //saveButton = findViewById(R.id.save_button);
        //saveButton.setOnClickListener(this);


        Intent intent = getIntent();
        //videoPath = intent.getStringExtra("video_path"); //TODO actually send a path
        //videoPath = new File(getFilesDir() + "/" + "testVid1.mp4").getPath();


        //TODO these urls are for testing
        //videoPath = getFilesDir().getAbsolutePath() +"/" + "testVid2.avi";
        //videoPath = getFilesDir().getAbsolutePath() +"/" + "testVid1.mp4";
        videoPath = getFilesDir().getAbsolutePath() +"/" + "video1.mp4";

        //videoPath = "/storage/emulated/0/VID_20200113_14_33_03.mp4";
        imageMat=new Mat();
//        Log.i("videoPath", videoPath);
        //testImage = findViewById(R.id.test_image);
        loader = findViewById(R.id.loader);

        //testImage.set


        ProcessImageTask processImageTask = new ProcessImageTask();
        processImageTask.execute("String");

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
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.save_button:
            //    storeImage();
             //   break;
        }
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



    private class ProcessImageTask extends AsyncTask<String, Integer, Long> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("LAUREL", "HERE ON PRE EXECUTE");
        }


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Long doInBackground(String... strings) {
            processImage();
            storeImage();
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //updateProgress(progress[0]);
        }

        protected void onPostExecute(Long result) {
            Intent viewResultIntent = new Intent(getApplicationContext(), ViewExamResultsActivity.class);
            viewResultIntent.putExtra("exam_id", exam_id);
            startActivity(viewResultIntent);
        }
    }



    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @RequiresApi(api = Build.VERSION_CODES.M)
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void processImage() {

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);

        Bitmap bitmapA = retriever.getFrameAtTime(0);
        Mat matA = new Mat();
        Utils.bitmapToMat( bitmapA.copy(Bitmap.Config.ARGB_8888, true), matA);

        Log.i("LAUREL", matA.toString());

        //for(int i = 0; i <1; i++){
            FeatureInfo featureInfoA = getKeypoints(matA);

            Bitmap bitmapB = retriever.getFrameAtTime(1000000);
            Mat matB = new Mat();
            Utils.bitmapToMat( bitmapB.copy(Bitmap.Config.ARGB_8888, true), matB);
            FeatureInfo featureInfoB = getKeypoints(matB);

            DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
            List<MatOfDMatch> knnMatches = new ArrayList<>();

            //TODO check to see that converting these doesn't mess stuff up
            featureInfoA.descriptors.convertTo(featureInfoA.descriptors, CV_32F);
            featureInfoB.descriptors.convertTo(featureInfoB.descriptors, CV_32F);

            matcher.knnMatch(featureInfoA.descriptors, featureInfoB.descriptors, knnMatches,2);

            float ratioThresh = 0.75f;
            List<DMatch> listOfGoodMatches = new ArrayList<>();
            for (int i = 0; i < knnMatches.size(); i++) {
                if (knnMatches.get(i).rows() > 1) {
                    DMatch[] matches = knnMatches.get(i).toArray();
                    if (matches[0].distance < ratioThresh * matches[1].distance) {
                        listOfGoodMatches.add(matches[0]);
                    }
                }
            }
            MatOfDMatch goodMatches = new MatOfDMatch();
            goodMatches.fromList(listOfGoodMatches);

            List<Point> obj = new ArrayList<>();
            List<Point> scene = new ArrayList<>();
            List<KeyPoint> listOfKeypointsObject = featureInfoA.keypoints.toList();
            List<KeyPoint> listOfKeypointsScene = featureInfoB.keypoints.toList();
            for (int i = 0; i < listOfGoodMatches.size(); i++) {
                //-- Get the keypoints from the good matches
                obj.add(listOfKeypointsObject.get(listOfGoodMatches.get(i).queryIdx).pt);
                scene.add(listOfKeypointsScene.get(listOfGoodMatches.get(i).trainIdx).pt);
            }




            MatOfPoint2f objMat = new MatOfPoint2f(), sceneMat = new MatOfPoint2f();
            objMat.fromList(obj);
            sceneMat.fromList(scene);
            double ransacReprojThreshold = 3.0;
            Mat H = Calib3d.findHomography( objMat, sceneMat, Calib3d.RANSAC, ransacReprojThreshold );
            Log.i("LAUREL", H.toString());

            int width = 2000;
            int height = 2000;
            Mat result = new Mat();
            Imgproc.warpPerspective(featureInfoA.maskOut, result, H, new Size(width, height));


            /*
            BFMatcher bf = new BFMatcher(NORM_HAMMING,false);

            List<MatOfDMatch> matches = new ArrayList<>();
            Log.i("Laurel", featureInfoA.keypoints.toString());
            Log.i("Laurel", featureInfoB.keypoints.toString());
            Log.i("LAUREL", matches.toString());
            Log.i("LAUREL", bf.toString());

        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bf.knnMatch(featureInfoA.descriptors, featureInfoB.descriptors, matches, 2);
        //bf.match(featureInfoA.descriptors, matches);
        */






        //}


        //VideoCapture cap = new VideoCapture();

        //VideoCapture cap = new VideoCapture(videoPath);
        //cap.open(videoPath);
        //Mat frame = new Mat();

       // MediaMetadataRetriever retriever = new MediaMetadataRetriever();
       // retriever.setDataSource(videoPath);
        /*
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        Bitmap bitmap1 = retriever.getFrameAtTime(0);
        Bitmap bitmap2 = retriever.getFrameAtTime(1000000);


        Mat mat1 = new Mat();
        Bitmap bmp321 = bitmap1.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp321, mat1);
        //Imgproc.cvtColor(mat1, mat1, Imgproc.COLOR_BGR2GRAY);

        Mat mat2 = new Mat();
        Bitmap bmp322 = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp322, mat2);
       // Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_BGR2GRAY);

        Mat mask = new Mat(mat1.size(),CV_8UC1);

        // Mat mask = Mat::zeros(mat1.size(), CV_8U); //TODO create an empty mask

        ORB orb = ORB.create();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        orb.detectAndCompute(mat1,mask, keypoints1, descriptors1);
        Log.i("keypoints", keypoints1.size().toString());

        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        Mat descriptors2 = new Mat();
        orb.detectAndCompute(mat2,mask, keypoints2, descriptors2);


        Features2d.drawKeypoints(mat1, keypoints1, mat1, new Scalar(0,200,0,4));
*/




        //Imgproc.threshold(mat, mat, 120, 255,Imgproc.THRESH_BINARY);

        //FeatureInfo featureInfo = getKeypoints(matA);
        Bitmap bitmap1 = Bitmap.createBitmap(result.width(), result.height(), Bitmap.Config.ARGB_8888);

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        currentExamImage = new ExamImage("1", "1", "1" ,"1234", bitmap1, bitmap1, bitmap1, bitmap1, bitmap1, bitmap1);


       // H.convertTo(H, CV_8U);

        Utils.matToBitmap(result, bitmap1);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //loader.setVisibility(View.GONE);
                //testImage.setVisibility(View.VISIBLE);
                //testImage.setImageBitmap(bitmap1);
            }
        });



    }

    private void storeImage(){
        if(currentExamImage!=null){
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            Date date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(timestamp, new ParsePosition(0));

            String timeStampString = date.toLocaleString();
            String examId = UUID.randomUUID().toString();
            exam_id = examId;
            ExamImage examImage1 = new ExamImage(UUID.randomUUID().toString(), examId, ExamImage.LEFT, ExamImage.MACULA, currentExamImage.getCombinedImageData(), currentExamImage.getImage1(), currentExamImage.getImage2(), currentExamImage.getImage3(), currentExamImage.getImage4(), currentExamImage.getImage5());
            ExamImage examImage2 = new ExamImage(UUID.randomUUID().toString(), examId, ExamImage.RIGHT, ExamImage.OPTIC_NERVE, currentExamImage.getCombinedImageData(), currentExamImage.getImage1(), currentExamImage.getImage2(), currentExamImage.getImage3(), currentExamImage.getImage4(), currentExamImage.getImage5());

            List<ExamImage> examImages = Arrays.asList(examImage1, examImage2);

            Exam exam = new Exam(examId, currentPatient.getPatientId(), "1234", timeStampString, examImages);

            //ExamImage examImage1 = new ExamImage(UUID.randomUUID().toString(), )
            databaseHelper.saveEyeExam(exam);
        }

    }

    private FeatureInfo getKeypoints(Mat image){
        Mat original = image;

        Mat mask = getRetinaMask(image);

        List<Mat> lRgb = new ArrayList<Mat>(3);
        Core.split(image, lRgb);
        Mat mR = lRgb.get(0);
        Mat mG = lRgb.get(1);
        Mat mB = lRgb.get(2);

        Mat kernel = new Mat(3,3, CV_32F){
            {
                put(0,0,-1);
                put(0,1,-1);
                put(0,2,-1);

                put(1,0,-1);
                put(1,1,9);
                put(1,2,-1);

                put(2,0,-1);
                put(2,1,-1);
                put(2,2,-1);
            }
        };

        Imgproc.filter2D(mG, mG, -1, kernel);
        ORB orb = ORB.create();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        orb.detectAndCompute(mG,mask, keypoints1, descriptors1);
        Log.i("Keypoints", keypoints1.toString());
        Features2d.drawKeypoints(mG, keypoints1, mG, new Scalar(200,0,0,4));


        Mat out = new Mat();
        List<Mat> in = new ArrayList<Mat>();
        in.add(mask);
        in.add(mask);
        in.add(mask);
        Core.merge(in, out);
        //Mat img2 = Mat.zeros(mG.size(), CV_8U);
        //img2[:,:,0] = mask
        //img2[:,:,1] = mask
        //img2[:,:,2] = mask

        Imgproc.cvtColor(original, original, CV_RGBA2RGB);

        Log.i("out", String.valueOf(out.channels()));
        Log.i("image", String.valueOf(original.channels()));

        Mat maskOut = Mat.zeros(original.size(), CV_8U);
        Core.subtract(out,original, maskOut);
        Core.subtract(out,maskOut, maskOut);
        Log.i("out", out.size().toString());
        Log.i("image", out.size().toString());


        FeatureInfo featureInfo = new FeatureInfo(keypoints1, descriptors1, mask, maskOut);

        return featureInfo;
    }

    class FeatureInfo {
        public MatOfKeyPoint keypoints;
        public Mat descriptors;
        public Mat mask;
        public Mat maskOut;

        FeatureInfo (MatOfKeyPoint keypoints, Mat descriptors, Mat mask, Mat maskOut ){
            this.keypoints = keypoints;
            this.descriptors = descriptors;
            this.mask = mask;
            this.maskOut = maskOut;
        }
    }


    private Mat getRetinaMask(Mat image){
        List<Mat> lRgb = new ArrayList<Mat>(3);
        Core.split(image, lRgb);
        Mat mR = lRgb.get(0);
        Mat mG = lRgb.get(1);
        Mat mB = lRgb.get(2);

        Mat mask = mR;
        Mat newMask = new Mat();
        //Mat blur = Imgproc.getGaussianKernel(25,0); //TODO figure out how to gaussian blur


       // Imgproc.filter2D(mask, mask, CV_8UC1, kernel);
        //Imgproc.filter2D(mask, mask, 1, kernel, new Point(-1,-1),0, BORDER_DEFAULT);
        Imgproc.threshold(mask, mask, 0, 255, THRESH_BINARY+THRESH_OTSU);

        // double low_threshold = 0.1;
        //int ratio = 10;
        //int kernel_size = 1000;

        Mat struc = Imgproc.getStructuringElement(MORPH_ELLIPSE, new Size(5,5), new Point(-1,-1));
        Imgproc.erode(mask, mask,struc, new Point(-1,-1), 10, BORDER_CONSTANT);

        int imageLength = mask.size(0);
        int imageWidth = mask.size(1);


        for(int i = 0; i< imageWidth; i++){
            double[] val = mask.get(0,i);
            if(val[0] == 255){
                Imgproc.floodFill(mask, Mat.zeros(mask.rows() + 2, mask.cols() + 2, CV_8U), new Point(i,0),new Scalar(0));
            }
            val = mask.get(imageLength-1, i);
            if(val[0] == 255){
                Imgproc.floodFill(mask, Mat.zeros(mask.rows() + 2, mask.cols() + 2, CV_8U), new Point(i,imageLength-1),new Scalar(0));
            }
        }

        for(int i = 0; i< imageLength; i++){
            double[] val = mask.get(i,0);
            if(val[0] == 255){
                Imgproc.floodFill(mask, Mat.zeros(mask.rows() + 2, mask.cols() + 2, CV_8U), new Point(0,i),new Scalar(0));
            }
            val = mask.get(i, imageWidth - 1);
            if(val[0] == 255){
                Imgproc.floodFill(mask, Mat.zeros(mask.rows() + 2, mask.cols() + 2, CV_8U), new Point(imageWidth-1,i),new Scalar(0));
            }
        }

        return mask;
    }

}
