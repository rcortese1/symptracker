package com.example.symptracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FBHandler
{
    private FirebaseFirestore db;
    private String TAG = "Store";

    public FBHandler(FirebaseFirestore db)
    {
        this.db = db;
    }

    public void addSymptom(Symptom s)
    {
        Map<String, Object> symptom = new HashMap<>();
        symptom.put("Name",  s.getName());
        symptom.put("isSevere", s.isSevere());
        symptom.put("Note", s.getNote());

        db.collection("Symptoms")
                .add(symptom)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding document", e);
                    }
                });

    }


}
