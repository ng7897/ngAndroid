package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class search1Main extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SharedPreferences sp1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search1);

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

                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("key_ageOfChildren",aSpinner1.getSelectedItem().toString());
                editor.putString("key_hoursFlight", aSpinner2.getSelectedItem().toString());
                editor.putString("key_attraction", aSpinner3.getSelectedItem().toString());
                editor.putString("key_wather", aSpinner4.getSelectedItem().toString());
                editor.commit();

                Intent intent2=new Intent(search1Main.this, FlyListMain.class);
                startActivity(intent2);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}