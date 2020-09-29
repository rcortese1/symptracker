package com.example.symptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SympHome extends AppCompatActivity {

    Button recSympBtn;
    Button calendarBtn;
    Button contactBtn;
    String resPref;
    private String CONTACT_NUMBER_KEY = "";
    private String CONTACT_NAME_KEY =  "";

    String defaultValue;
    TextView welcome_msg;

    private FirebaseAuth auth;
    private String TAG = "LoginTAG";

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    /*
    Logs the user out and sends them directly back to the login page.
    This could be improved similar to the main activity redirection by sending them to
    an activity informing them that they need to login.
     */
    private void logout()
    {
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(getApplicationContext(), FirebaseAuthentication.class));
                        finish();
                    }
                });
    }

    /*
    Deletes the user's account COMPLETELY.
    Confirmation dialog HIGHLY recommended to be implemented.
     */

    private void deleteAccount()
    {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        logout();
                    }
                });
    }

    /*
    Executes different functions based on the option selected in the option menu.
     */

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.logoutOpt:
                logout();
                return true;
            case R.id.deleteAccOpt:
                deleteAccount();
                return true;
            case R.id.databaseTest:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Initialises the layout and buttons that redirect to other activities.
     */

    protected void onResume()
    {
        super.onResume();
        checkIfLoggedIn();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_home);

        resPref = getResources().getString(R.string.contactFile);
        recSympBtn = findViewById(R.id.recSympBtn);
        calendarBtn = findViewById(R.id.calendarBtn);
        contactBtn = findViewById(R.id.contactBtn);
        welcome_msg = findViewById(R.id.welcome_msg);
        defaultValue = "default";
        CONTACT_NAME_KEY = getResources().getString(R.string.contact_name_key);
        CONTACT_NUMBER_KEY = getResources().getString(R.string.contact_number_key);

        auth = FirebaseAuth.getInstance();


        checkIfLoggedIn();


        welcome_msg = findViewById(R.id.welcome_msg);
        //The name part of the message should be updated once user's name recorded and stored in database


        //When user clicks button, starts the appropriate activity
        recSympBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getSharedPreferences(resPref, Context.MODE_PRIVATE);

                if(pref.getString(CONTACT_NAME_KEY, defaultValue).equals(defaultValue))
                {
                    Toast.makeText(SympHome.this, "You need to select an emergency contact first!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), RecordSymptom.class);
                    startActivity(intent);
                }

            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SympCalendar.class);
                startActivity(intent);
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SympContact.class);
                startActivity(intent);
            }
        });
    }

    //Checks if user is logged in - if so, continues on. If not, sends to login activity
    private void checkIfLoggedIn() {
        if (auth.getCurrentUser() != null) {
            Log.d(TAG, "User logged in : " + auth.getCurrentUser().getDisplayName());
            updateUI();
        } else {
            Log.d(TAG, "No user logged in");
            Intent intent = new Intent(this, FirebaseAuthentication.class);
            startActivity(intent);
            finish();

        }

    }

    private void updateUI()
    {
        //Takes the current logged in user's name and displays it instead of "User"
        welcome_msg.setText(getString(R.string.hello, auth.getCurrentUser().getDisplayName()));
    }

}