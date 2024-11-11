package com.example.tothedestination;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FlyListMain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<Fly> flyList;
    ListView lv;
    FlyAdapter flyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fly_list);
        Spinner aSpinner=findViewById(R.id.aSpinner);
        aSpinner.setOnItemSelectedListener(this);

        Bitmap country0= BitmapFactory.decodeResource(getResources(),R.drawable.country0);
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
        flyList.add(f7);

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
}