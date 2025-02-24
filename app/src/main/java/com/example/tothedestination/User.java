package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class User {
    private  String firstName;
    private String lastName;
    private String password;
    private String mail;

    public User(String firstName, String lastName, String password, String mail)
    {
        this.firstName = firstName;
        this.lastName=lastName;
        this.password=password;
        this.mail=mail;
    }
    public User()
    {

    }

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

    public void searchUserByEmail(String email, SharedPreferences sp, DatabaseReference usersRef, EditText et_email) {
        // Initialize Firebase Database reference
        //עובדת על הרשימה של המשתמשים בפיירבייס ובודקת האם המייל שיש לנו פה כפרמטר שווה לאחד המיילים שם פעולה ש

        usersRef.orderByChild("mail").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userId = snapshot.getKey();
                                String foundEmail = snapshot.child("email").getValue(String.class);
                                Log.d(TAG, "User found: " + userId + " - " + foundEmail);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("email_user", et_email.getText().toString());
                                editor.putString("key_user", userId);
                                editor.commit();
                            }
                        } else {
                            Log.d(TAG, "No user found with this email.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e(TAG, "Database error: " + databaseError.getMessage());
                    }
                });
    }
}
