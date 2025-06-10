package com.example.tothedestination;

//import static com.example.tothedestination.R.id.datePicker;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class after_deleteMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String keyFly;
    private TextView dateFromFinal, dateToFinal;
    private TextView countryFinal, hoursFinal, seasonFinal, airportFinal;
    private String ageOfChildrenFinal;
    private double coordinatesX, coordinatesY;
    private Button saveVac, map, moveForward;
    private String attraction;
    ArrayList<Attraction> attList, checkList;
    ListView lv;
    AttractionAdapter attAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_delete);

        //קבלת המפתח של הטיס השהמשתמש בחר
        keyFly = getIntent().getStringExtra("flyKey");

        //קישור המשתנים לערכים בxml
        countryFinal = findViewById(R.id.countryFinal);
        hoursFinal = findViewById(R.id.hoursFinal);
        seasonFinal = findViewById(R.id.seasonFinal);
        dateFromFinal = findViewById(R.id.dateFromFinal);
        dateToFinal = findViewById(R.id.dateToFinal);
        saveVac = findViewById(R.id.saveVac);
        airportFinal = findViewById(R.id.airportFinal);
        map = findViewById(R.id.map);
        moveForward = findViewById(R.id.moveForward);

        attList = new ArrayList<Attraction>();
        //קבלת הרשימות, ref
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference flyRef = database.getReference("flyList").child(keyFly);
        DatabaseReference attRef = database.getReference("attraction");

        //לעבור על הטיסה שהמשתמש בחר, לקבל את הנתונים שם ולהכניס למשתנים
        flyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot flyListDataSnapshot) {
                // Rule base ML הרבה תנאים
                Fly currentFlight = flyListDataSnapshot.getValue(Fly.class);
                countryFinal.setText(currentFlight.getCountry());
                hoursFinal.setText(Integer.toString(currentFlight.getHoursFlight()));
                seasonFinal.setText(currentFlight.getSeason());
                attraction =currentFlight.getAttraction();
                ageOfChildrenFinal = currentFlight.getAgeOfChild();
                airportFinal.setText(currentFlight.getAirport());
                coordinatesX = currentFlight.getCoordinatesX();
                coordinatesY = currentFlight.getCoordinatesY();
                SharedPreferences sharedPreferences = getSharedPreferences("myPref", 0);
                String dateFrom = sharedPreferences.getString("key_From", "Not Important");
                String dateTo = sharedPreferences.getString("key_To", "Not Important");

                dateFromFinal.setText(dateFrom);
                dateToFinal.setText(dateTo);

                //לקבל את attraction רשימה
                DataSnapshot attractionsSnapshot = flyListDataSnapshot.child("AttractionList");

                attList.clear();
                List<String> attractionKeys = new ArrayList<>();
                //attractionsSnapshow- מצביע על הרשימה attractionList הנמצאת בתוך הflyList
                //attractionKeySnapshow- מצביע כל לולאה על אטרקציה אחרת הנמצאת ברשימה attractionListflyList
                //עוברים פה על כל הרשימה attractionList ומכניסים את המפתחות שאנו צריכים של האטרציות לרשימה, יוצרים רשימה שתכיל את כל האטרקציות שצריך להראות בפני המשתמש
                for (DataSnapshot attractionKeySnapshot : attractionsSnapshot.getChildren()) {
                    attractionKeys.add(attractionKeySnapshot.getKey());
                }
                //עוברים על הרשימה attraction ומוצאים בה את כל האטרקציות שהכנסנו למערך attractionKey ומעדכנים את הadapter
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
        //שומרים בלחיצה על כפתור על הטיול
        saveVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVacation(countryFinal.getText().toString(), attraction,Integer.parseInt(hoursFinal.getText().toString()), ageOfChildrenFinal, seasonFinal.getText().toString(), convertStringToDate(dateFromFinal.getText().toString(), "MMM dd yyyy").getTime(), convertStringToDate(dateToFinal.getText().toString(), "MMM dd yyyy").getTime(), getIntent().getStringExtra("flyKey"), airportFinal.getText().toString(), coordinatesX, coordinatesY);
            }

        });
        //מציגים בפני המשתמש את הרשימה של האטרקציות המומלצות של אותה המדינה שנבחרה
        attAdapter = new AttractionAdapter(this, 0, 0, attList);
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(attAdapter);

        //בלחיצה על כפתור map עוברים על הרשימה של האטרקציות לראות איזה המשתמש בחר ומכניסים לרשימה-מערך ומעבירים אותה למסך הבא וגם את הקורדינציות של נמל התעופה והמשתמש עובר למסך המפה
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

        //לחיצה מעביר את המשתמש למסך הבא
        moveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(after_deleteMain.this, myTripsMain.class);
                //  intent.putExtra(checkList);
                startActivity(intent);
            }
        });
    }
    //כאשר המשתמש בחר  משהו מראה הערה
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
    //כאשר המשתמש לא בחר כלום
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //להפוך את הString לDate
    public static Date convertStringToDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null; // Or throw the exception if you prefer
        }
    }
    //שומר את הטיול שהמשתמש בחר, עם הנתונים שלו, מכניס את הטיול לרשימה vacation
    public void saveVacation(String country,String attraction, int hoursFlight, String ageOfChildren, String season, long dateFrom, long dateTo, String keyFly, String airport, double coordinatesX, double coordinatesY) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refVac = database.getReference("vacations");
        // searchUserByEmail(  ,sp1,refVac,);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        SharedPreferences sp1 = getSharedPreferences("myPref", 0);
        String userKey = sp1.getString("key_user", null);

        vacation vac1 = new vacation(country,attraction, hoursFlight, ageOfChildren, season, dateFrom, dateFrom, userKey, airport, coordinatesX, coordinatesY);
        DatabaseReference newRefVac = refVac.push();
        newRefVac.setValue(vac1);
        scheduleFlightNotification();
        Toast.makeText(this, "vacation added", Toast.LENGTH_SHORT).show();
    }
