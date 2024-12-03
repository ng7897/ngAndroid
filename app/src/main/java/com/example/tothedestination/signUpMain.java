package com.example.tothedestination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signUpMain extends AppCompatActivity {
    private Button backToStart,finalSighUp;
    private ImageView arrowImage;
    private SharedPreferences sp1;
    private EditText et_firstName,et_email,et_lastName,et_password,et_passwordAgain;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        backToStart=findViewById(R.id.backToStart);
        finalSighUp=findViewById(R.id.finalSighUp);

        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(signUpMain.this, startMain.class);
                startActivity(intent);
            }
        });

        arrowImage=findViewById(R.id.arrowImage);
        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(signUpMain.this, startMain.class);
                startActivity(intent2);
            }
        });
        // לשמור על המידע שמכניסים בshared preferences
        sp1=getSharedPreferences("myPref",0);
        et_password= findViewById(R.id.editTextNumberPassword);
        et_email= findViewById(R.id.editTextTextEmailAddress);
        //et_firstName= findViewById(R.id.editTextTextPersonName);
        et_lastName= findViewById(R.id.editTextTextPersonName2);
        et_passwordAgain= findViewById(R.id.editTextNumberPassword2);

        //Signup לכתוב הודעה אם לא מכניסים את כל הנתונים
         finalSighUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                         if(TextUtils.isEmpty(et_email.getText().toString()))
                         {
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                         }
                         else if(TextUtils.isEmpty(et_password.getText().toString()))
                         {
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                         }
                         else if(TextUtils.isEmpty(et_passwordAgain.getText().toString()))
                         {
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                         }
                         else if(TextUtils.isEmpty(et_firstName.getText().toString()))
                         {
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                         }
                         else if(TextUtils.isEmpty(et_lastName.getText().toString()))
                         {
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                         }

                         else
                         {
                             SharedPreferences.Editor editor = sp1.edit();
                             editor.putString("key_email",et_email.getText().toString());
                             editor.putString("key_password", et_password.getText().toString());
                             editor.putString("key_firstName", et_firstName.getText().toString());
                             editor.putString("key_lastName", et_lastName.getText().toString());
                             editor.commit();

                             Intent intent2=new Intent(signUpMain.this, search1Main.class);
                             startActivity(intent2);
                         }
                     }
                 });



    }
}