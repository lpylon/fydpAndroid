package com.fydp.fundusapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fydp.fundusapp.DatabaseHelper;
import com.fydp.fundusapp.EyeExamActivity;
import com.fydp.fundusapp.MainActivity;
import com.fydp.fundusapp.Objects.Patient;
import com.fydp.fundusapp.R;
import com.fydp.fundusapp.ReviewExistingPhotosActivity;
import com.fydp.fundusapp.VideoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button newVideoButton;
    private Button reviewExistingButton;



    private Patient currentPatient;
    private TextView patientNameEditText;
    private TextView patientPhoneNumberEditText;
    private TextView patientDOBEdiitText;

    public PatientInfoFragment() { }

    public static PatientInfoFragment newInstance() {
        PatientInfoFragment fragment = new PatientInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Patient Information");

        SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(
                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE);
        String patientId = prefs.getString(MainActivity.PATIENT_ID, "");
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());//TOdo probably save this context
        currentPatient = databaseHelper.getPatient(patientId);

        View view = inflater.inflate(R.layout.fragment_patient_info, container, false);
        patientNameEditText = view.findViewById(R.id.patient_name);
        patientPhoneNumberEditText = view.findViewById(R.id.phone_number);
        patientDOBEdiitText = view.findViewById(R.id.dob);

        if(currentPatient!=null) {
            patientNameEditText.setText(currentPatient.getFirstName() + " " + currentPatient.getLastName());
            patientPhoneNumberEditText.setText(currentPatient.getPhoneNumber());
            patientDOBEdiitText.setText(currentPatient.getDateOfBirth());
        }

        newVideoButton = view.findViewById(R.id.take_video_button);
        newVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });

        reviewExistingButton = view.findViewById(R.id.review_images_button);
        reviewExistingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
            //Fragment nextFragment = null;
            switch(view.getId()) {
                case R.id.take_video_button:
                    Intent takeAVideo = new Intent(getActivity().getApplicationContext(), EyeExamActivity.class);
                    startActivity(takeAVideo);
                    break;

                case R.id.review_images_button:
                    Intent reviewImages = new Intent(getActivity().getApplicationContext(), ReviewExistingPhotosActivity.class);
                    startActivity(reviewImages);
                    break;
            }
            //mListener.onFragmentInteraction();
            //FragmentTransaction fragmentTransaction = getActivity()
            //        .getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.patient_content, nextFragment);
            //fragmentTransaction.commit();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
}
