package com.example.symptracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "symptomTracker";
    private static final String TABLE_SYMPTOMS = "symptoms";

    private static final String KEY_NAME = "name";
    private static final String KEY_IS_SEVERE = "isSevere";
    private static final String KEY_NOTE = "note";

    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Severity is determined by;
    // 1 = true, 0 = false.

    //Table creation
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_SYMPTOM_TABLE = "CREATE TABLE " + TABLE_SYMPTOMS + "("
                + KEY_NAME + " TEXT," + KEY_IS_SEVERE
                + " NOT NULL CHECK (" + KEY_IS_SEVERE + " IN (0, 1)" + ")," + KEY_NOTE + " TEXT " + ")";
        db.execSQL(CREATE_SYMPTOM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOMS);

        onCreate(db);
    }

    public void addSymptom(Symptom symptom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, symptom.getName());

        //1 means severe and 0 means not severe
        int severity = symptom.isSevere() ? 1 : 0;
        values.put(KEY_IS_SEVERE, severity);

        values.put(KEY_NOTE, symptom.getNote());

        db.insert(TABLE_SYMPTOMS, null, values);
        db.close();
    }

    public Symptom getSymptom(String name)
    {
        name = name.toLowerCase();
        SQLiteDatabase db = getReadableDatabase();
//
        Cursor cursor = db.query(TABLE_SYMPTOMS, new String[] {KEY_NAME, KEY_IS_SEVERE, KEY_NOTE}, KEY_NAME + "=?",
                        new String[] {String.valueOf(name)}, null, null, null, null);

        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            boolean severity = Integer.parseInt(cursor.getString(1)) == 1;
            Symptom symptom = new Symptom(cursor.getString(0), severity, cursor.getString(2));
            cursor.close();
            return symptom;
        }
        else
        {
            Log.d("CursorNull", "lol cursor is null noob");
            return null;
        }

    }

    public List<Symptom> getAllSymptoms()
    {
        List<Symptom> symptomList = new ArrayList<Symptom>();

        String selectQuery = "SELECT * FROM " + TABLE_SYMPTOMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst())
        {
            do {
                Symptom symptom = new Symptom();
                boolean severity = Integer.parseInt(cursor.getString(1)) == 1;

                symptom.setName(cursor.getString(0));
                symptom.setSeverity(severity);
                symptom.setNote(cursor.getString(2));

                symptomList.add(symptom);
            }
            while(cursor.moveToNext());
        }

        return symptomList;
    }

    public int updateSymptom(Symptom symptom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        int severity = symptom.isSevere() ? 1 : 0;
        values.put(KEY_NAME, symptom.getName());
        values.put(KEY_IS_SEVERE, severity);
        values.put(KEY_NOTE, symptom.getNote());

        return db.update(TABLE_SYMPTOMS, values, KEY_NAME + "= ?",
                new String[] {String.valueOf(symptom.getName())});
    }

    public void deleteSymptom(Symptom symptom)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SYMPTOMS, KEY_NAME + "= ?",
                new String[] {String.valueOf(symptom.getName())});
    }




}
