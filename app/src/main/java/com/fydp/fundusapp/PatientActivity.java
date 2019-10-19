package com.fydp.fundusapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.fydp.fundusapp.Fragments.NewPatientFragment;
import com.fydp.fundusapp.Fragments.NoPatientSignedInFragment;
import com.fydp.fundusapp.Fragments.PatientInfoFragment;
import com.fydp.fundusapp.Fragments.PatientListFragment;
import com.fydp.fundusapp.Fragments.PhysicianInfoFragment;

public class PatientActivity extends AppCompatActivity
        implements NoPatientSignedInFragment.OnFragmentInteractionListener,
        NewPatientFragment.OnFragmentInteractionListener,
        PatientInfoFragment.OnFragmentInteractionListener,
        PatientListFragment.OnFragmentInteractionListener{
        //implements NavigationView.OnNavigationItemSelectedListener {

    boolean patientSignedIn = true;

    Button takeAVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        /*
        Fragment fragment = new NoPatientSignedInFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.id.contentfragment,fragment).commitNow();
        transaction.add(0,fragment).commitNow();
        */


        /*
        takeAVideoButton = findViewById(R.id.take_video_button);
        takeAVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent videoIntent = new Intent(PatientActivity.this, VideoActivity.class);
                startActivity(videoIntent);
            }
        });
        */



        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        setupDrawerContent(navigationView);

        Fragment fragment = new NoPatientSignedInFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.patient_content, fragment).commit();
        //transaction.replace(R.id.contentfragment,fragment).commitNow();


        if(patientSignedIn){
            //Change view to accept
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Log.i("LAUREL", String.valueOf(fragmentManager.getFragments().size()));
            Log.i("LAUREL", String.valueOf(fragmentManager.getFragments().get(0)));


            //if (getFragmentManager().getBackStackEntryCount() > 0) { //TODO look at backpress logic
                //    getFragmentManager().popBackStack();
                //} else {
                 //   super.onBackPressed();
                //}
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = new Fragment();
        Class fragmentClass = fragment.getClass();
        switch(menuItem.getItemId()) {
            case R.id.nav_log_out:
                //TODO do a log out
                Intent physicianLogoutIntent = new Intent(this, MainActivity.class);
                startActivity(physicianLogoutIntent);
                break;

            case R.id.nav_physician_info:
                fragmentClass = PhysicianInfoFragment.class;
                break;

            case R.id.nav_patient_list:
                fragmentClass = PatientListFragment.class;
                break;

            case R.id.nav_new_patient:
                fragmentClass = NewPatientFragment.class;
                //TODO open new patient
                break;

            case R.id.nav_patient_log_out:
                fragmentClass = NoPatientSignedInFragment.class;
                //TODO do patient log out
                break;
            default:
                fragmentClass = NoPatientSignedInFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.patient_content, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        // Close the navigation drawer
        //mDrawer.closeDrawers();
    }

    @Override
    public void onFragmentInteraction() {

    }





    /*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    */
}
