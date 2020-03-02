package com.fydp.fundusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import org.opencv.core.Core;


import com.fydp.fundusapp.Fragments.PatientInfoFragment;
import com.fydp.fundusapp.Objects.Patient;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SHARED_PREFS_PATIENT = "patient_shared_prefs";
    public static final String SHARED_PREFS_PHYSICIAN = "physician_shared_prefs";
    public static final String PATIENT_ID = "patient_id";
    public static final String PHYSICIAN_ID = "physician_id";

    Button physicianSignIn;
    Button administratorMenu;
    Button helpMenu;

    EditText usernameText;
    EditText passwordText;

    public static final String username = "";
    public static final String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        physicianSignIn = findViewById(R.id.physician_sign_in_button);
        administratorMenu = findViewById(R.id.admin_menu_button);
        helpMenu = findViewById(R.id.help_button);

        usernameText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        physicianSignIn.setOnClickListener(this);
        administratorMenu.setOnClickListener(this);
        helpMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.physician_sign_in_button:
                if(username.equals(usernameText.getText().toString()) && password.equals(passwordText.getText().toString())){
                    Intent physicianSignInIntent = new Intent(this, PatientActivity.class);
                    Toast.makeText(this,"Welcome, Laurel", Toast.LENGTH_SHORT).show();
                    startActivity(physicianSignInIntent);
                }
                else{
                    showWrongPasswordMessage();
                }

                break;
            case R.id.admin_menu_button:

                break;
            case R.id.help_button:
                Intent helpButtonIntent = new Intent(this, HelpMenu.class);
                startActivity(helpButtonIntent);
                break;
        }
    }



    private void showWrongPasswordMessage(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Wrong username/password");
        builder1.setMessage("You entered the wrong username or password! Please try again.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        );



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
