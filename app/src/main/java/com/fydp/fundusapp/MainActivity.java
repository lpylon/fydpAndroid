package com.fydp.fundusapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button physicianSignIn;
    Button administratorMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        physicianSignIn = findViewById(R.id.physician_sign_in_button);
        administratorMenu = findViewById(R.id.admin_menu_button);

        physicianSignIn.setOnClickListener(this);
        administratorMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.physician_sign_in_button:
                Intent physicianSignInIntent = new Intent(this, PhysicianLoginActivity.class);
                startActivity(physicianSignInIntent);
                break;
            case R.id.admin_menu_button:

                break;
        }
    }
}
