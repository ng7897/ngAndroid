package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MasterattractionMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Attraction> attList, checkList;
    ListView lv;
    AttractionAdapter attAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_attraction);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference attRef = database.getReference("attraction");

        attList = new ArrayList<Attraction>();
        attRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attList.clear();

                for (DataSnapshot attSnapshot : dataSnapshot.getChildren())
                {
                    Attraction currentAttraction = attSnapshot.getValue(Attraction.class);
                    attList.add(currentAttraction);
                    attAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        attAdapter=new AttractionAdapter(this,0,0,attList);
        lv=(ListView) findViewById(R.id.lv);
        lv.setAdapter(attAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}