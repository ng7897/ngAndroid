package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class search1Main extends AppCompatActivity {

    private TextView tvEmail,tvFname,tvpassword;
    private Button button2;
    private SharedPreferences sp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search1);

        tvEmail=findViewById(R.id.textView8);
        tvFname=findViewById(R.id.textView9);
        tvpassword=findViewById(R.id.textView11);
        button2=findViewById(R.id.button2);

        sp=getSharedPreferences("details1",0);
        String email=sp.getString("email",null);
        String Fname=sp.getString("full name",null);
        int iPassword=sp.getInt("password",0);

        if(email!=null && Fname !=null && iPassword!=0)
        {
            tvEmail.setText("email id:" + email);
            tvFname.setText("name is:" + Fname);
            tvpassword.setText("password is:" + iPassword);
        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent intent=new Intent(search1Main.this, startMain.class);
                startActivity(intent);
            }

        });
        return false;
    }
}