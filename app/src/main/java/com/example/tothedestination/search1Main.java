package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.app.DatePickerDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class search1Main extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SharedPreferences sp1;
    private LinearLayout calendarContainer;
    private Button toggleCalendarButton;
    private boolean isCalendarVisible = false;
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

        // Initialize views
        dateButtonFrom = findViewById(R.id.datePickerButtonFrom);
        dateButtonFrom.setText(getTodayDate());

        dateButtonTo = findViewById(R.id.datePickerButtonTo);
        dateButtonTo.setText(getTodayDate());

        Spinner aSpinner1=findViewById(R.id.aSpinner1);
        aSpinner1.setOnItemSelectedListener(this);
        Spinner aSpinner2=findViewById(R.id.aSpinner2);
        aSpinner2.setOnItemSelectedListener(this);
        Spinner aSpinner3=findViewById(R.id.aSpinner3);
        aSpinner3.setOnItemSelectedListener(this);
        Spinner aSpinner4=findViewById(R.id.aSpinner4);
        aSpinner4.setOnItemSelectedListener(this);

        Button b=findViewById(R.id.button);
        sp1=getSharedPreferences("myPref",0);

//למלא את הנתונים של הdate וגם את הנתונים של המאפיינים האמיתייים.
      //  vacation vac1=new vacation(Integer.parseInt(aSpinner1.getSelectedItem().toString()),aSpinner2.getSelectedItem().toString(),aSpinner3.getSelectedItem().toString(),aSpinner4.getSelectedItem().toString(),getDateFromString(dateButtonTo.getText().toString(),"dd/MMM/yyyy" )  ,getDateFromString(dateButtonTo.getText().toString(),"dd/MMM/yyyy" ) );
      //  DatabaseReference newVacRef= vacRef.push();
       // newVacRef.setValue(vac1);


        TextView textView8=findViewById(R.id.pp);
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search1Main.this, MasterattractionMain.class);
                startActivity(intent);
            }
        });

        TextView textView9=findViewById(R.id.ppFly);
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search1Main.this, MasterFlyMain.class);
                startActivity(intent);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // נשמור את נתוני החיפוש
               /* if (aSpinner1.getSelectedItem() == null ||
                        aSpinner2.getSelectedItem() == null ||
                        aSpinner3.getSelectedItem() == null ||
                        aSpinner4.getSelectedItem() == null) {

                    Toast.makeText(search1Main.this, "Please select all options", Toast.LENGTH_SHORT).show();
                    return;
                }*/

//                SharedPreferences.Editor editor = sp1.edit();
//                editor.putString("key_hoursFlight",aSpinner1.getSelectedItem().toString());
//                editor.putString("key_attraction", aSpinner2.getSelectedItem().toString());
//                editor.putString("key_season", aSpinner3.getSelectedItem().toString());
//                editor.putString("key_ageOfChildren", aSpinner4.getSelectedItem().toString());
//                editor.commit();

                String a=aSpinner3.getSelectedItem().toString();

              //  intent2.putExtra("hoursFlight",aSpinner1.getSelectedItem().toString());
              //  intent2.putExtra("attraction", aSpinner2.getSelectedItem().toString());
             //   intent2.putExtra("season", aSpinner3.getSelectedItem().toString());
             //   intent2.putExtra("ageOfChildren", aSpinner4.getSelectedItem().toString());
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("key_From",dateButtonFrom.getText().toString());
                editor.putString("key_To",dateButtonTo.getText().toString());
               // editor.putString("key_airport", aSpinner2.getSelectedItem().toString());
                editor.putInt("key_hoursFlight", Integer.parseInt(aSpinner1.getSelectedItem().toString()));
                editor.putString("key_attraction", aSpinner2.getSelectedItem().toString());
                editor.putString("key_season", aSpinner3.getSelectedItem().toString());
                editor.putString("key_ageOfChildren", aSpinner4.getSelectedItem().toString());
                editor.commit();
                Intent intent2=new Intent(search1Main.this, FlyListMain.class);
                startActivity(intent2);
            }

        });

    }


    public static Date getDateFromString(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
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
        // For example, hide if user is not admin
//        boolean hadDataAdmin1=sp.getBoolean("hasDataAdmin",false);
//        if(hadDataAdmin1)
//        {
//            attractionListChange.setVisible(hadDataAdmin1);
//            flyListChange.setVisible(hadDataAdmin1);
//        }
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
            Intent intent1=new Intent(search1Main.this, loginMain.class);
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
        }
        else if(id==R.id.action_attractionListChange)
        {
            Toast.makeText(this,"you selected change attraction list", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_flyListChange)
        {
            Toast.makeText(this,"you selected change fly list", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private String getTodayDate()
    {
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        month = month + 1;
        int day= cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                if (whichCalenderWasClicked == "From")
                    dateButtonFrom.setText(date);
                else
                    dateButtonTo.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        int style= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

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
    public void openDatePicker(View view)
    {
        if (view.getId() == R.id.datePickerButtonFrom)
            whichCalenderWasClicked = "From";
        else
            whichCalenderWasClicked = "To";

        datePickerDialog.show();

    }

}