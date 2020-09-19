package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/*import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.widget.AdapterView.OnItemSelectedListener;*/ //for some reason they want to be optimised

public class RecordSymptom extends AppCompatActivity {

    Spinner sympSelect;
    EditText symptomTextEntry;
    Button saveBtn;
    //Symptom[] symparray;
    String note = "";
    /*AdapterView<?> parent;
    View view;*/
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


        /*symparray = {
                new Symptom("headache", false); //use constructor to make the 6 symptoms
        new Symptom("fever", true);
        new Symptom("cough", false);
        new Symptom("vomiting", false);
        new Symptom("chest pain", true);
        new Symptom("severe pains", true);
            }*/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.symp_name_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sympSelect.setAdapter(adapter);

        /*@Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            //upon selecting spinner item, set it to symptom var
            symptom = parent.getItemAtPosition(position).toString();
            //shows selected symptom item
            Toast.makeText(parent.getContext(), "Selected: " + symptom, Toast.LENGTH_LONG).show();
            //set save btn as being auto turned off, and then make save btn functional at end of this method
        }*/

        //initSymptoms();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get details from edit text
                note = symptomTextEntry.getText().toString();
                //get chosen symptom from spinner select
                symptom = sympSelect.getSelectedItem().toString();
                severity = isSevere(symptom);
                //oh shit, do we need to create a separate database for actually RECORDING the user's symptoms?
                db.addSymptom(new Symptom(symptom, severity, note));
                Log.d("Insert: ", "Added new user symptom to database");
                //resets the text in the box
                symptomTextEntry.getText().clear();
                //TODO: send sms if severity == yes
                //don't think we need to reset the dropdown box again
            }
        });
    }

    private boolean isSevere(String name)
    {
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
                /*severity = false;
                break;
            case "fever":
                severity = true;
                break;

                severity = false;
                break;
            case "chest pain":
                severity = true;
                break;
            case "severe pains":
                severity = true;
                break;

                severity = false;
                break;

                severity = false;
                break;
            case "pneumonia":
                severity = true;
                break;

                severity = false;
                break;*/
        }
        return severity;
    }

    /*private void initSymptoms(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}