package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class after_deleteMain extends AppCompatActivity {

    private LinearLayout calendarContainer;
    private Button toggleCalendarButton;
    private CalendarView calendarView;
    private boolean isCalendarVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_delete);

        // Initialize views
        calendarContainer = findViewById(R.id.calendarFromDateContainer);
        toggleCalendarButton = findViewById(R.id.toggleFromCalendarButton);
        calendarView = findViewById(R.id.calendarView);

        // Set calendar listener
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        // Toggle calendar visibility
        toggleCalendarButton.setOnClickListener(v -> toggleCalendar());

    }


    private void toggleCalendar() {
        if (isCalendarVisible) {
            // Close calendar
            Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            calendarContainer.startAnimation(slideUp);
            calendarContainer.setVisibility(View.GONE);
        } else {
            // Open calendar
            Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            calendarContainer.startAnimation(slideDown);
            calendarContainer.setVisibility(View.VISIBLE);
        }
        isCalendarVisible = !isCalendarVisible;
    }
}