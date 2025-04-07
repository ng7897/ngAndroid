package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

public class DetailsAttractionMain extends AppCompatActivity {

    private EditText etAtName, etExplain, etCoordiantesX, etCoordinatesY;
    private String keyAtt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_attraction);

        keyAtt = getIntent().getStringExtra("attKey");

        if (keyAtt == null) {
            Toast.makeText(this, "Error: Attraction key is missing!", Toast.LENGTH_LONG).show();
            finish(); // Stop the activity so it doesn't crash
            return;
        }

        etAtName = findViewById(R.id.etAtName);
        etExplain = findViewById(R.id.etExplain);
        etCoordiantesX = findViewById(R.id.etCoordinatesX);
        etCoordinatesY = findViewById(R.id.etCoordinatesY);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference attRef = database.getReference("attList").child(keyAtt);

        DatabaseReference attRef = database.getReference("attraction").child(keyAtt);


        attRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot attListDataSnapshot) {
                // Rule base ML הרבה תנאים
                Attraction currentAttraction = attListDataSnapshot.getValue(Attraction.class);
                etAtName.setText(currentAttraction.getAttName());
                etExplain.setText(currentAttraction.getExplain());
                etCoordiantesX.setText(Double.toString(currentAttraction.getCoordinatesX()));
                etCoordinatesY.setText(Double.toString(currentAttraction.getCoordinatesY()));

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}