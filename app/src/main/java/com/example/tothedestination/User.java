package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {

    //הגדרת תכונות המחלקה User
    private  String firstName;
    private String lastName;
    private String password;
    private String mail;

    //יצירת פעולה בונה והכנסת הנתונים שהיא מקבלת לתכונותיה
    public User(String firstName, String lastName, String password, String mail)
    {
        this.firstName = firstName;
        this.lastName=lastName;
        this.password=password;
        this.mail=mail;
    }

    //עולה בונה ריקה כדי שנוכל להתשמש בfirebase
    public User()
    {

    }

    //פעולות get set
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



}
