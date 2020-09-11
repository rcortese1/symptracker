package com.example.symptracker;

public class Symptom
{
    private String name;
    private boolean isSevere;

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

    public String getName()
    {
        return this.name;
    }

    public boolean isSevere()
    {
        return this.isSevere;
    }

    public void setSeverity(boolean b)
    {
        this.isSevere = b;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
