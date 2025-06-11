package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class DetailsFlyMain extends AppCompatActivity {

    private EditText etCountry, etAirport, etAgeOfChild, etSeason, etHoursFlight, etAttraction, etCoordinatesX, etCoordinatesY;
    private String keyFly;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_fly);

        //קבלת המפתח של הטיסה שהמשתמש בחר
        keyFly = getIntent().getStringExtra("flyKey");

        //קישור המשתנים לערכים בxml
        etAirport = findViewById(R.id.etFlyAirport);
        etAttraction = findViewById(R.id.etFlyAttraction);
        saveChanges = findViewById(R.id.saveChanges);
        etCoordinatesX = findViewById(R.id.etCoordinatesX);
        etCoordinatesY = findViewById(R.id.etCoordinatesY);
        etCountry = findViewById(R.id.etFlyCountry);
        etHoursFlight = findViewById(R.id.etFlyHoursFlight);
        etSeason = findViewById(R.id.etFlySeason);
        etAgeOfChild = findViewById(R.id.etFlyAgeOfChild);

        //firebase, מצביע לflyList,לטיסה עם אותו המפתח שקיבלנו
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyReff = database.getReference("flyList").child(keyFly);
        //עובר על הנתונים של הטיסה שקיבלנו את המפתח שלה ומכניס אותם למשתנים, לשדות במטרה שהן יראו את הנתונים של הטיסה
        flyReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot flyListDataSnapshot) {
                // Rule base ML הרבה תנאים
                Fly currentFly = flyListDataSnapshot.getValue(Fly.class);
                etAirport.setText(currentFly.getAirport());
                etAttraction.setText(currentFly.getAttraction());
                etCountry.setText(currentFly.getCountry());
                etHoursFlight.setText(Integer.toString(currentFly.getHoursFlight()));
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
        //בלחיצה על כפתור שמירה הנתונים נשמרים, הדברים שהמששתמש שינה, ישנו עדכון של הנתונים
        saveChanges.setOnClickListener(v -> {
            String airport = etAirport.getText().toString();
            String attraction = etAttraction.getText().toString();
            String country = etCountry.getText().toString();
            String hoursFlight = etHoursFlight.getText().toString();
            String season = etSeason.getText().toString();
            String ageOfChild = etAgeOfChild.getText().toString();
            double etcoordinatesX = Double.parseDouble(etCoordinatesX.getText().toString());
            double etcoordinatesY = Double.parseDouble(etCoordinatesY.getText().toString());

            //יוצרים HashMap אשר שומרת את כל הנתונים וכל הדברים שכתובים בשדות לאחר השינוי
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("Airport", airport);
            updatedData.put("CoordinatesX", etcoordinatesX);
            updatedData.put("CoordinatesY", etcoordinatesY);
            updatedData.put("ageOfChild", ageOfChild);
            updatedData.put("attraction", attraction);
            updatedData.put("country", country);
            updatedData.put("hoursFlight", hoursFlight);
            updatedData.put("season", season);

            //משנים את הנתונים של הטיסה בהתאם לHashMap
            flyReff.updateChildren(updatedData)
                    .addOnSuccessListener(unused -> Toast.makeText(DetailsFlyMain.this, "Updated successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DetailsFlyMain.this, "Update failed!", Toast.LENGTH_SHORT).show());
        });
    }
    //תפריט menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //בדיקה האם המשתמש הוא admmin, אם כן יראה עוד אפשרויות בmenu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem attractionListChange = menu.findItem(R.id.action_attractionListChange);
        MenuItem flyListChange = menu.findItem(R.id.action_flyListChange);
        SharedPreferences sp = getSharedPreferences("myPref", 0);

        //בדיקה האם המשתמש הוא admin
        boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
        //אם כן זה יראה את שני אילו
        attractionListChange.setVisible(isAdmin);
        flyListChange.setVisible(isAdmin);

        return super.onPrepareOptionsMenu(menu);
    }
    //אם המשתמש בוחר משהו בmenu מה עושים
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
            Intent intent1 = new Intent(DetailsFlyMain.this, finalstartMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_attractionListChange)
        {
            Toast.makeText(this,"you selected change attraction list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsFlyMain.this, MasterattractionMain.class);
            startActivity(intent);
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DetailsFlyMain.this, MasterFlyMain.class);
            startActivity(intent);
        }
        return true;
    }

}