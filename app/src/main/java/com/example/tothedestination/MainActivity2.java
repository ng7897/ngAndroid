package com.example.tothedestination;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    private Button button5,button4,button3;
    private ImageView imageView;
    private SharedPreferences sp;
    EditText et_firstName,et_email,et_lastName,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button5=findViewById(R.id.button5);
        button4=findViewById(R.id.button4);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
        String strLastName = sp.getString("key_Lname",null);
        et_firstName= findViewById(R.id.editTextTextPersonName);

      sp=getSharedPreferences("details1",0);
      SharedPreferences.Editor editor = sp.edit();
      editor.putString("key_Lname",et_lastName.getText().toString());
      editor.commit();
    }
}