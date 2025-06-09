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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyRef = database.getReference("flyList");

        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        flyList = new ArrayList<Fly>();
        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flyList.clear();

                Dictionary<String,Integer> countryMap = new Hashtable<String,Integer>();
                countryMap.put("Italy",R.drawable.country0);
                countryMap.put("Germany",R.drawable.country1);
                countryMap.put("Netherlands",R.drawable.country2);
                countryMap.put("France",R.drawable.country3);
                countryMap.put("Greece",R.drawable.country4);
                countryMap.put("Iceland",R.drawable.country5);
                countryMap.put("Belgium",R.drawable.country6);

                for (DataSnapshot flySnapshot : dataSnapshot.getChildren()) {
                    // Rule base

                    Fly currentFlight = flySnapshot.getValue(Fly.class);
                    if (countryMap.get(currentFlight.getCountry()) != null) {
                        imageRes = countryMap.get(currentFlight.getCountry());
                    } else {
                        imageRes = R.drawable.planedefultimage;
                    }
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageRes);
                    currentFlight.setBitmap(bitmap);
                    currentFlight.setKey(flySnapshot.getKey());
                    flyList.add(currentFlight);
                }
                flyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        flyAdapter = new FlyAdapter(this, 0, 0, flyList);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(flyAdapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Fly> checkedFlights = new ArrayList<>();
                for (Fly fly : flyList) {
                    if (fly.isChecked()) {
                        checkedFlights.add(fly);
                    }
                }
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                for (Fly flight : checkedFlights) {
                    String keyFly = flight.getKey(); // Replace with your actual getter

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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterFlyMain.this);
                builder.setTitle("Add New Flight");
                // Create layout for input
                LinearLayout layout = new LinearLayout(MasterFlyMain.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText airportInput = new EditText(MasterFlyMain.this);
                airportInput.setHint("Airport");
                layout.addView(airportInput);

                final EditText hoursInput = new EditText(MasterFlyMain.this);
                hoursInput.setHint("HoursFlight");
                hoursInput.setInputType(InputType.TYPE_CLASS_NUMBER); //makes sure there are no letters or symbols written
                layout.addView(hoursInput);

                final EditText ageOfChildInput = new EditText(MasterFlyMain.this);
                ageOfChildInput.setHint("ageOfChild");
                layout.addView(ageOfChildInput);

                final EditText attractionInput = new EditText(MasterFlyMain.this);
                attractionInput.setHint("attraction");
                layout.addView(attractionInput);

                final EditText seasonIntput = new EditText(MasterFlyMain.this);
                seasonIntput.setHint("season");
                layout.addView(seasonIntput);

                final EditText countryInput = new EditText(MasterFlyMain.this);
                countryInput.setHint("country");
                layout.addView(countryInput);

                final EditText CoordinatesXInput = new EditText(MasterFlyMain.this);
                CoordinatesXInput.setHint("CoordinatesX");
                layout.addView(CoordinatesXInput);

                final EditText CoordinatesYInput = new EditText(MasterFlyMain.this);
                CoordinatesYInput.setHint("CoordinatesY");
                layout.addView(CoordinatesYInput);


                builder.setView(layout);

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
                    addFlightToFirebase(airport, hoursFlight, CoordinatesX, CoordinatesY, ageOfChild, attraction, country, season);
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

    }

    private void addFlightToFirebase(String airport, int hoursFlight, double CoordinatesX, double CoordinatesY, String ageOfChild, String attraction, String country, String season) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("flyList");

        String flightKey = ref.push().getKey(); // Generate unique ID

        Fly newFlight = new Fly(hoursFlight, attraction, country, ageOfChild, season, flightKey, airport, CoordinatesX, CoordinatesY);

        ref.child(flightKey).setValue(newFlight)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Flight added!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding flight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

        boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
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
            Intent intent1 = new Intent(MasterFlyMain.this, finalstartMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
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
