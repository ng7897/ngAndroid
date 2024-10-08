package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class sighupMain extends AppCompatActivity {

    private Button backToStart,button3,finalSighUp;
    private ImageView arrowImage;
    private SharedPreferences sp;
    EditText et_firstName,et_email,et_lastName,et_password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        backToStart=findViewById(R.id.backToStart);
        finalSighUp=findViewById(R.id.finalSighUp);

        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(sighupMain.this, startMain.class);
                startActivity(intent);
            }
        });

        finalSighUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(sighupMain.this, search1.class);
                startActivity(intent);
            }
        });

        arrowImage=findViewById(R.id.arrowImage);
        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(sighupMain.this, startMain.class);
                startActivity(intent2);
            }
        });



    }
}