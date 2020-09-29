package com.example.symptracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBHandler {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String TAG = "Store";
    private FirebaseUser user;
    List<Recording> recordings = new ArrayList<Recording>();

    public FBHandler(FirebaseFirestore db) {
        this.db = db;
    }

    //Converts the recording object into an object that can be stored by Firebase
    public void addRecording(Recording r) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //Places all the object variables into a hash map for retrieval later
        Map<String, Object> recording = new HashMap<>();
        recording.put("Symptom", r.getSymptom());
        recording.put("Note", r.getNote());
        recording.put("Millis", r.getMillis());
        recording.put("Date", r.getTime());

        Log.d("Fireball", user.getEmail());

        //Accesses the user's collection which is denoted by their email address.
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

    public List<Recording> getRecordings()
    {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        recordings.clear();

        db.collection(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                //Returns the database as a map
                                Map<String, Object> list = document.getData();

                                //Converts the symptom object that is stored in the database into a map
                                Map<String, Object> s = (Map<String, Object>) list.get("Symptom");

                                //Retrieves symptom values from map
                                Symptom symptom = new Symptom(String.valueOf(s.get("name")), Boolean.parseBoolean((s.get("severe").toString())));

                                //Retrieves note related to recording
                                String note = list.get("Note").toString();
                                String millis = list.get("Millis").toString();
                                Timestamp timestamp = (Timestamp) list.get("Date");
                                Date date = timestamp.toDate();

                                Recording r = new Recording(symptom, note, millis, date);

                                recordings.add(r);
                                Log.d(TAG, r.toString());

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        return recordings;

    }

}