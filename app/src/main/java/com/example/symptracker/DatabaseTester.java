package com.example.symptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class DatabaseTester extends AppCompatActivity {

    private EditText symptomNameEntry;
    private EditText symptomSeverityEntry;
    private EditText symptomEntry;
    private TextView showSympName;
    private TextView showSympSeverity;
    private Button addSymptom;
    private Button findSymptom;

    private DBHandler db;

    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_tester);

        //EditTexts
        symptomNameEntry = findViewById(R.id.symptomNameEntry);
        symptomSeverityEntry = findViewById(R.id.symptomSeverityEntry);
        symptomEntry = findViewById(R.id.symptomTextEntry);

        //Buttons
        addSymptom = findViewById(R.id.addSymptom);
        findSymptom = findViewById(R.id.findSymptom);

        //TextViews
        showSympName = findViewById(R.id.showSympName);
        showSympSeverity = findViewById(R.id.showSympSeverity);

        //Database
        db = new DBHandler(this);

        //Variables

        List<Symptom> symptomList = db.getAllStudents();

        for(Symptom s : symptomList)
        {
            Log.d("Symptom: ", "Symptom Name: " + s.getName() + ", Severity: " + s.isSevere());
        }

        addSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = symptomEntry.getText().toString();
                Symptom s = new Symptom("Pneumonia", true);
                db.addSymptom(s);
            }
        });

        findSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = symptomEntry.getText().toString();
                Symptom s = db.getSymptom(name);

                if(s == null)
                {
                    Log.d("ERROR:", "Well the query does not work.");
                }
                else
                {
                    Log.d("SUCCESS", "Symptom is NOT null");
                    showSympName.setText(s.getName());
                    String severe = "";
                    severe = s.isSevere() ? "Severe" : "Not severe";
                    showSympSeverity.setText(severe);

                }
            }
        });


    }
}