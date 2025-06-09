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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

public class    FlyListMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Fly> flyList;
    ListView lv;
    FlyAdapter flyAdapter;
    //Random random = new Random();
    private SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fly_list);
        flyList = new ArrayList<Fly>();
        //  לייצור מילון בין שם מדינה לבין שם קובץ
        // נקבל את נתוני החיפוש מהמסך הקודם
        // עונה , סוג אטרקציות, טווח גילאים (תינוק, ילד, נוער) , כמות שעות טיסה
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference flyRef=database.getReference("flyList");

        // בונה "מנוע" לוגי שלפי המאפיינים משנה את הסינונים

        sp1=getSharedPreferences("myPref",0);
        int hoursFlight = sp1.getInt("key_hoursFlight", 0);
        String attraction = sp1.getString("key_attraction", "Not Important");
        String season = sp1.getString("key_season", "Not Important");
        String ageOfChildren = sp1.getString("key_ageOfChildren", "Not Important");

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
                        if (currentFlight.getHoursFlight() == hoursFlight || hoursFlight==0) {
                            if (attraction.equals(currentFlight.getAttraction()) || attraction.equals("Not Important")) {
                                if (season.equals(currentFlight.getSeason()) || season.equals("Not Important")) {
                                    if (ageOfChildren.equals(currentFlight.getAgeOfChild()) || ageOfChildren.equals("Not Important")) {
                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), countryMap.get(currentFlight.getCountry())); // Example: Load from resources
                                            currentFlight.setBitmap(bitmap);
                                            currentFlight.setKey(flySnapshot.getKey());
                                            flyList.add(currentFlight);
                                    }
                                }
                            }
                        }
                    flyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        flyAdapter=new FlyAdapter(this,0,0,flyList);
        lv=(ListView) findViewById(R.id.lv);
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
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}