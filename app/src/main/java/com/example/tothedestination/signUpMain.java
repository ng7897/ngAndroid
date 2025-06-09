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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUpMain extends AppCompatActivity {

    private Button finalSighUp;
    private ImageView arrowImage;
    private SharedPreferences sp1;
    private FirebaseAuth mAuth;
    private EditText et_firstName,et_email,et_lastName,et_password,et_passwordAgain;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();

        finalSighUp=findViewById(R.id.finalSignUp);

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
        et_email= findViewById(R.id.editTextEmailAddress);
        et_firstName= findViewById(R.id.editTextPersonName);
        et_lastName= findViewById(R.id.editTextPersonName2);
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
                         else
                         {
                             if(!hasAtSymbol(et_email.getText().toString()))
                             {
                                 et_email.setError("needed @ in the email");
                                 ifError=true;
                             }
                         }
                         if(TextUtils.isEmpty(et_password.getText().toString()))
                         {
                             et_password.setError("Password is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         else
                         {
                             if (!hasUpperCase(et_password.getText().toString())) {
                                 et_password.setError("Password must contain at least one uppercase letter");
                                 ifError=true;
                             }
                             if(!hasLowerCase(et_password.getText().toString()))
                             {
                                 et_password.setError("Password must contain at least one lowercase letter");
                             }

                         }
                         if(TextUtils.isEmpty(et_passwordAgain.getText().toString()))
                         {
                             et_passwordAgain.setError("Confirm your password");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         else if(!(et_password.getText().toString().equals(et_passwordAgain.getText().toString())))
                         {
                             et_passwordAgain.setError("Passwords do not match");
                             Toast.makeText(signUpMain.this,"passwords dont match", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         if(TextUtils.isEmpty(et_firstName.getText().toString()))
                         {
                             et_firstName.setError("First name is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         else
                         {
                             if (!hasUpperCase(et_firstName.getText().toString())) {
                                 et_firstName.setError("Password must contain at least one uppercase letter");
                                 ifError=true;
                             }
                             if(!hasLowerCase(et_firstName.getText().toString()))
                             {
                                 et_firstName.setError("Password must contain at least one lowercase letter");
                                 ifError=true;
                             }

                         }
                         if(TextUtils.isEmpty(et_lastName.getText().toString()))
                         {
                             et_lastName.setError("Last name is required");
                             Toast.makeText(signUpMain.this,"no empty field allowed", Toast.LENGTH_SHORT).show();
                             ifError=true;
                         }
                         else
                         {
                             if (!hasUpperCase(et_lastName.getText().toString())) {
                                 et_lastName.setError("Password must contain at least one uppercase letter");
                                 ifError=true;
                             }
                             if(!hasLowerCase(et_lastName.getText().toString()))
                             {
                                 et_lastName.setError("Password must contain at least one lowercase letter");
                                 ifError=true;
                             }

                         }

                         if (!ifError)
                         {
                             register();
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
        if(id==R.id.About_programmer)
        {
            Toast.makeText(this,"you selected About programmer", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.about_app)
        {
            Toast.makeText(this,"you selected About app", Toast.LENGTH_SHORT).show();
        }
         else if (id == R.id.action_signout)
        {
        Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
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
        sp1=getSharedPreferences("myPref",0);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("key_user", String.valueOf(newUserRef));
        editor.commit();

        Toast.makeText(this,"user added", Toast.LENGTH_SHORT).show();
    }

    public void register() {
        mAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent2 = new Intent(signUpMain.this, search1Main.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(signUpMain.this, "register failed :(", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public static boolean hasUpperCase(String str) {
        return str.matches(".*[A-Z].*");
    }

    public static boolean hasLowerCase(java.lang.String str)
    {
        Pattern pattern = Pattern.compile(".*[a-z].*");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
    public static boolean hasAtSymbol(String str) {
        Pattern pattern = Pattern.compile(".*@.*");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}