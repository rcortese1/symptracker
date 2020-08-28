package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SympContact extends AppCompatActivity {

    Button addContactBtn;
    TextView contactName;
    TextView contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_contact);

        addContactBtn = findViewById(R.id.addContactBtn);

        /* These two should be updated when emergency contact is selected to match the contact's info */
        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);

        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* insert code that happens when button clicked */
            }
        });
    }
}