package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class myTripsMain extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    ArrayList<vacation> vacList;
    ListView lv;
    TripsAdapter tripsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_trips);

        vacList = new ArrayList<vacation>();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String userKey = getSharedPreferences("myPref",0).getString("key_user", null);
        Query userVacationQuery=database.getReference("vacations").orderByChild("keyUser").equalTo(userKey);


        userVacationQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vacList.clear();

                Dictionary<String,Integer> countryMap = new Hashtable<String,Integer>();
                countryMap.put("Italy",R.drawable.country0);
                countryMap.put("Germany",R.drawable.country1);
                countryMap.put("Netherlands",R.drawable.country2);
                countryMap.put("France",R.drawable.country3);
                countryMap.put("Greece",R.drawable.country4);
                countryMap.put("Iceland",R.drawable.country5);
                countryMap.put("Belgium",R.drawable.country6);

                for (DataSnapshot vacSnapshot : dataSnapshot.getChildren())
                {
                    vacation currentVac = vacSnapshot.getValue(vacation.class);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), countryMap.get(currentVac.getCountry())); // Example: Load from resources
                    currentVac.setBitmap(bitmap);
                    currentVac.setKey(vacSnapshot.getKey());
                    vacList.add(currentVac);
                    tripsAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        tripsAdapter=new TripsAdapter(this,0,0,vacList);
        lv=(ListView) findViewById(R.id.lv);
        lv.setAdapter(tripsAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}