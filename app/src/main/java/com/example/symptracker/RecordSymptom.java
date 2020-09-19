package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RecordSymptom extends AppCompatActivity {

    Spinner sympSelect;
    EditText symptomTextEntry;
    Button saveBtn;
    String note = "";
    String symptom = "";
    DBHandler db = new DBHandler(this);
    boolean severity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_symptom);

        sympSelect = findViewById(R.id.sympSelect);
        symptomTextEntry = findViewById(R.id.symptomTextEntry);
        saveBtn = findViewById(R.id.saveBtn);

        Log.d("Insert: ", "Inserting...");
        db.addSymptom(new Symptom("headache", false));
        db.addSymptom(new Symptom("fever", true));
        db.addSymptom(new Symptom("cough", false));
        db.addSymptom(new Symptom("vomiting", false));
        db.addSymptom(new Symptom("chest pain", true));
        db.addSymptom(new Symptom("severe pains", true));
        db.addSymptom(new Symptom("dizziness", false));
        db.addSymptom(new Symptom("congestion", false));
        db.addSymptom(new Symptom("pneumonia", true));
        db.addSymptom(new Symptom("other", false));
        //do we even need to do this if we're treating symp objects as user recorded ones rather than system references ones?

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
                //oh shit, do we need to create a separate database for actually RECORDING the user's symptoms?
                Symptom s = new Symptom(symptom, severity, note);
                db.addSymptom(s);
                //for testing purposes
                Log.d("Insert: ", "Added new user symptom to database: " + s.toString());
                //resets the text in the box
                symptomTextEntry.getText().clear();

            }
        });
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