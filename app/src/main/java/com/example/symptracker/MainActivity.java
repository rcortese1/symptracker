package com.example.symptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button recSympBtn;
    Button calendarBtn;
    Button contactBtn;
    Button crashBtn;

    AlertDialog.Builder builder;

    String TAG = "Auth";

    private FirebaseAuth mAuth;

    TextView welcome_msg;

    //TODO: add rest of activities

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void login()
    {
        builder.setMessage(R.string.login)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You clicked me!", Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You didn't :(", Toast.LENGTH_SHORT).show();
                    }
                })
                .setView(R.id.dialogbox);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateUI(FirebaseUser user)
    {
        if(user != null)
        {

            String introMSG = getResources().getString(R.string.hello, user.getDisplayName());
            welcome_msg.setText(introMSG);
        }
        else
        {
            Log.d("ABC!", "First??");
            //login();
        }
    }

    public void signUp(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "createUserWithEmail=success!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            Log.d(TAG, "createUserWithEmail=failure!");
                            Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void signIn(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG, "signin=success!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            Log.d(TAG, "signin=failure!");
                            Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recSympBtn = findViewById(R.id.recSympBtn);
        calendarBtn = findViewById(R.id.calendarBtn);
        contactBtn = findViewById(R.id.contactBtn);

        mAuth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(this);

        //The name part of the message should be updated once user's name recorded and stored in database
        welcome_msg = findViewById(R.id.welcome_msg);

        crashBtn = findViewById(R.id.crashButton);
        crashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Test Crash");
            }
        });

        recSympBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecordSymptom.class);
                //Intent intent = new Intent(getApplicationContext(), DatabaseTester.class);
                startActivity(intent);
            }
        });

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //THESE WILL BE UNCOMMENTED WHEN THEIR SCREENS ARE MADE
                /*Intent intent = new Intent(getApplicationContext(), SympCalendar.class);
                startActivity(intent);*/
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
}