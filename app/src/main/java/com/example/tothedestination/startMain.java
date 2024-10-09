package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class startMain extends AppCompatActivity {

    private Button logIn,sighUp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        logIn=findViewById(R.id.logIn);
        sighUp=findViewById(R.id.sighUp);
        sighUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(startMain.this, sighupMain.class);
                startActivity(intent);
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(startMain.this, loginMain.class);
                startActivity(intent1);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.action_login)
        {
            Toast.makeText(this,"you selected login", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(startMain.this, loginMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_setting)
        {
            Toast.makeText(this,"you selected setting", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}