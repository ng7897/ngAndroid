package com.example.tothedestination;

//import static com.example.tothedestination.R.id.datePicker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class after_deleteMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String keyFly;
    private TextView dateFromFinal, dateToFinal;
    private TextView countryFinal, hoursFinal, seasonFinal, airportFinal;
    private String ageOfChildrenFinal;
    private double coordinatesX, coordinatesY;
    private Button saveVac, map, moveForward;
    ArrayList<Attraction> attList, checkList;
    ListView lv;
    AttractionAdapter attAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_delete);

        keyFly = getIntent().getStringExtra("flyKey");

        countryFinal = findViewById(R.id.countryFinal);
        hoursFinal = findViewById(R.id.hoursFinal);
        seasonFinal = findViewById(R.id.seasonFinal);
        dateFromFinal = findViewById(R.id.dateFromFinal);
        dateToFinal = findViewById(R.id.dateToFinal);
        saveVac = findViewById(R.id.saveVac);
        airportFinal = findViewById(R.id.airportFinal);
        //LinearLayout checkBox= findViewById(R.id.checkBox);
        map = findViewById(R.id.map);
        moveForward = findViewById(R.id.moveForward);
        attList = new ArrayList<Attraction>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyRef = database.getReference("flyList").child(keyFly);

        DatabaseReference attRef = database.getReference("attraction");

        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot flyListDataSnapshot) {
                // Rule base ML הרבה תנאים
                Fly currentFlight = flyListDataSnapshot.getValue(Fly.class);
                countryFinal.setText(currentFlight.getCountry());
                hoursFinal.setText(Integer.toString(currentFlight.getHoursFlight()));
                seasonFinal.setText(currentFlight.getSeason());
                ageOfChildrenFinal = currentFlight.getAgeOfChild();
                airportFinal.setText(currentFlight.getAirport());
                coordinatesX = currentFlight.getCoordinatesX();
                coordinatesY = currentFlight.getCoordinatesY();
                SharedPreferences sharedPreferences = getSharedPreferences("myPref", 0);
                String dateFrom = sharedPreferences.getString("key_From", "Not Important");
                String dateTo = sharedPreferences.getString("key_To", "Not Important");

                dateFromFinal.setText(dateFrom);
                dateToFinal.setText(dateTo);

                DataSnapshot attractionsSnapshot = flyListDataSnapshot.child("AttractionList");
                // Get the attraction keys
                attList.clear();
                List<String> attractionKeys = new ArrayList<>();
                for (DataSnapshot attractionKeySnapshot : attractionsSnapshot.getChildren()) {
                    attractionKeys.add(attractionKeySnapshot.getKey());
                }
                for (int i = 0; i < attractionKeys.size(); i++) {
                    DatabaseReference attractionRef = attRef.child(attractionKeys.get(i));
                    attractionRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Attraction currentAttraction = dataSnapshot.getValue(Attraction.class);
                            attList.add(currentAttraction);
                            attAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        saveVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVacation(countryFinal.getText().toString(), Integer.parseInt(hoursFinal.getText().toString()), ageOfChildrenFinal, seasonFinal.getText().toString(), convertStringToDate(dateFromFinal.getText().toString(), "MMM dd yyyy").getTime(), convertStringToDate(dateToFinal.getText().toString(), "MMM dd yyyy").getTime(), getIntent().getStringExtra("flyKey"), airportFinal.getText().toString(), coordinatesX, coordinatesY);
            }

        });
        attAdapter=new AttractionAdapter(this,0,0,attList);
        lv=(ListView) findViewById(R.id.lv);
        lv.setAdapter(attAdapter);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Attraction> checkedAttractions = new ArrayList<>();
                for (Attraction attraction : attList) {
                    if (attraction.isChecked()) {
                        checkedAttractions.add(attraction);
                    }
                }
                Intent intent = new Intent(after_deleteMain.this, MapsActivity.class);
                intent.putExtra("coordinatesX", coordinatesX);
                intent.putExtra("coordinatesY", coordinatesY);
                intent.putExtra("airport", airportFinal.getText().toString());
                // Pass the attList as an extra
                intent.putExtra("attList", checkedAttractions);
                startActivity(intent);

            }
        });



        moveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(after_deleteMain.this, myTripsMain.class);
              //  intent.putExtra(checkList);
                startActivity(intent);
            }
        });

//        attRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                attList.clear();
//
//                // Rule base ML הרבה תנאים
//                for (DataSnapshot attSnapshot : dataSnapshot.getChildren())
//                {
//                    attraction currentAttraction = attSnapshot.getValue(attraction.class);
//                    attList.add(currentAttraction);
//                    attAdapter.notifyDataSetChanged();
//                    //france, spring:3,1,2,4,6
//                    //netherlands: 5,
//                }
//
////            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//        attAdapter=new AttractionAdapter(this,0,0,attList);
//        lv=(ListView) findViewById(R.id.lv);
//        lv.setAdapter(attAdapter);
//    }

//        TextView textView8=findViewById(R.id.pp);
//        textView8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(after_deleteMain.this, MasterattractionMain.class);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static Date convertStringToDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null; // Or throw the exception if you prefer
        }
    }
public void saveVacation(String country, int hoursFlight, String ageOfChildren, String season, long dateFrom, long dateTo, String keyFly, String airport, double coordinatesX, double coordinatesY) {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refVac = database.getReference("vacations");
    // searchUserByEmail(  ,sp1,refVac,);
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    SharedPreferences sp1=getSharedPreferences("myPref",0);
    String userKey = sp1.getString("key_user", null);

    vacation vac1 = new vacation(country, hoursFlight, ageOfChildren, season, dateFrom, dateFrom, userKey, airport, coordinatesX, coordinatesY);
    DatabaseReference newRefVac= refVac.push();
    newRefVac.setValue(vac1);

    Toast.makeText(this,"vacation added", Toast.LENGTH_SHORT).show();
}



    }
