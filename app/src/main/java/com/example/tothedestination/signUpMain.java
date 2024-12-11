package com.example.tothedestination;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                Intent intent =new Intent(signUpMain.this, finalstartMain.class);
                startActivity(intent);
            }
        });

        arrowImage=findViewById(R.id.arrowImage);
        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 =new Intent(signUpMain.this, finalstartMain.class);
                startActivity(intent2);
            }
        });
        // לשמור על המידע שמכניסים בshared preferences
        sp1=getSharedPreferences("myPref",0);
        et_password= findViewById(R.id.editTextNumberPassword);
        et_email= findViewById(R.id.editTextTextEmailAddress);
        et_firstName= findViewById(R.id.editTextTextPersonName);
        et_lastName= findViewById(R.id.editTextTextPersonName2);
        et_passwordAgain= findViewById(R.id.editTextNumberPassword2);

        //Signup לכתוב הודעה אם לא מכניסים את כל הנתונים
         finalSighUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 boolean ifError = false;
                         if(TextUtils.isEmpty(et_email.getText().toString()))
                         {
                             et_email.setError("Email is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         if(TextUtils.isEmpty(et_password.getText().toString()))
                         {
                             et_password.setError("Password is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         if(TextUtils.isEmpty(et_passwordAgain.getText().toString()))
                         {
                             et_passwordAgain.setError("Confirm your password");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         if(TextUtils.isEmpty(et_firstName.getText().toString()))
                         {
                             et_firstName.setError("First name is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         if(TextUtils.isEmpty(et_lastName.getText().toString()))
                         {
                             et_lastName.setError("Last name is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }

                         if (!ifError)
                         {
                             SharedPreferences.Editor editor = sp1.edit();
                             editor.putString("key_email",et_email.getText().toString());
                             editor.putString("key_password", et_password.getText().toString());
                             editor.putString("key_firstName", et_firstName.getText().toString());
                             editor.putString("key_lastName", et_lastName.getText().toString());
                             editor.commit();
                             saveUser();
                             Intent intent2=new Intent(signUpMain.this, search1Main.class);
                             startActivity(intent2);
                         }
                     }
                 });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.action_login)
        {
            Toast.makeText(this,"you selected login", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(signUpMain.this, loginMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_setting)
        {
            Toast.makeText(this,"you selected setting", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.About_programmer)
        {
            Toast.makeText(this,"you selected About programmer", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.fav_flights)
        {
            Toast.makeText(this,"you selected Favorite Flights", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public void saveUser()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersListRef = database.getReference("userList");


        User user=new User(et_firstName.getText().toString(),et_lastName.getText().toString(),et_password.getText().toString(),et_email.getText().toString());
        DatabaseReference newUserRef= usersListRef.push();
        newUserRef.setValue(user);

        Toast.makeText(this,"user added", Toast.LENGTH_SHORT).show();
    }

}