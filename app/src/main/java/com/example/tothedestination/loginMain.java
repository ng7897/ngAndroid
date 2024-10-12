package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

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

        // Do Login אם לא מכניסים סיסמה או מייל רושם הודעה
        finalLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(TextUtils.isEmpty(et_email.getText().toString()))
                    {
                        Toast.makeText(loginMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(et_password.getText().toString()))
                    {
                         Toast.makeText(loginMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("key_email",et_email.getText().toString());
                        editor.putString("key_password", et_password.getText().toString());
                        editor.commit();

                        Intent intent2=new Intent(loginMain.this, search1Main.class);
                        startActivity(intent2);
                    }
            }
        });

    }
}