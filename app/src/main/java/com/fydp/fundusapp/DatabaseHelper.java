package com.fydp.fundusapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.fydp.fundusapp.Objects.Exam;
import com.fydp.fundusapp.Objects.ExamImage;
import com.fydp.fundusapp.Objects.Patient;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "fundus.db";


    private static final String TABLE_PHYSICIANS = "physicians";
    private static final String PHYSICIAN_ID = "physician_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PYSICIAN_FIRST_NAME = "first_name";
    private static final String PYSICIAN_LAST_NAME = "last_name";

    private static final String TABLE_PATIENTS = "patients";
    private static final String PATIENT_ID = "patient_id";
    private static final String PATIENT_FIRST_NAME = "first_name";
    private static final String PATIENT_LAST_NAME = "last_name";
    private static final String PATIENT_DATE_OF_BIRTH = "date_of_birth";
    private static final String PHONE_NUMBER = "phone_number";

    private static final String TABLE_EXAMS = "exams";
    private static final String EXAM_ID = "exam_id";
    private static final String EXAMINER_ID = "examiner_id";
    private static final String EXAM_DATE = "exam_date";

    private static final String TABLE_EXAM_IMAGE = "exam_image";
    private static final String EXAM_IMAGE_ID = "exam_image_id";
    //private static final String EXAM_ID = "exam_id";
    // private static final String EXAM_DATE = "exam_date";
    private static final String EYE_LR = "eye_lr";
    private static final String EYE_SECTION = "eye_section";
    //private static final String EXAMINER_ID = "examiner_id";
    private static final String COMBINED_IMAGE = "combined_image";
    private static final String IMAGE1 = "image1";
    private static final String IMAGE2 = "image2";
    private static final String IMAGE3 = "image3";
    private static final String IMAGE4 = "image4";
    private static final String IMAGE5 = "image5";


    //private static final String TABLE_EXAMS = "exams";
    //private static final String EXAM_ID = "exam_id";
    //private static final String EXAMINER_ID = "examiner_id";


    private String CREATE_PHYSICIAN_TABLE = "CREATE TABLE " + TABLE_PHYSICIANS + "("
            + PHYSICIAN_ID + " TEXT PRIMARY KEY,"
            + USERNAME + " TEXT,"
            + PASSWORD + " TEXT,"
            + PYSICIAN_FIRST_NAME + " TEXT DEFAULT 0,"
            + PYSICIAN_LAST_NAME + " TEXT DEFAULT 0)";

    private String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PATIENTS + "("
            + PATIENT_ID + " TEXT PRIMARY KEY,"
            + PATIENT_FIRST_NAME + " TEXT,"
            + PATIENT_LAST_NAME + " TEXT,"
            + PATIENT_DATE_OF_BIRTH + " TEXT DEFAULT 0,"
            + PHONE_NUMBER + " TEXT DEFAULT 0)";

    private String CREATE_EXAM_IMAGE_TABLE = "CREATE TABLE " + TABLE_EXAM_IMAGE + "("
            + EXAM_IMAGE_ID + " TEXT PRIMARY KEY,"
            + EXAM_ID + " TEXT,"
            + EYE_LR + " TEXT,"
            + EYE_SECTION + " TEXT,"
            + COMBINED_IMAGE + " BLOB,"
            + IMAGE1 + " BLOB,"
            + IMAGE2 + " BLOB,"
            + IMAGE3 + " BLOB,"
            + IMAGE4 + " BLOB,"
            + IMAGE5 + " BLOB)";

    private String CREATE_EXAMS_TABLE = "CREATE TABLE " + TABLE_EXAMS + "("
            + EXAM_ID + " TEXT PRIMARY KEY,"
            + PATIENT_ID + " TEXT,"
            + EXAMINER_ID + " TEXT,"
            + EXAM_DATE + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PHYSICIAN_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
        db.execSQL(CREATE_EXAM_IMAGE_TABLE);
        db.execSQL(CREATE_EXAMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(CREATE_EXAM_DATA_TABLE);

    }


    public void addNewPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_ID, patient.getPatientId());
        values.put(PATIENT_FIRST_NAME, patient.getFirstName());
        values.put(PATIENT_LAST_NAME, patient.getLastName());
        values.put(PATIENT_DATE_OF_BIRTH, patient.getDateOfBirth());
        values.put(PHONE_NUMBER, patient.getPhoneNumber());

        db.insert(TABLE_PATIENTS, null, values);
        db.close();
    }

    public List<Patient> getAllPatients() {
        List<Patient> userList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + PATIENT_ID + ", " + PATIENT_FIRST_NAME + ", " + PATIENT_LAST_NAME + ", " + PHONE_NUMBER + ", " + PATIENT_DATE_OF_BIRTH  + " from " + TABLE_PATIENTS + " ORDER BY " + PATIENT_FIRST_NAME + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {
                String patientId = cursor.getString(0);
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String phoneNumber = cursor.getString(3);
                String dateOfBirth = cursor.getString(4);

                Patient user = new Patient(patientId, firstName, lastName, phoneNumber, dateOfBirth);
                userList.add(user);


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public List<Patient> getSearchedPatient(String searchstring) {
        List<Patient> userList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + PATIENT_ID + ", " + PATIENT_FIRST_NAME + ", " + PATIENT_LAST_NAME + ", " + PHONE_NUMBER + ", " + PATIENT_DATE_OF_BIRTH  + " from " + TABLE_PATIENTS + " Where " + PATIENT_ID + "  like '%" + searchstring + "%' OR " + PATIENT_FIRST_NAME + "  like '%" + searchstring + "%' OR " + PHONE_NUMBER + "  like '%" + searchstring + "%' OR " + PATIENT_LAST_NAME + "  like '%" + searchstring + "%' OR "  + PATIENT_FIRST_NAME + "|| ' ' || " +  PATIENT_LAST_NAME + " LIKE  '%" + searchstring +"%'"  + " ORDER BY " + PATIENT_FIRST_NAME + " ASC " , null);
        if (cursor.moveToFirst()) {
            do {
                String userid = cursor.getString(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String pnumber = cursor.getString(3);
                String byear = cursor.getString(4);

                Patient patient = new Patient(userid, fname, lname, pnumber, byear);
                userList.add(patient);


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public Patient getPatientByName(String firstName, String lastName){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PATIENT_FIRST_NAME + " = ? AND " + PATIENT_LAST_NAME + " = ?";

        String[] columns = {PATIENT_ID, PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PHONE_NUMBER, PATIENT_DATE_OF_BIRTH};
        String[] selectionArgs = {firstName, lastName};

        Patient patient = null;
        Cursor cursor = db.query(TABLE_PATIENTS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) { //TODO make sure only one result appears
            String patientID = cursor.getString(0);
            String firstNameString = cursor.getString(1);
            String lastNameString = cursor.getString(2);
            String phoneNumber = cursor.getString(3);
            String dateOfBirth = cursor.getString(4);
            patient = new Patient(patientID, firstNameString, lastNameString, phoneNumber, dateOfBirth);
        }
        db.close();
        cursor.close();
        return patient;
    }

    public Patient getPatient(String patientId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PATIENT_ID + " = ?";

        String[] columns = {PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PHONE_NUMBER, PATIENT_DATE_OF_BIRTH};
        String[] selectionArgs = {patientId};

        Patient patient = null;
        Cursor cursor = db.query(TABLE_PATIENTS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) { //TODO make sure only one result appears
                String firstName = cursor.getString(0);
                Log.i("LAUREL", firstName);
                String lastName = cursor.getString(1);
                String phoneNumber = cursor.getString(3);
                String dateOfBirth = cursor.getString(2);
                patient = new Patient(patientId, firstName, lastName, phoneNumber, dateOfBirth);
        }
        db.close();
        cursor.close();
        return patient;
    }


    public void saveEyeExam(Exam exam){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EXAM_ID, exam.getExamId());
        values.put(PATIENT_ID, exam.getPatientId());
        values.put(EXAMINER_ID, exam.getExaminerId());
        values.put(EXAM_DATE, exam.getExamDate());


        db.insert(TABLE_EXAMS, null, values);
        //db.close();

        List<ExamImage> examImages = exam.getExamImages();

        ContentValues valuesExamImage = new ContentValues();

        for(ExamImage examImage : examImages){

            valuesExamImage.put(EXAM_IMAGE_ID, examImage.getExamImageID());
            valuesExamImage.put(EXAM_ID, examImage.getExamId());
            valuesExamImage.put(EYE_LR, examImage.getEyeLR());
            valuesExamImage.put(EYE_SECTION, examImage.getEyeSection());
            valuesExamImage.put(COMBINED_IMAGE, getBitmapAsByteArray(examImage.getCombinedImageData()));
            valuesExamImage.put(IMAGE1, getBitmapAsByteArray(examImage.getImage1()));
            valuesExamImage.put(IMAGE2, getBitmapAsByteArray(examImage.getImage2()));
            valuesExamImage.put(IMAGE3, getBitmapAsByteArray(examImage.getImage3()));
            valuesExamImage.put(IMAGE4, getBitmapAsByteArray(examImage.getImage4()));
            valuesExamImage.put(IMAGE5, getBitmapAsByteArray(examImage.getImage5()));

            db.insert(TABLE_EXAM_IMAGE, null, valuesExamImage);
            //db.close();
        }
        db.close();
    }

    /*
    public void saveEyeExam(ExamImage examImage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_ID, examImage.getPatientId());
        values.put(EXAM_DATE, examImage.getExamDate());
        values.put(EYE_LR, examImage.getEyeLR());
        values.put(EYE_SECTION, examImage.getEyeSection());
        values.put(EXAMINER_ID, examImage.getExaminerId());
        values.put(COMBINED_IMAGE, getBitmapAsByteArray(examImage.getCombinedImageData()));
        values.put(IMAGE1, getBitmapAsByteArray(examImage.getImage1()));
        values.put(IMAGE2, getBitmapAsByteArray(examImage.getImage2()));
        values.put(IMAGE3, getBitmapAsByteArray(examImage.getImage3()));
        values.put(IMAGE4, getBitmapAsByteArray(examImage.getImage4()));
        values.put(IMAGE5, getBitmapAsByteArray(examImage.getImage5()));

        db.insert(TABLE_EXAM_IMAGE, null, values);
        db.close();
    }
    */

    /*
    public ExamImage getExamImage(String patientId){
        ExamImage examImage = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PATIENT_ID + " = ?";

        String[] columns = {EXAM_DATE, EYE_LR, EYE_SECTION, EXAMINER_ID, COMBINED_IMAGE, IMAGE1, IMAGE2, IMAGE3, IMAGE4, IMAGE5};
        String[] selectionArgs = {patientId};

        Cursor cursor = db.query(TABLE_EXAM_IMAGE, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) { //TODO make sure only one result appears
            String examDate = cursor.getString(0);
            String eyeLR = cursor.getString(1);
            String eyeSection = cursor.getString(2);
            String examinerId = cursor.getString(3);
            byte[] combinedImageByte = cursor.getBlob(4);
            byte[] image1Byte = cursor.getBlob(5);
            byte[] image2Byte = cursor.getBlob(6);
            byte[] image3Byte = cursor.getBlob(7);
            byte[] image4Byte = cursor.getBlob(8);
            byte[] image5Byte = cursor.getBlob(9);

            Bitmap combinedImage = BitmapFactory.decodeByteArray(combinedImageByte, 0, combinedImageByte.length);
            Bitmap image1 = BitmapFactory.decodeByteArray(image1Byte, 0, image1Byte.length);
            Bitmap image2 = BitmapFactory.decodeByteArray(image2Byte, 0, image2Byte.length);
            Bitmap image3 = BitmapFactory.decodeByteArray(image3Byte, 0, image3Byte.length);
            Bitmap image4 = BitmapFactory.decodeByteArray(image4Byte, 0, image4Byte.length);
            Bitmap image5 = BitmapFactory.decodeByteArray(image5Byte, 0, image5Byte.length);


            examImage = new ExamImage(patientId, examDate, eyeLR, eyeSection, examinerId, combinedImage, image1,image2, image3, image4, image5);
        }
        db.close();
        cursor.close();
        return examImage;
    }*/




    public List<Exam> getAllPatientExams(String patientId){

        List<Exam> exams = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PATIENT_ID + " = ?";

        String[] columns = {EXAM_ID, EXAMINER_ID, EXAM_DATE};
        String[] selectionArgs = {patientId};

        Cursor cursor = db.query(TABLE_EXAMS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) { //TODO make sure only one result appears

            do {
                String examId = cursor.getString(0);
                String examinerId = cursor.getString(1);
                String examDate = cursor.getString(2);


                /*
                Bitmap combinedImage = BitmapFactory.decodeByteArray(combinedImageByte, 0, combinedImageByte.length);
                Bitmap image1 = BitmapFactory.decodeByteArray(image1Byte, 0, image1Byte.length);
                Bitmap image2 = BitmapFactory.decodeByteArray(image2Byte, 0, image2Byte.length);
                Bitmap image3 = BitmapFactory.decodeByteArray(image3Byte, 0, image3Byte.length);
                Bitmap image4 = BitmapFactory.decodeByteArray(image4Byte, 0, image4Byte.length);
                Bitmap image5 = BitmapFactory.decodeByteArray(image5Byte, 0, image5Byte.length);
                */


                Exam exam = new Exam(examId, patientId, examinerId, examDate, Arrays.asList());
                Log.i("exam", exam.toString());
                exams.add(exam);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return exams;

    }

    public List<ExamImage> getExamImages(String examId){
        List<ExamImage> examImages = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EXAM_ID + " = ?";

        String[] columns = {EXAM_IMAGE_ID, EYE_LR, EYE_SECTION, COMBINED_IMAGE, IMAGE1, IMAGE2, IMAGE3, IMAGE4,IMAGE5};
        String[] selectionArgs = {examId};

        Cursor cursor = db.query(TABLE_EXAM_IMAGE, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) { //TODO make sure only one result appears

            do {
                String examImageId = cursor.getString(0);
                String eyeLR = cursor.getString(1);
                String eyeSection = cursor.getString(2);
                byte[]combinedImageByte = cursor.getBlob(3);
                byte[]image1Byte = cursor.getBlob(4);
                byte[]image2Byte = cursor.getBlob(5);
                byte[]image3Byte = cursor.getBlob(6);
                byte[]image4Byte = cursor.getBlob(7);
                byte[]image5Byte = cursor.getBlob(8);


                Bitmap combinedImage = BitmapFactory.decodeByteArray(combinedImageByte, 0, combinedImageByte.length);
//                Bitmap image1 = BitmapFactory.decodeByteArray(image1Byte, 0, image1Byte.length);
//                Bitmap image2 = BitmapFactory.decodeByteArray(image2Byte, 0, image2Byte.length);
//                Bitmap image3 = BitmapFactory.decodeByteArray(image3Byte, 0, image3Byte.length);
//                Bitmap image4 = BitmapFactory.decodeByteArray(image4Byte, 0, image4Byte.length);
//                Bitmap image5 = BitmapFactory.decodeByteArray(image5Byte, 0, image5Byte.length);


//TODO handle these bitmaps
                ExamImage examImage = new ExamImage(examImageId, examId, eyeLR, eyeSection, combinedImage, combinedImage, combinedImage, combinedImage, combinedImage, combinedImage);
                Log.i("exam", examImage.toString());
                examImages.add(examImage);
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return examImages;

    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
