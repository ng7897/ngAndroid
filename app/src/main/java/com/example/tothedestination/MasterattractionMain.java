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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_attraction);

        //firebase מצביע לרשימה attraction
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference attRef = database.getReference("attraction");

        //קישור בין המשתנים לערכים בxml
        delete=findViewById(R.id.delete);
        add=findViewById(R.id.add);

        //יצירת רשימה ריקה בה נכניס את הרשימת האטרקציות
        attList = new ArrayList<Attraction>();
        //לעבור על רשימת האטרקציות ולהציג אותה בפני המשתמש
        attRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attList.clear();
                //לעבור על כל רשימת האטרקציות
                //attSnapshow- אטרקציה אחת
                //dataSnapshot- רשימת האטרקציות, מצביע
                for (DataSnapshot attSnapshot : dataSnapshot.getChildren())
                {
                    //הכנסת האטרקציה לרשימה-מערך
                    Attraction currentAttraction = attSnapshot.getValue(Attraction.class);
                    //שינוי המפתח מכיוון שהוא null
                    currentAttraction.setKey(attSnapshot.getKey());
                    attList.add(currentAttraction);
                    //עדכון הadapter
                    attAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        //הצגת הרשימה בפני המשתמש
        attAdapter=new AttractionAdapter(this,0,0,attList);
        lv=(ListView) findViewById(R.id.lv);
        lv.setAdapter(attAdapter);

        //בלחיצה על הכפתור מחיקה הadmin יכול לבחור איזו האטרקציות הוא רוצה למחוק
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ליצור רשימה-מערך בה נכיל את האטרקציות אשר הadmin סימן ורוצה למחוק
                ArrayList<Attraction> checkedAttractions = new ArrayList<>();
                //נעבור על רשימת האטרקציות ונבדוק האם המשתמש סימן את הcheckBox שלהן, אם כן נוסיף לרשימה-מערך
                for (Attraction att : attList) {
                    if (att.isChecked()) {
                        checkedAttractions.add(att);
                    }
                }
                //לעבור על הרשימה-מערך שיצרנו
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                for (Attraction att1 : checkedAttractions) {
                    //קבלת המפתח של האטרקציה שרוצים למחוק
                    String keyAtt = att1.getKey(); // Replace with your actual getter
                    //מחיקת האטרקציה
                    database.getReference("attraction").child(keyAtt).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Log.d("DeleteFlight", "Flight deleted: " + keyAtt);
                            })
                            .addOnFailureListener(e -> {
                                Log.w("DeleteFlight", "Error deleting flight: " + keyAtt, e);
                            });
                }

            }
        });
        //בלחיצה על הכפתור הוספה הadmin יכול להוסיף אטרקציה
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //builder כדי ליצור כותרת Add New Flight,כאשר נוסיף אטרקציה
                AlertDialog.Builder builder = new AlertDialog.Builder(MasterattractionMain.this);
                builder.setTitle("Add New Attraction");
                // Create layout for input
                //איך תוצג כרשימה,layout
                LinearLayout layout = new LinearLayout(MasterattractionMain.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                //הצגת שדה וכותרת בו הadmin צריך להוסיף שם האטרקציה
                final EditText attNameInput = new EditText(MasterattractionMain.this);
                attNameInput.setHint("attName");
                layout.addView(attNameInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף הסבר על האטרקציה
                final EditText explainInput = new EditText(MasterattractionMain.this);
                explainInput.setHint("explain");
                layout.addView(explainInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף קורדינציות לאטרקציה ציר x
                final EditText CoordinatesXInput = new EditText(MasterattractionMain.this);
                CoordinatesXInput.setHint("CoordinatesX");
                layout.addView(CoordinatesXInput);
                //הצגת שדה וכותרת בו הadmin צריך להוסיף קורדינציות לאטרקציה ציר y
                final EditText CoordinatesYInput = new EditText(MasterattractionMain.this);
                CoordinatesYInput.setHint("CoordinatesY");
                layout.addView(CoordinatesYInput);

                //יצירת האטרקציה
                builder.setView(layout);

                //הכנסת הנתונים שהadmin ההזין למשתנים
                builder.setPositiveButton("Add", (dialog, which) -> {
                    String explain = explainInput.getText().toString().trim();
                    String name = attNameInput.getText().toString().trim();
                    String yString = CoordinatesYInput.getText().toString().trim();
                    Double CoordinatesY = Double.parseDouble(yString);
                    String xString = CoordinatesXInput.getText().toString().trim();
                    Double CoordinatesX = Double.parseDouble(xString);
                    //הוספת האטרקציה לfirebase, שמירה
                    addAttToFirebase(name, explain, CoordinatesX, CoordinatesY);
                });
                //אם לא עובד
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            }
        });

    }
    //הוספת האטרקציה לfirebase
    private void addAttToFirebase(String name, String explain, double CoordinatesX, double CoordinatesY) {
        //firebase מצביע לרשימה attraction
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("attraction");

        //קבלת המפתח של האטרקציה
        String attKey = ref.push().getKey(); // Generate unique ID
        String defaultImageUrl = "https://firebasestorage.googleapis.com/v0/b/flight-c18f5.firebasestorage.app/o/attraction%2Fdefultpicture.png?alt=media&token=bfeda48f-9961-4946-ba1b-d74bfa926c36";
        //יצירת האטרקציה עם כל המאפיינים
        Attraction newAtt = new Attraction(name, CoordinatesX, CoordinatesY, explain, defaultImageUrl, attKey);
        //עידכון הנתונים של האטרקציה לנכונים מnull
        ref.child(attKey).setValue(newAtt)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Flight added!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding flight: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });


    }
    //אם המשתמש בחר משהו מציג הערה
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
    //אם המשתמש לא בחר משהו
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //תפריט menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //בודק האם המשתמש הוא admin אם כן אז מראה גם עוד כמה דברים והאפשרויות בmenu
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
    //אם המשתמש בחר משהו מהmenu מה עושים
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