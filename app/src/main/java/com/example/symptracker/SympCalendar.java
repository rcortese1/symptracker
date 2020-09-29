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

        //oh, put a textview in the XML, and when a date is selected, trigger onDateSelect method
        //then use that date to see if any symptoms in db match that date
        //if so, display them. else, display "no symps for date displayed"
        //looks like we can't incorp google calendar functionality easily :/
        //so we just make our own make-pretend calendar
        //can we highlight dates that have symptoms?
        //i know there's a function that changes date colours - NVM its deprecated

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //viewSymps.setText(""); //search for symptoms by date, and display all that match
                //maybe use !viewSymps.hasSelection() to check for null instead?
                //oh, or length() == 0
                //get symptom's Date object, convert it to GregorianCalendar, then use those methods to extract day month year
                //and then search thru database to find those that match. wait. shit. should i convert to gregorian first? but how do i store that. hmmmm.
                //A METHOD.

                recordingList.sort(new Comparator<Recording>() {
                    @Override
                    public int compare(Recording o1, Recording o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                viewSymps.setMovementMethod(new ScrollingMovementMethod());

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