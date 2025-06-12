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

        //קבלת המפתח של האטרקציה שהמשתמש בחר
        keyAtt = getIntent().getStringExtra("attKey");

        //קישור המשתנים לערכים בxml
        etAtName = findViewById(R.id.etAtName);
        etExplain = findViewById(R.id.etExplain);
        saveChanges = findViewById(R.id.saveChanges);
        etCoordinatesX = findViewById(R.id.etCoordinatesX);
        etCoordinatesY = findViewById(R.id.etCoordinatesY);

        //firebase, מצביע attraction,לטיסה עם אותו המפתח שקיבלנו
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference attRef = database.getReference("attraction").child(keyAtt);
        //עובר על הנתונים של האטרקציה שקיבלנו את המפתח שלה ומכניס אותם למשתנים, לשדות במטרה שהן יראו את הנתונים של האטרקציה
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

        //בלחיצה על כפתור שמירה הנתונים נשמרים, הדברים שהמששתמש שינה, ישנו עדכון של הנתונים
        saveChanges.setOnClickListener(v -> {
            String name = etAtName.getText().toString();
            String explain = etExplain.getText().toString();
            double etcoordinatesX = Double.parseDouble(etCoordinatesX.getText().toString());
            double etcoordinatesY = Double.parseDouble(etCoordinatesY.getText().toString());

            //יוצרים HashMap אשר שומרת את כל הנתונים וכל הדברים שכתובים בשדות לאחר השינוי
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("attName", name);
            updatedData.put("explain", explain);
            updatedData.put("CoordinatesX", etcoordinatesX);
            updatedData.put("CoordinatesY", etcoordinatesY);

            //משנים את הנתונים של האטרקציה בהתאם לHashMap
            attRef.updateChildren(updatedData)
                    .addOnSuccessListener(unused -> Toast.makeText(DetailsAttractionMain.this, "Updated successfully!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(DetailsAttractionMain.this, "Update failed!", Toast.LENGTH_SHORT).show());
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
            Intent intent1 = new Intent(DetailsAttractionMain.this, informationProgrammerMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(DetailsAttractionMain.this, informationApplicationMain.class);
            startActivity(intent1);
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
            finishAffinity(); // סוגר את כל ה-Activities
            System.exit(0);   // עוצר את תהליך האפליקציה
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
