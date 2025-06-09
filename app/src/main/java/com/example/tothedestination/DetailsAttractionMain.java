package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DetailsAttractionMain extends AppCompatActivity {

    private EditText etAtName, etExplain, etCoordinatesX, etCoordinatesY;
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
        etCoordinatesX = findViewById(R.id.etCoordinatesX);
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
                etCoordinatesX.setText(Double.toString(currentAttraction.getCoordinatesX()));
                etCoordinatesY.setText(Double.toString(currentAttraction.getCoordinatesY()));

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //make me be able to change the information about the attraction and it changes it in the firebase as well
        saveChanges.setOnClickListener(v -> {
            String name = etAtName.getText().toString();
            String explain = etExplain.getText().toString();
            double etcoordinatesX = Double.parseDouble(etCoordinatesX.getText().toString());
            double etcoordinatesY = Double.parseDouble(etCoordinatesY.getText().toString());

            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("attName", name);
            updatedData.put("explain", explain);
            updatedData.put("CoordinatesX", etcoordinatesX);
            updatedData.put("CoordinatesY", etcoordinatesY);

            attRef.updateChildren(updatedData)
                    .addOnSuccessListener(unused -> Toast.makeText(DetailsAttractionMain.this, "Updated successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DetailsAttractionMain.this, "Update failed!", Toast.LENGTH_SHORT).show());
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem attractionListChange = menu.findItem(R.id.action_attractionListChange);
        MenuItem flyListChange = menu.findItem(R.id.action_flyListChange);
        SharedPreferences sp = getSharedPreferences("myPref", 0);

        boolean isAdmin = sp.getBoolean("CanEditAttraction",false); // replace with your condition
        attractionListChange.setVisible(isAdmin);
        flyListChange.setVisible(isAdmin);

        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.About_programmer)
        {
            Toast.makeText(this,"you selected About programmer", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_signout)
        {
            Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(DetailsAttractionMain.this, finalstartMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_attractionListChange)
        {
            Toast.makeText(this,"you selected change attraction list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsAttractionMain.this, MasterattractionMain.class);
            startActivity(intent);
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsAttractionMain.this, MasterFlyMain.class);
            startActivity(intent);
        }
        return true;
    }


}
