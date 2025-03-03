package com.example.tothedestination;

//import static com.example.tothedestination.R.id.datePicker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class after_deleteMain extends AppCompatActivity {

    private String keyFly;
    private TextView countryFinal, hoursFinal, seasonFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_delete);

        keyFly=getIntent().getStringExtra("flyKey");
        countryFinal=findViewById(R.id.countryFinal);
        hoursFinal=findViewById(R.id.hoursFinal);
        seasonFinal=findViewById(R.id.seasonFinal);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference flyRef=database.getReference("flyList");

        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot flySnapshot : dataSnapshot.getChildren()) {
                    // Rule base ML הרבה תנאים
                    Fly currentFlight = flySnapshot.getValue(Fly.class);
                    if (currentFlight.getKey() == keyFly) {
                        countryFinal.setText(currentFlight.getCountry());
                        hoursFinal.setText(currentFlight.getHoursFlight());
                        seasonFinal.setText(currentFlight.getSeason());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }




    }
