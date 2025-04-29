package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DetailsFlyMain extends AppCompatActivity {

    private EditText etCountry, etAirport, etAgeOfChild, etSeason, etHoursFlight, etAttraction, etCoordinatesX, etCoordinatesY;
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
        etCoordinatesX = findViewById(R.id.etCoordinatesX);
        etCoordinatesY = findViewById(R.id.etCoordinatesY);
        etCountry = findViewById(R.id.etFlyCountry);
        etHoursFlight = findViewById(R.id.etFlyHoursFlight);
        etSeason = findViewById(R.id.etFlySeason);
        etAgeOfChild = findViewById(R.id.etFlyAgeOfChild);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference flyReff = database.getReference("flyList").child(keyFly);

        flyReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot flyListDataSnapshot) {
                // Rule base ML הרבה תנאים
                Fly currentFly = flyListDataSnapshot.getValue(Fly.class);
                etAirport.setText(currentFly.getAirport());
                etAttraction.setText(currentFly.getAttraction());
                etCountry.setText(currentFly.getCountry());
                etHoursFlight.setText(currentFly.getHoursFlight());
                etSeason.setText(currentFly.getSeason());
                etAgeOfChild.setText(currentFly.getAgeOfChild());
                etCoordinatesX.setText(Double.toString(currentFly.getCoordinatesX()));
                etCoordinatesY.setText(Double.toString(currentFly.getCoordinatesY()));
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        saveChanges.setOnClickListener(v -> {
            String airport = etAirport.getText().toString();
            String attraction = etAttraction.getText().toString();
            String country = etCountry.getText().toString();
            String hoursFlight = etHoursFlight.getText().toString();
            String season = etSeason.getText().toString();
            String ageOfChild = etAgeOfChild.getText().toString();
            double etcoordinatesX = Double.parseDouble(etCoordinatesX.getText().toString());
            double etcoordinatesY = Double.parseDouble(etCoordinatesY.getText().toString());

            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("Airport", airport);
            updatedData.put("CoordinatesX", etcoordinatesX);
            updatedData.put("CoordinatesY", etcoordinatesY);
            updatedData.put("ageOfChild", ageOfChild);
            updatedData.put("attraction", attraction);
            updatedData.put("country", country);
            updatedData.put("hoursFlight", hoursFlight);
            updatedData.put("season", season);

            flyReff.updateChildren(updatedData)
                    .addOnSuccessListener(unused -> Toast.makeText(DetailsFlyMain.this, "Updated successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DetailsFlyMain.this, "Update failed!", Toast.LENGTH_SHORT).show());
        });



    }
}