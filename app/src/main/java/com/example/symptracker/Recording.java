package com.example.symptracker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Recording
{
    private Symptom symptom;
    private String note;
    private Calendar cal;
    private String millis;
    private Date time;

    public Recording(Symptom symptom, String note)
    {
        this.symptom = symptom;
        this.note = note;
        cal = Calendar.getInstance();
        millis = String.valueOf(cal.getTimeInMillis());
        time = cal.getTime();

    }

    //When reading in from the database
    public Recording(Symptom symptom, String note, String millis, Date time)
    {
        this.symptom = symptom;
        this.note = note;
        this.millis = millis;
        this.time = time;
    }

    public String getNote()
    {
        return note;
    }

    public String getMillis() { return millis; }

    public Date getTime() { return time; }

    //Creates a symptom at a new memory address to prevent memory modifying
    public Symptom getSymptom()
    {
        return new Symptom(symptom);
    }

    public int getYear() {
        GregorianCalendar greg = new GregorianCalendar();
        greg.setTime(time);
        return greg.get(greg.YEAR);
    }

    public int getMonth() {
        GregorianCalendar greg = new GregorianCalendar();
        greg.setTime(time);
        return greg.get(greg.MONTH);
    }

    public int getDay() {
        GregorianCalendar greg = new GregorianCalendar();
        greg.setTime(time);
        return greg.get(greg.DAY_OF_MONTH);
    }

    public String toString()
    {
        return "\n" + getSymptom() + "\nNote: " + getNote() + "\nTime: " + getTime() + "\n";
    }
}
