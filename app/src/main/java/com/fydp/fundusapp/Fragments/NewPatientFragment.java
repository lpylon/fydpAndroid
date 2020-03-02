package com.fydp.fundusapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fydp.fundusapp.DatabaseHelper;
import com.fydp.fundusapp.MainActivity;
import com.fydp.fundusapp.Objects.Patient;
import com.fydp.fundusapp.R;
import com.fydp.fundusapp.VideoReviewActivity;

import java.util.Calendar;
import java.util.UUID;

import static android.telephony.PhoneNumberUtils.isGlobalPhoneNumber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewPatientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPatientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText dateOfBirth;
    private Button createNewPatientButton;
    private DatabaseHelper databaseHelper;


    TextWatcher twDate = new TextWatcher() {
        private String current = "";
        private String ddmmyyyy = "DDMMYYYY";
        private Calendar cal = Calendar.getInstance();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //dateOfBirth.setText("DD/MM/YYYY");
            //dateOfBirth.setHint("DD/MM/YYYY");
        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!s.toString().equals(current)) {
                String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                int cl = clean.length();
                int sel = cl;
                for (int i = 2; i <= cl && i < 6; i += 2) {
                    sel++;
                }
                //Fix for pressing delete next to a forward slash
                if (clean.equals(cleanC)) sel--;

                if (clean.length() < 8){
                    clean = clean + ddmmyyyy.substring(clean.length());
                }else{
                    //This part makes sure that when we finish entering numbers
                    //the date is correct, fixing it otherwise
                    int day  = Integer.parseInt(clean.substring(0,2));
                    int mon  = Integer.parseInt(clean.substring(2,4));
                    int year = Integer.parseInt(clean.substring(4,8));

                    mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                    cal.set(Calendar.MONTH, mon-1);
                    year = (year<1900)?1900:(year>2100)?2100:year;
                    cal.set(Calendar.YEAR, year);
                    // ^ first set year for the line below to work correctly
                    //with leap years - otherwise, date e.g. 29/02/2012
                    //would be automatically corrected to 28/02/2012

                    day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                    clean = String.format("%02d%02d%02d",day, mon, year);
                }

                clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8));

                sel = sel < 0 ? 0 : sel;
                current = clean;
                dateOfBirth.setText(current);
                dateOfBirth.setSelection(sel < current.length() ? sel : current.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };


    TextWatcher twPhoneNumber = new TextWatcher() {
        int keyDel;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            phoneNumber.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                }
            });

            if (keyDel == 0) {
                int len = phoneNumber.getText().length();
                if(len == 3) {
                    phoneNumber.setText(phoneNumber.getText() + "-");
                    phoneNumber.setSelection(phoneNumber.getText().length());
                }
                else if (len==7){
                    phoneNumber.setText(phoneNumber.getText() + "-");
                    phoneNumber.setSelection(phoneNumber.getText().length());
                }
            } else {
                keyDel = 0;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public NewPatientFragment() { }

    public static NewPatientFragment newInstance(String param1, String param2) {
        NewPatientFragment fragment = new NewPatientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("New Patient");
        View view = inflater.inflate(R.layout.fragment_new_patient, container, false);
        createNewPatientButton = view.findViewById(R.id.register);
        createNewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });

        databaseHelper = new DatabaseHelper(getContext());
        firstName = view.findViewById(R.id.firstname);
        lastName = view.findViewById(R.id.lastname);
        dateOfBirth = view.findViewById(R.id.birthyear);
        dateOfBirth.addTextChangedListener(twDate);
        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if(dateOfBirth.getText().toString().equals("")){
                        dateOfBirth.setText(" ");
                        dateOfBirth.setSelection(0);

                    }
                   // Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        phoneNumber = view.findViewById(R.id.phonenumber);
        phoneNumber.addTextChangedListener(twPhoneNumber);
        return view;
    }


    String phoneNumberRegex = "^[-]?[0-9]{12,15}$";

    public void onButtonPressed(View view) {

        if (mListener != null) {

            if(firstName.getText().toString().equals("")){
                Log.i("LAUREL", "No First Name"); //TODO show user prompts to complete information
                showIncompleteInfoMessage("Invalid first name.");
            }
            else if(lastName.getText().toString().equals("")){
                showIncompleteInfoMessage("Invalid last name.");
            }
           // else if(phoneNumber.getText().toString().equals("") || !(phoneNumber.getText().toString().matches(phoneNumberRegex))){
            else if(phoneNumber.getText().toString().length() < 10 || phoneNumber.getText().toString().equals("") || !(isGlobalPhoneNumber(phoneNumber.getText().toString()))){
                showIncompleteInfoMessage("Invalid phone number.");
            }
            else if(dateOfBirth.getText().toString().equals("")){
                showIncompleteInfoMessage("Invalid date of birth.");
            }
            else if(!(databaseHelper.getPatientByName(firstName.getText().toString(), lastName.getText().toString()) == null)){
                showExistingPatientMessage(databaseHelper.getPatientByName(firstName.getText().toString(), lastName.getText().toString()));
            }
            else {
                Patient patient = new Patient(
                        UUID.randomUUID().toString(),
                        firstName.getEditableText().toString(),
                        lastName.getEditableText().toString(),
                        dateOfBirth.getEditableText().toString(),
                        phoneNumber.getEditableText().toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
                databaseHelper.addNewPatient(patient);

                SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences(
                        MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
                editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
                editor.apply();

                mListener.onFragmentInteraction();
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack("tag").commit();
                getActivity().setTitle("Patient Information");
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    private void showIncompleteInfoMessage(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Please Complete Information ");
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                (dialog, id) -> dialog.cancel());


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showExistingPatientMessage(Patient patient){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("Existing patient found in system");
        builder1.setMessage("An existing patient with the name " + patient.getFirstName() + " " + patient.getLastName() + " was found with " +
                "date of birth: " + patient.getDateOfBirth() + " and phone number: " + patient.getPhoneNumber() +
                ". Would you like to sign into this existing patient or create a new patient?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Sign in Existing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO refactor this part
                        Patient patient = new Patient(
                                UUID.randomUUID().toString(),
                                firstName.getEditableText().toString(),
                                lastName.getEditableText().toString(),
                                dateOfBirth.getEditableText().toString(),
                                phoneNumber.getEditableText().toString());

                        SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences(
                                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
                        editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
                        editor.apply();

                        mListener.onFragmentInteraction();
                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack( "tag" ).commit();;
                        getActivity().setTitle("Patient Information");
                    }
                }
        );

        builder1.setNegativeButton(
                "Create New",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Patient patient = new Patient(
                                UUID.randomUUID().toString(),
                                firstName.getEditableText().toString(),
                                lastName.getEditableText().toString(),
                                dateOfBirth.getEditableText().toString(),
                                phoneNumber.getEditableText().toString());

                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
                        databaseHelper.addNewPatient(patient);

                        SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences(
                                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
                        editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
                        editor.apply();

                        mListener.onFragmentInteraction();
                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack("tag").commit();
                        getActivity().setTitle("Patient Information");

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
