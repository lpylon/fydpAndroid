package com.fydp.fundusapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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


}
