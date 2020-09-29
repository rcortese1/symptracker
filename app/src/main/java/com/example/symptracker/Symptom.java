package com.example.symptracker;

public class Symptom
{
    private String name;
    private boolean isSevere;

    //Multiple constructors for multiple uses, including for testing
    public Symptom()
    {
        this.name = null;
        isSevere = false;
    }

    public Symptom(String name, boolean isSevere)
    {
        this.name = name.toLowerCase();
        this.isSevere = isSevere;
    }

    public Symptom(Symptom s)
    {
        this.name = s.name;
        this.isSevere = s.isSevere;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isSevere()
    {
        return this.isSevere;
    }

    public String severeYesOrNo()
    {
        if(isSevere)
        {
            return "Yes";
        }
        else
        {
            return "No";
        }
    }


    public void setSeverity(boolean b)
    {
        this.isSevere = b;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    //Makes the toString more human-readable and user-friendly
    public String toString()
    {
        String capitalName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return "Name: " + capitalName + "\nSevere?: " + severeYesOrNo();
    }

}
