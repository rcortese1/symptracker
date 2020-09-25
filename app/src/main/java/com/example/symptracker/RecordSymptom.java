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

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

import java.util.List;

public class RecordSymptom extends AppCompatActivity {

    private static final int SEND_MESSAGE = 3;
    private static final int SMS_PERMISSION_REQ = 1230;
    private FBHandler fb;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Spinner sympSelect;
    private EditText symptomTextEntry;
    private Button saveBtn;
    private String note = "";
    private String symptom = "";
    private String phoneNumber = "";
    private boolean severity;

    List<Symptom> symptomList;

    /*
    This activity records symptoms experienced by users and saves them to the
    online firebase storage that we have setup.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_symptom);

        sympSelect = findViewById(R.id.sympSelect);
        symptomTextEntry = findViewById(R.id.symptomTextEntry);
        saveBtn = findViewById(R.id.saveBtn);
        symptomList = new ArrayList<Symptom>();

        /*
        Initializes a bunch of symptoms.
        */
        Symptom pneumonia = new Symptom("Pneumonia", true);
        Symptom dizziness = new Symptom("Dizziness", false);
        Symptom congestion = new Symptom("Congestion", false);
        Symptom headache = new Symptom("Headache", false);
        Symptom fever = new Symptom("Fever", false);
        Symptom cough = new Symptom("Cough", false);
        Symptom vomiting = new Symptom("Vomiting", true);
        Symptom chest_pain = new Symptom("Chest pain", true);
        Symptom severe_pain = new Symptom("Severe pain", true);
        Symptom other = new Symptom("Other", true);
        /*
        Other is flagged as severe to err on the side of caution
        For example: Brain haemorrhage isn't on the list but we would say it's rather severe
         */

        symptomList.add(pneumonia);
        symptomList.add(dizziness);
        symptomList.add(congestion);
        symptomList.add(headache);
        symptomList.add(fever);
        symptomList.add(cough);
        symptomList.add(vomiting);
        symptomList.add(chest_pain);
        symptomList.add(severe_pain);
        symptomList.add(other);
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
                symptom = sympSelect.getSelectedItem().toString();

                Symptom s = retrieveSymptom(symptom);
                Recording r = new Recording(s, note);
                //Stores the symptom recording on the cloud.
                fb.addRecording(r);
                //for testing purposes
                Log.d("Insert: ", "Added new user symptom to database: " + s.toString());

                /*
                When a symptom is considered severe, as soon as a recording has been submitted
                an SMS is automatically sent to the emergency contact specified in
                SympContact activity specifying the symptom they experienced and the victim's note.
                */
                if(r.getSymptom().isSevere())
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

    /*
    Requests message permissions if permissions have not been granted.
    If permissions are granted, a message is sent to the phone number supplied
    in emergency contact shared preference and a toast confirms message for the user.
     */
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

    //Converts the spinner options to a symptom object
    private Symptom retrieveSymptom(String symptom)
    {
        symptom = symptom.toLowerCase();
        for(Symptom s: symptomList)
        {
            if(s.getName().equalsIgnoreCase(symptom))
            {
                return s;
            }
        }
        return null;
    }

}