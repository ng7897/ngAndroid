package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class startMain extends AppCompatActivity {

    private Button logIn,sighUp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }
        public boolean onOptionsItemSelected(MenuItem item)
        {


        }

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
}