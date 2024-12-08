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
import java.util.Random;

public class    FlyListMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Fly> flyList;
    ListView lv;
    FlyAdapter flyAdapter;
    Random random = new Random();
    private SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fly_list);
        flyList = new ArrayList<Fly>();

        // אפשרות לייצור מילון בין שם מדינה לבין שם קובץ

        // נקבל את נתוני החיפוש מהמסך הקודם


        // עונה , סוג אטרקציות, טווח גילאים (תינוק, ילד, נוער) , כמות שעות טיסה
        //מדינה אני לא צריכה להשוות לשמהו בעמוד הקודם משום שהמשתמש בוחר אותה בסופו של דבר

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference flyRef=database.getReference("flyList");

        // בונה "מנוע" לוגי שלפי המאפיינים משנה את הסינונים

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int hoursFlight = sharedPreferences.getInt("key_hoursFlight", 0);
        String attraction = sharedPreferences.getString("key_attraction", "");
        String season = sharedPreferences.getString("key_season", "");
        String ageOfChildren = sharedPreferences.getString("key_ageOfChildren", "");

        //Query query = flyRef.orderByChild("country").equalTo("Belgium");

        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flyList.clear();
                for (DataSnapshot flySnapshot : dataSnapshot.getChildren()) {
                    // Rule base ML הרבה תנאים
                    Fly currentFlight = flySnapshot.getValue(Fly.class);
                        if (currentFlight.getHoursFlight() == hoursFlight) {
                            if (attraction.equals(currentFlight.getAttraction())) {
                                if (season.equals(currentFlight.getSeason())) {
                                    if (ageOfChildren.equals(currentFlight.getAgeOfChild())) {
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

       /*for(int i=0;i<7;i++)
        {
           int randomNumber = random.nextInt(24) + 1;
           Fly fly1=new Fly(randomNumber,"hiking","french","teen","summer");
            flyList.add(fly1);
            DatabaseReference newFlyRef= flyRef.push();
            newFlyRef.setValue(fly1);
        }*/

       /* Bitmap country0= BitmapFactory.decodeResource(getResources(),R.drawable.country0);
        Bitmap country1= BitmapFactory.decodeResource(getResources(),R.drawable.country1);
        Bitmap country2= BitmapFactory.decodeResource(getResources(),R.drawable.country2);
        Bitmap country3= BitmapFactory.decodeResource(getResources(),R.drawable.country3);
        Bitmap country4= BitmapFactory.decodeResource(getResources(),R.drawable.country4);
        Bitmap country5= BitmapFactory.decodeResource(getResources(),R.drawable.country5);
        Bitmap country6= BitmapFactory.decodeResource(getResources(),R.drawable.country6);

        Fly f1=new Fly(5,"hiking", "italy",country0);
        Fly f2=new Fly(8,"shopping", "germany",country1);
        Fly f3=new Fly(11,"cheese factory", "netherland",country2);
        Fly f4=new Fly(6,"hiking", "french",country3);
        Fly f5=new Fly(5,"beach", "ostria",country4);
        Fly f6=new Fly(4,"hiking", "spain",country5);
        Fly f7=new Fly(2,"hiking", "greece",country6);

        flyList= new ArrayList<Fly>();
        flyList.add(f1);
        flyList.add(f2);
        flyList.add(f3);
        flyList.add(f4);
        flyList.add(f5);
        flyList.add(f6);
        flyList.add(f7);*/

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
        if(id==R.id.action_login)
        {
            Toast.makeText(this,"you selected login", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(FlyListMain.this, loginMain.class);
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
        else if(id==R.id.fav_flights)
        {
            Toast.makeText(this,"you selected Favorite Flights", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}