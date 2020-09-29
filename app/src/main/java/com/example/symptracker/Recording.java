package com.example.symptracker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Recording
{
    private Symptom symptom;
    private String note;
    private Calendar cal;
    private long millis;
    private Date time;

    public Recording(Symptom symptom, String note)
    {
        this.symptom = symptom;
        this.note = note;
        cal = Calendar.getInstance();
        millis = cal.getTimeInMillis();
        time = cal.getTime();
    }

    public String getNote()
    {
        return note;
    }

    public long getMillis() { return millis; }

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
        return "Symptom: " + getSymptom() + "\nNote: " + getNote()
             + "\nTime in millis: " + getMillis() + "\nTime in Date: " + getTime();
    }
}
