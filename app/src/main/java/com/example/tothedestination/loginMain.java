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

public class loginMain extends AppCompatActivity {

    private Button backToStart,finalLogIn;
    private ImageView arrowImage;
    private SharedPreferences sp;
    EditText et_fullName,et_email,et_password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        backToStart=findViewById(R.id.backToStart);
        finalLogIn=findViewById(R.id.finalLogIn);

        // Return to main activity
        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(loginMain.this, startMain.class);
                startActivity(intent);
            }
        });

        // Back to last view
        arrowImage=findViewById(R.id.arrowImage);
        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(loginMain.this, startMain.class);
                startActivity(intent1);
            }
        });


        //49-67 לשמור על המידע שמכניסים בshared preferences
        sp=getSharedPreferences("myPref",0);
        et_password= findViewById(R.id.editTextNumberPassword);
        et_email= findViewById(R.id.editTextTextEmailAddress);

        // Do Login
        finalLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("key_email",et_email.getText().toString());
                    editor.putInt("key_password", Integer.parseInt(et_password.getText().toString()));
                    editor.commit();

                    Intent intent2=new Intent(loginMain.this, search1Main.class);
                    startActivity(intent2);
            }
        });
        return false;
    }
}