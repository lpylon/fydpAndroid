package com.fydp.fundusapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fydp.fundusapp.Fragments.PatientInfoFragment;
import com.fydp.fundusapp.Objects.Exam;
import com.fydp.fundusapp.Objects.ExamImage;
import com.fydp.fundusapp.Objects.Patient;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReviewExistingPhotosActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    Patient currentPatient;
    private ListView searchList;
    DatabaseHelper databaseHelper;

    Button searchButton;
    TextView searchKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_existing_photos);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE);
        String patientId = prefs.getString(MainActivity.PATIENT_ID, "");
        databaseHelper = new DatabaseHelper(getApplicationContext());//TODO probably save this context
        currentPatient = databaseHelper.getPatient(patientId);

        searchButton = findViewById(R.id.search_button_exams);
        searchButton.setOnClickListener(this);
        searchKey = findViewById(R.id.search_phrase_exams);

        databaseHelper = new DatabaseHelper(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        final List<Exam> exams = databaseHelper.getAllPatientExams(patientId);
        ArrayAdapter arrayAdapter = new ArrayAdapter<Exam>(this, android.R.layout.simple_expandable_list_item_1, exams){
            @Override
            public View getView(int position, View convertView,  ViewGroup exam){
                TextView item = (TextView) super.getView(position,convertView,exam);

                item.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                //new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                String timestamp = exams.get(position).getExamDate();
                Date date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(timestamp, new ParsePosition(0));
                //String date = new Date(Integer.valueOf(timestamp)).toString();
                //item.setText(date.toLocaleString());
                item.setText(timestamp);
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                return item;
            }
        };
        searchList = findViewById(R.id.list_exams);

        searchList.setAdapter(arrayAdapter);
        searchList.setOnItemClickListener((parent, view, position, id) -> {

            Intent viewExam = new Intent(this, ViewExamResultsActivity.class);
            viewExam.putExtra("exam_id", exams.get(position).getExamId());
            startActivity(viewExam);


            //tODO open up exam selected
            /*
            Patient patient = patients.get(position);

            SharedPreferences.Editor editor = this.getSharedPreferences(
                    MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
            editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
            editor.apply();


            mListener.onFragmentInteraction();
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack( "tag" ).commit();
            */
        });

        //image = findViewById(R.id.image_test);


        /*
        if(currentPatient!=null) {
            ExamImage examImage = databaseHelper.getExamImage(patientId);
            Log.i("EyeLR", examImage.getEyeLR());
            Log.i("EyeSection", examImage.getEyeSection());
            Log.i("ExaminerID", examImage.getExaminerId());
            Log.i("Bitmap", examImage.getCombinedImageData().toString());

            Bitmap imageBitmap = examImage.getCombinedImageData();
            image.setImageBitmap(imageBitmap);
        }

        */

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.search_button_exams:

                try {
                    InputMethodManager imm = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }


                final List<Exam> exams = databaseHelper.getSearchedExam(searchKey.getText().toString());
                ArrayAdapter arrayAdapter = new ArrayAdapter<Exam>(this.getApplicationContext(), android.R.layout.simple_expandable_list_item_1, exams){
                    @Override
                    public View getView(int position, View convertView,  ViewGroup exam){
                        TextView item = (TextView) super.getView(position,convertView, exam);

                        item.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                        //new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                        String timestamp = exams.get(position).getExamDate();
                        Date date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").parse(timestamp, new ParsePosition(0));
                        //String date = new Date(Integer.valueOf(timestamp)).toString();
                        //item.setText(date.toLocaleString());
                        item.setText(timestamp);
                        item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                        return item;
                    }
                };
                searchList.setAdapter(arrayAdapter);
                searchList.setOnItemClickListener((parent, view, position, id) -> {
                    Intent viewExam = new Intent(this, ViewExamResultsActivity.class);
                    viewExam.putExtra("exam_id", exams.get(position).getExamId());
                    startActivity(viewExam);

                });

            break;

         }
    }
}
