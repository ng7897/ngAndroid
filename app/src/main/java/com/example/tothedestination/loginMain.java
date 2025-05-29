package com.example.tothedestination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class loginMain extends AppCompatActivity {

    private Button finalLogIn;
    private ImageView arrowImage;
    private SharedPreferences sp;
    private EditText et_email, et_password;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("userList");
        finalLogIn = findViewById(R.id.finalLogIn);

        // Back to last view
        arrowImage = findViewById(R.id.arrowImage);
        arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(loginMain.this, finalstartMain.class);
                startActivity(intent1);
            }
        });

        //49-67 לשמור על המידע שמכניסים בshared preferences
        sp = getSharedPreferences("myPref", 0);
        et_password = findViewById(R.id.editTextNumberPassword);
        et_email = findViewById(R.id.editTextEmailAddress);

        // Do Login אם לא מכניסים סיסמה או מייל רושם הודעה
        finalLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean ifError = false;
                if (TextUtils.isEmpty(et_email.getText().toString())) {
                    et_email.setError("Email is required");
                    Toast.makeText(loginMain.this, "no empty field allowed", Toast.LENGTH_SHORT).show();
                    ifError = true;
                }
                if (TextUtils.isEmpty(et_password.getText().toString())) {
                    et_password.setError("Password is required");
                    Toast.makeText(loginMain.this, "no empty field allowed", Toast.LENGTH_SHORT).show();
                    ifError = true;
                }

                if (!ifError) {
                    login();

                }
            }
        });
      //  FirebaseAuth.getInstance().signOut();
       // Intent intent1 = new Intent(loginMain.this, finalstartMain.class);
      //  startActivity(intent1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if (id == R.id.action_login) {
            Toast.makeText(this, "you selected login", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(loginMain.this, loginMain.class);
            startActivity(intent1);
        }
        else if (id == R.id.action_setting)
        {
            Toast.makeText(this, "you selected setting", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.About_programmer)
        {
            Toast.makeText(this, "you selected About programmer", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.about_app)
        {
            Toast.makeText(this, "you selected About app", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_signout)
        {
            Toast.makeText(this, "you selected sign out", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_exit)
        {
            Toast.makeText(this, "you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void login() {
        mAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("key_email", et_email.getText().toString());
                    editor.putString("key_password", et_password.getText().toString());


                    editor.commit();
                    Helpers.searchUserByEmail(et_email.getText().toString(),sp);
                    Intent intent2 = new Intent(loginMain.this, search1Main.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(loginMain.this, "login failed :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}