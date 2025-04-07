package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsFlyMain extends AppCompatActivity {

    private EditText etCountry, etAirport, etAgeOfChild, etSeason, etHoursFlight, etAttraction, etCoordiantesX, etCoordinatesY;
    private String keyFly;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_fly);

        keyFly = getIntent().getStringExtra("flyKey");

        etAirport = findViewById(R.id.etFlyAirport);
        etAttraction = findViewById(R.id.etFlyAttraction);
        saveChanges = findViewById(R.id.saveChanges);
        etCoordiantesX = findViewById(R.id.etCoordinatesX);
        etCoordinatesY = findViewById(R.id.etCoordinatesY);
        etCountry = findViewById(R.id.etFlyCountry);
        etHoursFlight = findViewById(R.id.etFlyHoursFlight);
        etSeason = findViewById(R.id.etFlySeason);
        etAgeOfChild = findViewById(R.id.etFlyAgeOfChild);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference flyReff = database.getReference("flyList").child(keyFly);
//
//        flyReff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot flyListDataSnapshot) {
//                // Rule base ML הרבה תנאים
//                Fly currentFly = flyListDataSnapshot.getValue(Fly.class);
//                etAirport.setText(currentFly.getAirport());
//                etAttraction.setText(currentFly.getAttraction());
//                etCountry.setText(currentFly.getCountry());
//                etHoursFlight.setText(currentFly.getHoursFlight());
//                etSeason.setText(currentFly.getSeason());
//                etAgeOfChild.setText(currentFly.getAgeOfChild());
//                etCoordiantesX.setText(Double.toString(currentFly.getCoordinatesX()));
//                etCoordinatesY.setText(Double.toString(currentFly.getCoordinatesY()));
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

    }
}