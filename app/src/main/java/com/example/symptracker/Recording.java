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
        //we call an instance of Calendar to get the current time of the Recording's instantiation,
        //and then use that object to extract both the time in milliseconds and as a Date object
        cal = Calendar.getInstance();
        //we store millis as a string for cloud firestore
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
        //Because the Date class methods are deprecated, we need to make a GregorianCalendar,
        //instantiate it to the time specified by the Date object, and then use the GregCal's methods to extract things like year, month etc.
        //We do this so we can then search our symptoms by day/month/year, to then display on SympCalendar
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
