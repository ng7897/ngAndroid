package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MasterFlyMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Fly> flyList, checkList;
    ListView lv;
    FlyAdapter flyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_fly);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyRef = database.getReference("flyList");

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
                    // ML הרבה תנאים
                    Fly currentFlight = flySnapshot.getValue(Fly.class);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), countryMap.get(currentFlight.getCountry())); // Example: Load from resources
                    currentFlight.setBitmap(bitmap);
                    currentFlight.setKey(flySnapshot.getKey());
                    flyList.add(currentFlight);
                    flyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        flyAdapter = new FlyAdapter(this, 0, 0, flyList);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(flyAdapter);

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

        // TODO: check Why CanEditAttraction

        boolean isAdmin = sp.getBoolean("CanEditAttraction",false); // replace with your condition
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
            Intent intent1=new Intent(MasterFlyMain.this, loginMain.class);
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
