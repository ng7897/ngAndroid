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

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MasterattractionMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Attraction> attList, checkList;
    ListView lv;
    AttractionAdapter attAdapter;
    private Button delete, add;
    private int imageRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_attraction);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference attRef = database.getReference("attraction");

        delete=findViewById(R.id.delete);
        add=findViewById(R.id.add);


        attList = new ArrayList<Attraction>();
        attRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attList.clear();

                for (DataSnapshot attSnapshot : dataSnapshot.getChildren())
                {
                    Attraction currentAttraction = attSnapshot.getValue(Attraction.class);
                    imageRes = R.drawable.planedefultimage;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageRes);
                    currentAttraction.setBitmap(bitmap);
                    attList.add(currentAttraction);
                }
                attAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        attAdapter=new AttractionAdapter(this,0,0,attList);
        lv=(ListView) findViewById(R.id.lv);
        lv.setAdapter(attAdapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Attraction> checkedFlights = new ArrayList<>();
                for (Attraction att : attList) {
                    if (att.isChecked()) {
                        checkedFlights.add(att);
                    }
                }
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                for (Attraction att1 : checkedFlights) {
                    String keyFly = att1.getKey(); // Replace with your actual getter

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterattractionMain.this);
                builder.setTitle("Add New Flight");
                // Create layout for input
                LinearLayout layout = new LinearLayout(MasterattractionMain.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText airportInput = new EditText(MasterattractionMain.this);
                airportInput.setHint("Airport");
                layout.addView(airportInput);

                final EditText hoursInput = new EditText(MasterattractionMain.this);
                hoursInput.setHint("HoursFlight");
                hoursInput.setInputType(InputType.TYPE_CLASS_NUMBER); //makes sure there are no letters or symbols written
                layout.addView(hoursInput);

                final EditText ageOfChildInput = new EditText(MasterattractionMain.this);
                ageOfChildInput.setHint("ageOfChild");
                layout.addView(ageOfChildInput);

                final EditText attractionInput = new EditText(MasterattractionMain.this);
                attractionInput.setHint("attraction");
                layout.addView(attractionInput);

                final EditText seasonIntput = new EditText(MasterattractionMain.this);
                seasonIntput.setHint("season");
                layout.addView(seasonIntput);

                final EditText countryInput = new EditText(MasterattractionMain.this);
                countryInput.setHint("country");
                layout.addView(countryInput);

                final EditText CoordinatesXInput = new EditText(MasterattractionMain.this);
                CoordinatesXInput.setHint("CoordinatesX");
                layout.addView(CoordinatesXInput);

                final EditText CoordinatesYInput = new EditText(MasterattractionMain.this);
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
        if(id==R.id.action_login)
        {
            Toast.makeText(this,"you selected login", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(MasterattractionMain.this, loginMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_setting)
        {
            Toast.makeText(this,"you selected setting", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.About_programmer)
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
            Intent intent1 = new Intent(MasterattractionMain.this, finalstartMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_attractionListChange)
        {
            Toast.makeText(this,"you selected change attraction list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MasterattractionMain.this, MasterattractionMain.class);
            startActivity(intent);
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MasterattractionMain.this, MasterFlyMain.class);
            startActivity(intent);
        }
        return true;
    }

}