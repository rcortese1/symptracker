package com.example.symptracker;

public class Symptom
{
    private String name;
    private boolean isSevere;
    private String note = "";

    public Symptom()
    {
        this.name = null;
        isSevere = false;
        note = null;
    }

    public Symptom(String name, boolean isSevere)
    {
        this.name = name.toLowerCase();
        this.isSevere = isSevere;
        //note set to . for testing purposes
        note = ".";
    }

    public Symptom(String name, String note)
    {
        this.name = name.toLowerCase();
        isSevere = false;
        this.note = note;
    }

    public Symptom(String name, boolean isSevere, String note)
    {
        this.name = name.toLowerCase();
        this.isSevere = isSevere;
        this.note = note;
    }

    //get methods

    public String getName()
    {
        return this.name;
    }

    public boolean isSevere()
    {
        return this.isSevere;
    }

    public String getNote()
    {
        return note;
    }

    //set methods

    public void setSeverity(boolean b)
    {
        this.isSevere = b;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String toString()
    {
        return "Name: " + getName() + "\nSevere?: " + isSevere() + "\nNote: " + getNote();
    }

}
