package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class search1Main extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SharedPreferences sp1;
    private DatePickerDialog datePickerDialog;
    private Button dateButtonFrom;
    private Button dateButtonTo;
    private String whichCalenderWasClicked = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search1);
        initDatePicker();

        //קישור המשתנים לערכים בxml
        dateButtonFrom = findViewById(R.id.datePickerButtonFrom);
        dateButtonFrom.setText(getTodayDate());
        dateButtonTo = findViewById(R.id.datePickerButtonTo);
        dateButtonTo.setText(getTodayDate());
        Spinner hoursFlySpinner=findViewById(R.id.hoursFlySpinner);
        hoursFlySpinner.setOnItemSelectedListener(this);
        Spinner attractionSpinner=findViewById(R.id.attractionSpinner);
        attractionSpinner.setOnItemSelectedListener(this);
        Spinner seasonSpinner=findViewById(R.id.seasonSpinner);
        seasonSpinner.setOnItemSelectedListener(this);
        Spinner ageChildSpinner=findViewById(R.id.ageChildSpinner);
        ageChildSpinner.setOnItemSelectedListener(this);
        Button saveFeatures=findViewById(R.id.saveFeatures);

        sp1=getSharedPreferences("myPref",0);

        //בלחיצה על כפתור שומרים את המאפיינים שהמשתמש ההזין בshared preference+מעבר למסך הבא
        saveFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // נשמור את נתוני החיפוש
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("key_From",dateButtonFrom.getText().toString());
                editor.putString("key_To",dateButtonTo.getText().toString());
               // editor.putString("key_airport", attractionSpinner.getSelectedItem().toString());
                editor.putInt("key_hoursFlight", Integer.parseInt(hoursFlySpinner.getSelectedItem().toString()));
                editor.putString("key_attraction", attractionSpinner.getSelectedItem().toString());
                editor.putString("key_season", seasonSpinner.getSelectedItem().toString());
                editor.putString("key_ageOfChildren", ageChildSpinner.getSelectedItem().toString());
                editor.commit();
                Intent intent2=new Intent(search1Main.this, FlyListMain.class);
                startActivity(intent2);
            }
        });
    }

    //להפוך String לDate
    public static Date getDateFromString(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }

    //כאשר המשתמש בוחר משהו
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
    //כאשר המשתמש לא בוחר כלום
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    //תפריט menu
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //בודק האם המשתמש במשתמש בmenu הוא admin או משתמש רגיל
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem attractionListChange = menu.findItem(R.id.action_attractionListChange);
        MenuItem flyListChange = menu.findItem(R.id.action_flyListChange);
        SharedPreferences sp = getSharedPreferences("myPref", 0);

        boolean isAdmin = sp.getBoolean("CanEditAttraction",false); // replace with your condition
        //אם המשתמש admin הוא יראה גם את שני אילו
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
            Intent intent1 = new Intent(search1Main.this, informationProgrammerMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(search1Main.this, informationApplicationMain.class);
            startActivity(intent1);
        }
         else if (id == R.id.action_signout)
        {
            Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = new Intent(search1Main.this, finalstartMain.class);
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
            Intent intent = new Intent(search1Main.this, MasterattractionMain.class);
            startActivity(intent);
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(search1Main.this, MasterFlyMain.class);
            startActivity(intent);
        }
        return true;
    }

    //לקבל את התאריך של היום הנוכחי
    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        month = month + 1;
        int day= cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    //פותח את החלון של בחירת התאריך בפני המשתמש ובודק האם המשתמש בחר לשנות את התאריך הלוך או חזור, איזה כפתור הוא לחץ ושמנה אותו בהתאם
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if (whichCalenderWasClicked.equals("From"))
                    dateButtonFrom.setText(date);
                else
                    dateButtonTo.setText(date);
            }
        };
        //תאריך ברירת מחדל
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        //הגרה לעיצוב הdialog, מעניק מראה בהיר וקלאסי
        int style= AlertDialog.THEME_HOLO_LIGHT;
        //this- activity, syyle-סגנון, dateSetLisner- פועל אחרי שהמשתמש לוחץ אישור ומקבל את החודש שנה ויום שנבחרו, year+month+day- תאריכים של הרגע הנוכחי
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }
    //מקבל יום חודש ושנה ושם אותם בstring
    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    //להחזיר חודש
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }
    //בודקת האם המשתמש בחר את הכפתור של טיסה הלוך או חזור
    public void openDatePicker(View view)
    {
        if (view.getId() == R.id.datePickerButtonFrom)
            whichCalenderWasClicked = "From";
        else
            whichCalenderWasClicked = "To";

        datePickerDialog.show();

    }

}