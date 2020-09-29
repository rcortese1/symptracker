package com.example.symptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.List;

public class SympCalendar extends AppCompatActivity {

    private CalendarView calendar;
    private TextView viewSymps;
    private FBHandler fb;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Recording> recordingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_calendar);

        calendar = findViewById(R.id.calendarView);
        viewSymps = findViewById(R.id.sympsOnDate);

        fb = new FBHandler(db);

        recordingList = fb.getRecordings();

        //We retrieve a list of Recordings (user's symptoms),
        //then we sort through them by date and display them on the appropriate calendar day,
        //as the day changes with our onDateChangeListener function

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //We sort our recordings to display in order of when they were entered, if multiple entered on the same day
                recordingList.sort(new Comparator<Recording>() {
                    @Override
                    public int compare(Recording o1, Recording o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                viewSymps.setMovementMethod(new ScrollingMovementMethod());

                //We iterate through our Recordings list, and show all that match the date
                boolean found = false;
                String displaySymptoms = "";
                for(Recording r : recordingList)
                {
                    if(r.getDay() == day && r.getMonth() == month && r.getYear() == year)
                    {
                        displaySymptoms += r.toString();
                        found = true;
                    }
                }

                if(!found)
                {
                    viewSymps.setText("No symptoms recorded on " + day +"/"+month+"/"+year);
                }
                else
                {
                    viewSymps.setText(displaySymptoms);
                }
            }
        });
    }
}