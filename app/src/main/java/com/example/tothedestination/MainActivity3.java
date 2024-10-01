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

public class MainActivity3 extends AppCompatActivity {

    private Button button5,button6;
    private ImageView imageView;
    private SharedPreferences sp;
    EditText et_fullName,et_email,et_password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        button5=findViewById(R.id.back);
        button6=findViewById(R.id.button6);

        // Return to main activity
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Back to last view
        imageView=findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(MainActivity3.this,MainActivity.class);
                startActivity(intent1);
            }
        });


        //
        sp=getSharedPreferences("myPref",0);
        et_password= findViewById(R.id.editTextNumberPassword);
        et_email= findViewById(R.id.editTextTextEmailAddress);

        // Do Login
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("key_email",et_email.getText().toString());
                    editor.putInt("key_password", Integer.parseInt(et_password.getText().toString()));
                    editor.commit();

                    Intent intent2=new Intent(MainActivity3.this,search1.class);
                    startActivity(intent2);
            }
        });
    }
}