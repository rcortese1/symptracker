package com.example.symptracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FBHandler
{
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String TAG = "Store";
    private FirebaseUser user;

    public FBHandler(FirebaseFirestore db)
    {
        this.db = db;
    }

    public void addRecording(Recording r)
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        Map<String, Object> recording = new HashMap<>();
        recording.put("Symptom",  r.getSymptom());
        recording.put("Note", r.getNote());
        recording.put("Time in millis", r.getMillis());
        recording.put("Time as Date", r.getTime());

        Log.d("Fireball", user.getEmail());
        db.collection(user.getEmail())
                .add(recording)
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
