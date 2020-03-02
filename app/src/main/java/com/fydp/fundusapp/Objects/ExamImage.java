package com.fydp.fundusapp.Objects;

import android.graphics.Bitmap;

public class ExamImage {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String MACULA = "macula";
    public static final String OPTIC_NERVE = "optic_nerve";

    private String examImageID;
    private String examId;
    private String eyeLR;
    private String eyeSection;
    private Bitmap combinedImageData;
    private Bitmap image1;
    private Bitmap image2;
    private Bitmap image3;
    private Bitmap image4;
    private Bitmap image5;


    public ExamImage(String examImageId, String examId, String eyeLR, String eyeSection, Bitmap combinedImageData, Bitmap image1, Bitmap image2, Bitmap image3, Bitmap image4, Bitmap image5){
        this.examImageID = examImageId;
        this.examId = examId;
        this.eyeLR= eyeLR;
        this.eyeSection = eyeSection;
        this.combinedImageData = combinedImageData;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }

    /*
    public ExamImage(String patientId, String examDate, String eyeLR, String eyeSection, String examinerId, Bitmap combinedImageData, Bitmap image1, Bitmap image2, Bitmap image3, Bitmap image4, Bitmap image5){
        this.patientId = patientId;
        this.examDate = examDate;
        this.eyeLR = eyeLR;
        this.eyeSection = eyeSection;
        this.examinerId = examinerId;
        this.combinedImageData = combinedImageData;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }
    */

    public String getExamImageID(){
        return examImageID;
    }

    public String getExamId(){
        return examId;
    }


    public String getEyeLR(){
        return eyeLR;
    }

    public String getEyeSection() {return eyeSection;}


    public Bitmap getCombinedImageData(){
        return combinedImageData;
    }

    public Bitmap getImage1(){
        return image1;
    }

    public Bitmap getImage2(){
        return image2;
    }

    public Bitmap getImage3(){
        return image3;
    }

    public Bitmap getImage4(){ return image4;}

    public Bitmap getImage5(){ return image5;}

}
