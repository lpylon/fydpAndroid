package com.fydp.fundusapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fydp.fundusapp.Objects.Patient;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
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


    private String CREATE_PHYSICIAN_TABLE = "CREATE TABLE " + TABLE_PHYSICIANS + "("
            + PHYSICIAN_ID + " TEXT PRIMARY KEY,"
            + USERNAME + "TEXT,"
            + PASSWORD + "TEXT,"
            + PYSICIAN_FIRST_NAME + " TEXT DEFAULT 0,"
            + PYSICIAN_LAST_NAME + "TEXT DEFAULT 0)";


    private String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_PATIENTS + "("
            + PATIENT_ID + " TEXT PRIMARY KEY,"
            + PATIENT_FIRST_NAME + "TEXT,"
            + PATIENT_LAST_NAME + "TEXT,"
            + PATIENT_DATE_OF_BIRTH + " TEXT DEFAULT 0,"
            + PHONE_NUMBER + "TEXT DEFAULT 0)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PHYSICIAN_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addNewPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PATIENT_FIRST_NAME, patient.getFirstName());
        values.put(PATIENT_LAST_NAME, patient.getLastName());
        values.put(PATIENT_DATE_OF_BIRTH, patient.getDateOfBirth());
        values.put(PHONE_NUMBER, patient.getPhoneNumber());

        db.insert(TABLE_PATIENTS, null, values);
        db.close();
    }

    public List<Patient> getallusers() {
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







}
