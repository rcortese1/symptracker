package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button recSympBtn;
    private Button calendarBtn;
    private Button contactBtn;
    private TextView welcome_msg;

    //TODO: add rest of activities
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recSympBtn = findViewById(R.id.recSympBtn);
        calendarBtn = findViewById(R.id.calendarBtn);
        contactBtn = findViewById(R.id.contactBtn);
        //The name part of the message should be updated once user's name recorded and stored in database
        welcome_msg = findViewById(R.id.welcome_msg);

        recSympBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), RecordSymptom.class);
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
                //ok, this works, it goes to the contact screen
            }
        });
    }
}