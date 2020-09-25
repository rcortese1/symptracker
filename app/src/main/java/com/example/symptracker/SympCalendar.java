package com.example.symptracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class SympCalendar extends AppCompatActivity {

    private CalendarView calendar;
    private TextView viewSymps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symp_calendar);

        calendar = findViewById(R.id.calendarView);
        viewSymps = findViewById(R.id.sympsOnDate);

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
                viewSymps.setText(""); //search for symptoms by date, and display all that match
                //maybe use !viewSymps.hasSelection() to check for null instead?
                //oh, or length() == 0
                if(viewSymps.getText() == "")
                {
                    viewSymps.setText("No symptoms recorded on " + day +"/"+month+"/"+year);
                }
            }
        });
    }
}