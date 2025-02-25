package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;

public class Helpers {

    public static void searchUserByEmail(String email,SharedPreferences sp) {
        // Initialize Firebase Database reference
        //עובדת על הרשימה של המשתמשים בפיירבייס ובודקת האם המייל שיש לנו פה כפרמטר שווה לאחד המיילים שם פעולה ש

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersListRef = database.getReference("userList");
        usersListRef.orderByChild("mail").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userId = snapshot.getKey();
                                String foundEmail = snapshot.child("email").getValue(String.class);
                                Log.d(TAG, "User found: " + userId + " - " + foundEmail);
                                SharedPreferences.Editor editor = sp.edit();
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
