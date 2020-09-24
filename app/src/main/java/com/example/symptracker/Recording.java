package com.example.symptracker;

public class Recording
{
    private Symptom symptom;
    private String note;

    public Recording(Symptom symptom, String note)
    {
        this.symptom = symptom;
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }

    //Creates a symptom at a new memory address to prevent memory modifying
    public Symptom getSymptom()
    {
        return new Symptom(symptom);
    }


}
