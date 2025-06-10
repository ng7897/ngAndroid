package com.example.tothedestination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class finalstartMain extends AppCompatActivity {


    private Button logIn, signUp;
    LottieAnimationView lottie2;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalstart);

        //קישור בין המשתנים לxml
        logIn = findViewById(R.id.logIn);
        signUp = findViewById(R.id.signUp);
        lottie2 = findViewById(R.id.lottie2);
        mAuth = FirebaseAuth.getInstance();

        //בלחיצה עובר למסך הירשמות
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finalstartMain.this, signUpMain.class);
                startActivity(intent);
            }
        });

        //בלחיצה עובר למסך התחברות
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(finalstartMain.this, loginMain.class);
                startActivity(intent1);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        //בודק האם המשתמש מחובר כבר- אם כן עובר למסך search1
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser !=null)
        {
            //קורא לפעולה הבודקת בautentication האם האימייל באמת קיים, האם המשתמש באמת קיים
            Helpers.searchUserByEmail(currentUser.getEmail(),getSharedPreferences("myPref", 0));
            Intent intent = new Intent(finalstartMain.this, search1Main.class);
            startActivity(intent);
        }
    }


}
