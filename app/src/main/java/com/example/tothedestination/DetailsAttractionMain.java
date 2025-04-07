package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class DetailsAttractionMain extends AppCompatActivity {

    private EditText etAtName, etExplain, etCoordiantesX, etCoordinatesY;
    private String keyAtt;
    private Button saveChanges;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_attraction);

        keyAtt = getIntent().getStringExtra("attKey");

        etAtName = findViewById(R.id.etAtName);
        etExplain = findViewById(R.id.etExplain);
        saveChanges = findViewById(R.id.saveChanges);
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

//        saveChanges.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = etAtName.getText().toString();
//                String location = etExplain.getText().toString().trim();
//                String description = etCoordiantesX.getText().toString().trim();
//                String rating = etCoordinatesY.getText().toString().trim();
//
//                if (name.isEmpty() || location.isEmpty()) {
//                    Toast.makeText(DetailsAttractionMain.this, "Name and location required", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Map<String, Object> updates = new HashMap<>();
//                updates.put("name", name);
//                updates.put("location", location);
//                updates.put("description", description);
//                updates.put("rating", rating);
//
//                attRef.updateChildren(updates).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Successfully updated the data in Firebase
//                        Toast.makeText(DetailsAttractionMain.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Failed to update data
//                        Toast.makeText(DetailsAttractionMain.this, "Failed to update data", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//       });


    }

}
