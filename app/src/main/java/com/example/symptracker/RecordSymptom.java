package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecordSymptom extends AppCompatActivity {

    private static final int SEND_MESSAGE = 3;
    private static final int SMS_PERMISSION_REQ = 1230;
    private FBHandler fb;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner sympSelect;
    EditText symptomTextEntry;
    Button saveBtn;
    String note = "";
    String symptom = "";

    String phoneNumber = "";
    // Deprecated database call but here for posterity
    // DBHandler db = new DBHandler(this);

    boolean severity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_symptom);

        sympSelect = findViewById(R.id.sympSelect);
        symptomTextEntry = findViewById(R.id.symptomTextEntry);
        saveBtn = findViewById(R.id.saveBtn);

        fb = new FBHandler(db);

        Log.d("Insert: ", "Inserting...");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.symp_name_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sympSelect.setAdapter(adapter);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get details from edit text
                note = symptomTextEntry.getText().toString();

                //get chosen symptom from spinner select
                symptom = sympSelect.getSelectedItem().toString();
                severity = isSevere(symptom);

                Symptom s = new Symptom(symptom, severity, note);
                fb.addSymptom(s);
                //db.addSymptom(s);
                //for testing purposes
                Log.d("Insert: ", "Added new user symptom to database: " + s.toString());

                if(s.isSevere() == true)
                {
                    String resPref = getResources().getString(R.string.contactFile);
                    SharedPreferences pref = getSharedPreferences(resPref, Context.MODE_PRIVATE);

                    String keyValuePhone = getResources().getString(R.string.contact_number_key);
                    phoneNumber = pref.getString(keyValuePhone, "orange");
                    String keyValueName = getResources().getString(R.string.contact_name_key);
                    String phoneName = pref.getString(keyValueName, "orange");

                    Toast.makeText(RecordSymptom.this, "Message sent to " + phoneName, Toast.LENGTH_LONG).show();
                    String message = "";
                    message += "ALERT:\nSymptom experienced: " + s.getName() + "\nNote attached: " + symptomTextEntry.getText().toString();
                    sendMessage(message);
                }

                //resets the text in the box
                symptomTextEntry.getText().clear();

            }
        });
    }

    private void sendMessage(String message)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("MAD", " SMS Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQ);
        } else
            {
            Log.d("MAD", "SMS Permission is given");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(RecordSymptom.this, "Message Sent", Toast.LENGTH_LONG).show();
        }
    }


    private boolean isSevere(String name) {
        //switch case determines if symptom selected is counted as severe or not
        //this will then help in alerting the emergency contact if a worrying symptom is selected
        switch (name.toLowerCase()) {
            case "fever":
            case "chest pain":
            case "severe pains":
            case "pneumonia":
                severity = true;
                break;
            case "headache":
            case "coughing":
            case "vomiting":
            case "dizziness":
            case "congestion":
            case "other":
            default:
                severity = false;
                break;
        }
        return severity;
    }
}