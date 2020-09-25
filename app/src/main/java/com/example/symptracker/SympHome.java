package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SympHome extends AppCompatActivity {

    Button recSympBtn;
    Button calendarBtn;
    Button contactBtn;
    Button crashBtn;
    FirebaseUser user;
    String name;



    //AlertDialog.Builder builder;

    //String TAG = "Auth";

    //private FirebaseAuth mAuth;

    TextView welcome_msg;

    //TODO: add rest of activities
//
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    private void login()
//    {
//        builder.setMessage(R.string.login)
//                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "You clicked me!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "You didn't :(", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    public void updateUI(FirebaseUser user)
//    {
//        if(user != null)
//        {
//
//            String introMSG = getResources().getString(R.string.hello, user.getDisplayName());
//            welcome_msg.setText(introMSG);
//        }
//        else
//        {
//            Log.d("ABC!", "First??");
//            //login();
//        }
//    }
//
//    public void signUp(String email, String password)
//    {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Log.d(TAG, "createUserWithEmail=success!");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        }
//                        else
//                        {
//                            Log.d(TAG, "createUserWithEmail=failure!");
//                            Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//
//    public void signIn(String email, String password)
//    {
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful())
//                        {
//                            Log.d(TAG, "signin=success!");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        }
//                        else
//                        {
//                            Log.d(TAG, "signin=failure!");
//                            Toast.makeText(MainActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//                    }
//                });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_home);

        recSympBtn = findViewById(R.id.recSympBtn);
        calendarBtn = findViewById(R.id.calendarBtn);
        contactBtn = findViewById(R.id.contactBtn);

        //get current user, and get their name
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        //need to update string resource for name when user updates/changes?
        //can i do an if-statement in the res file? or do i just do one here
        //can i remotely update the res string?


        //mAuth = FirebaseAuth.getInstance();

        //builder = new AlertDialog.Builder(this);

        //welcome_msg = getString(R.string.hello, name);
        welcome_msg = findViewById(R.id.welcome_msg);
        welcome_msg.setText(getString(R.string.hello, name));

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