package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MasterFlyMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Fly> flyList;
    private ListView lv;
    private FlyAdapter flyAdapter;
    private Button delete, add;
    private int imageRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_fly);

        //firebase מצביע לרשימה flyList
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyRef = database.getReference("flyList");

        //קישור בין המשתנים לערכים בxml
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        //יצירת רשימה ריקה בה נכניס את הרשימת טיסות
        flyList = new ArrayList<Fly>();
        //לעבור על רשימת הטיסות ולהציג אותה בפני המשתמש
        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flyList.clear();

                //ליצור מילון שיקשר בין המדינה לתמונה
                Dictionary<String,Integer> countryMap = new Hashtable<String,Integer>();
                countryMap.put("Italy",R.drawable.country0);
                countryMap.put("Germany",R.drawable.country1);
                countryMap.put("Netherlands",R.drawable.country2);
                countryMap.put("France",R.drawable.country3);
                countryMap.put("Greece",R.drawable.country4);
                countryMap.put("Iceland",R.drawable.country5);
                countryMap.put("Belgium",R.drawable.country6);
                //לעבור על כל רשימת הטיסות
                //flySnapshow- טיסה אחת
                //dataSnapshot- רשימת הטיסות, מצביע
                for (DataSnapshot flySnapshot : dataSnapshot.getChildren()) {
                    // Rule base
                    //הכנסת התמונות לטיסה, למדינה, אם אין תמונה במילון אז תיכנס תמונה default
                    Fly currentFlight = flySnapshot.getValue(Fly.class);
                    if (countryMap.get(currentFlight.getCountry()) != null) {
                        imageRes = countryMap.get(currentFlight.getCountry());
                    } else {
                        imageRes = R.drawable.planedefultimage;
                    }
                    //הוספת התמונה לרשימה
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageRes);
                    currentFlight.setBitmap(bitmap);
                    //שינוי המפתח מכיוון שהוא null
                    currentFlight.setKey(flySnapshot.getKey());
                    flyList.add(currentFlight);
                }
                //עדכנת הadapter
                flyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //הצגת הרשימה בפני המשתמש
        flyAdapter = new FlyAdapter(this, 0, 0, flyList);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(flyAdapter);

        //בלחיצה על הכפתור מחיקה הadmin יכול לבחור איזו מדינות הוא רוצה למחוק
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ליצור רשימה-מערך בה נכיל את המדינות אשר הadmin סימן ורוצה למחוק
                ArrayList<Fly> checkedFlights = new ArrayList<>();
                //נעבור על רשימת הטיסות ונבדוק האם המשתמש סימן את הcheckBox שלהן, אם כן נוסיף לרשימה-מערך
                for (Fly fly : flyList) {
                    if (fly.isChecked()) {
                        checkedFlights.add(fly);
                    }
                }
                //לעבור על הרשימה-מערך שיצרנו
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                for (Fly flight : checkedFlights) {
                    //קבלת המפתח של הטיסה שרוצים למחוק
                    String keyFly = flight.getKey(); // Replace with your actual getter
                    //מחיקת הטיסה
                    database.getReference("flyList").child(keyFly).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Log.d("DeleteFlight", "Flight deleted: " + keyFly);
                            })
                            .addOnFailureListener(e -> {
                                Log.w("DeleteFlight", "Error deleting flight: " + keyFly, e);
                            });
                }

            }
        });
        //בלחיצה על הכפתור הוספה הadmin יכול להוסיף טיסה
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //builder כדי ליצור כותרת Add New Flight,כאשר נוסיף טיסה
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterFlyMain.this);
                builder.setTitle("Add New Flight");
                // Create layout for input
                //איך תוצג כרשימה,layout
                LinearLayout layout = new LinearLayout(MasterFlyMain.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                //הצגת שדה וכותרת בו הadmin צריך להוסיף נמל תעופה
                final EditText airportInput = new EditText(MasterFlyMain.this);
                airportInput.setHint("Airport");
                layout.addView(airportInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף שעות טיסה
                final EditText hoursInput = new EditText(MasterFlyMain.this);
                hoursInput.setHint("HoursFlight");
                hoursInput.setInputType(InputType.TYPE_CLASS_NUMBER); //makes sure there are no letters or symbols written
                layout.addView(hoursInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף גילאי הילדים
                final EditText ageOfChildInput = new EditText(MasterFlyMain.this);
                ageOfChildInput.setHint("ageOfChild");
                layout.addView(ageOfChildInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף סוג האטרקציה
                final EditText attractionInput = new EditText(MasterFlyMain.this);
                attractionInput.setHint("attraction");
                layout.addView(attractionInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף עונה
                final EditText seasonIntput = new EditText(MasterFlyMain.this);
                seasonIntput.setHint("season");
                layout.addView(seasonIntput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף מדינה
                final EditText countryInput = new EditText(MasterFlyMain.this);
                countryInput.setHint("country");
                layout.addView(countryInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף קורדינציות לנמל תעופה ציר x
                final EditText CoordinatesXInput = new EditText(MasterFlyMain.this);
                CoordinatesXInput.setHint("CoordinatesX");
                layout.addView(CoordinatesXInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף קורדינציות לנמל תעופה ציר y
                final EditText CoordinatesYInput = new EditText(MasterFlyMain.this);
                CoordinatesYInput.setHint("CoordinatesY");
                layout.addView(CoordinatesYInput);

                //יצירת הטיסה
                builder.setView(layout);

                //הכנסת הנתונים שהadmin ההזין למשתנים
                builder.setPositiveButton("Add", (dialog, which) -> {
                    String airport = airportInput.getText().toString().trim();
                    String ageOfChild = hoursInput.getText().toString().trim();
                    String attraction = attractionInput.getText().toString().trim();
                    String country = countryInput.getText().toString().trim();
                    String season = seasonIntput.getText().toString().trim();
                    String yString = CoordinatesYInput.getText().toString().trim();
                    Double CoordinatesY = Double.parseDouble(yString);
                    String xString = CoordinatesXInput.getText().toString().trim();
                    Double CoordinatesX = Double.parseDouble(xString);
                    int hoursFlight = Integer.parseInt(hoursInput.getText().toString().trim());
                    //הוספת הטיסה לfirebase, שמירה
                    addFlightToFirebase(airport, hoursFlight, CoordinatesX, CoordinatesY, ageOfChild, attraction, country, season);
                });
                //אם לא עובד
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

    }
    //הוספת הטיסה לfirebase
    private void addFlightToFirebase(String airport, int hoursFlight, double CoordinatesX, double CoordinatesY, String ageOfChild, String attraction, String country, String season) {
        //firebase מצביע לרשימה flyList
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("flyList");

        //קבלת המפתח של הטיסה
        String flightKey = ref.push().getKey(); // Generate unique ID
        //יצירת הטיסה עם כל המאפיינים
        Fly newFlight = new Fly(hoursFlight, attraction, country, ageOfChild, season, flightKey, airport, CoordinatesX, CoordinatesY);
        //עידכון הנתונים של הטיסה לנכונים מnull
        ref.child(flightKey).setValue(newFlight)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Flight added!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding flight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    //אם המשתמש בחר משהו מציג הערה
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
    //אם המשתמש לא בחר משהו
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //תפריט menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //בודק האם המשתמש הוא admin אם כן אז מראה גם עוד כמה דברים והאפשרויות בmenu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem attractionListChange = menu.findItem(R.id.action_attractionListChange);
        MenuItem flyListChange = menu.findItem(R.id.action_flyListChange);
        SharedPreferences sp = getSharedPreferences("myPref", 0);
        //בודק האם המשתמש הוא admin
        boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
        //אם המשתמש הוא admin מציג את אילו גם
        attractionListChange.setVisible(isAdmin);
        flyListChange.setVisible(isAdmin);

        return super.onPrepareOptionsMenu(menu);
    }
    //אם המשתמש בחר משהו מהmenu מה עושים
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.About_programmer)
        {
            Toast.makeText(this,"you selected About programmer", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MasterFlyMain.this, informationProgrammerMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MasterFlyMain.this, informationApplicationMain.class);
            startActivity(intent1);
        }
        else if (id == R.id.action_signout)
        {
            Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(MasterFlyMain.this, finalstartMain.class);
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
            Intent intent = new Intent(MasterFlyMain.this, MasterattractionMain.class);
            startActivity(intent);
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MasterFlyMain.this, MasterFlyMain.class);
            startActivity(intent);
        }
        return true;
    }
}