//---------------------------------------------------------------------------------------------------------
    //הצגת התראה בפני המשתמש
    @SuppressLint("ScheduleExactAlarm")
    public void scheduleFlightNotification() {
        //מקבלים את התאריך טיסה הלוך
        SharedPreferences sp1 = this.getSharedPreferences("myPref", 0);
        String dateStr = sp1.getString("key_From", null);

        if (dateStr == null) return;
        //יוצרים format
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
        try {
            //המרת String לDate
            Date flightDate = sdf.parse(dateStr);

            // לקבוע את ההתראה שתיהיה יום לפני תאריך הטיסה בשעה 9:00 בבוקר
            Calendar notifyTime = Calendar.getInstance();
            notifyTime.setTime(flightDate);
            notifyTime.add(Calendar.DAY_OF_YEAR, -1);
            notifyTime.set(Calendar.HOUR_OF_DAY, 9); // 9 AM
            notifyTime.set(Calendar.MINUTE, 0);
            notifyTime.set(Calendar.SECOND, 0);

            //בידקה שההתראה עובדת
            // Update for testing  ---------------
            //notifyTime.add(Calendar.SECOND, 5);
            /// --------------

            //אם הזמן שבו הייתה צריכה ההתראה עבר כבר אז אל תבצע אותה
            if (notifyTime.before(Calendar.getInstance())) {
                // Don't schedule if it's in the past
                return;
            }
            //pendingIntent- כאשר הגיעה השעה של ההתראה אז תשדר את הintent כדי להפעיל את ההתראה
            //ברגע שההתראה מצלצלת יש intent זה מעביר למסך notificationReciever
            //pendingIntent- מאפשר לאפליקציה להריץ קוד בעתיד,כאשר מקבלים שידור ישנו intent שמתבצע
            //100- מספר ייחודי המשמש להבדיל בין PendingIntent שונים
            //FLAG_UPDATE_CURRENT- אם כבר קיים pendingIntent עם אותו הrequestCode והintent אז שהוא יעדכן אותו במקום ליצור חדש
            //FLAG_IMMUTABLE- מציין שהintent לא ניתן לשינוי לאחר יצירתו
            Intent intent = new Intent(this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            //מתי בדיוק להפעיל את הקוד, דרך pendingIntent
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            //setExact- תזמון מדויק
            //AlarmManager.RTC_WAKEUP- תשתמש בשעון אמיתי של המכשיר(RTC) ותעיר את המכשיר אם צריך משינה
            //pendingIntent- הפעולה שתרצה לבצע באותו זמן(שידור לnotificationReceiver)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notifyTime.getTimeInMillis(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    //תפריט menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //אם המשתמש בוחר משהו מהmenu מה עושים
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.About_programmer)
        {
            Toast.makeText(this, "you selected About programmer", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.about_app)
        {
            Toast.makeText(this, "you selected About app", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_signout)
        {
            Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_exit)
        {
            Toast.makeText(this, "you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    }
