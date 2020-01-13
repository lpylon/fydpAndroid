package com.fydp.fundusapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fydp.fundusapp.DatabaseHelper;
import com.fydp.fundusapp.MainActivity;
import com.fydp.fundusapp.Objects.Patient;
import com.fydp.fundusapp.R;
import com.fydp.fundusapp.VideoActivity;

import java.util.List;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientListFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private Button searchButton;
    private EditText searchKey;
    private ListView searchList;



    private OnFragmentInteractionListener mListener;

    public PatientListFragment() {
    }

    public static PatientListFragment newInstance(String param1, String param2) {
        PatientListFragment fragment = new PatientListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Patient List");

        // Inflate the layout for this fragment
        View view2 =  inflater.inflate(R.layout.fragment_patient_list, container, false);


        //ListView listView = view2.findViewById(R.id.list_patients);

        databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());

        final List<Patient> patients = databaseHelper.getAllPatients();
        ArrayAdapter arrayAdapter = new ArrayAdapter<Patient>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, patients){
            @Override
            public View getView(int position, View convertView,  ViewGroup parent){
                TextView item = (TextView) super.getView(position,convertView,parent);

                item.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary));
                item.setText(patients.get(position).getFirstName() + " " + patients.get(position).getLastName());
                item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                return item;
            }
        };
        searchList = view2.findViewById(R.id.list_patients);


        searchList.setAdapter(arrayAdapter);
        searchList.setOnItemClickListener((parent, view, position, id) -> {
            Patient patient = patients.get(position);

            SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences(
                    MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
            editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
            editor.apply();

            mListener.onFragmentInteraction();
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack( "tag" ).commit();
        });

        searchKey = view2.findViewById(R.id.search_phrase);
        searchButton = view2.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed(view);
            }
        });
        return view2;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
            //Fragment nextFragment = null;
            switch(view.getId()) {
                case R.id.search_button:

                    try {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    final List<Patient> patients = databaseHelper.getSearchedPatient(searchKey.getText().toString());
                    ArrayAdapter arrayAdapter = new ArrayAdapter<Patient>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, patients){
                        @Override
                        public View getView(int position, View convertView,  ViewGroup parent){
                            TextView item = (TextView) super.getView(position,convertView,parent);

                            item.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary));
                            item.setText(patients.get(position).getFirstName() + " " + patients.get(position).getLastName());
                            item.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                            return item;
                        }
                    };
                    searchList.setAdapter(arrayAdapter);
                    searchList.setOnItemClickListener((parent, view2, position, id) -> {
                        Patient patient = patients.get(position);

                        SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences(
                                MainActivity.SHARED_PREFS_PATIENT, Context.MODE_PRIVATE).edit();
                        editor.putString(MainActivity.PATIENT_ID, patient.getPatientId());
                        editor.apply();

                        mListener.onFragmentInteraction();
                        FragmentTransaction fragmentTransaction = getActivity()
                                .getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.patient_content, new PatientInfoFragment()).addToBackStack( "tag" ).commit();
                    });


                    //Intent takeAVideo = new Intent(getActivity().getApplicationContext(), VideoActivity.class);
                    //startActivity(takeAVideo);
                    //break;
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
