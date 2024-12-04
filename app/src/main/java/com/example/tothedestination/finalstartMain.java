package com.example.tothedestination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;


public class finalstartMain extends AppCompatActivity {


    private Button logIn, signUp;
    LottieAnimationView lottie2;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalstart);

        logIn = findViewById(R.id.logIn);
        signUp = findViewById(R.id.signUp);
        lottie2 = findViewById(R.id.lottie2);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finalstartMain.this, signUpMain.class);
                startActivity(intent);
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(finalstartMain.this, loginMain.class);
                startActivity(intent1);
            }
        });

    }



}
