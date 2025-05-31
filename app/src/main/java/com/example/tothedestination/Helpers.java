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
        //עובדת על הרשימה של המשתמשים בפיירבייס ובודקת האם המייל שיש לנו פה כפרמטר שווה לאחד המיילים שם פעולה ש
        SharedPreferences.Editor editor = sp.edit();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersListRef = database.getReference("userList");
        usersListRef.orderByChild("mail").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userId = snapshot.getKey();
                                if (snapshot.hasChild("canEditAttraction")) {
                                    AdminUser user = snapshot.getValue(AdminUser.class);
                                    if (user instanceof AdminUser) {
                                        editor.putBoolean("CanEditAttraction", true);
                                        editor.putBoolean("CanDeleteAttraction", true);
                                        editor.putBoolean("CanAddAttraction", true);
                                        editor.putBoolean("CanEditFlyList", true);
                                        editor.putBoolean("CanDeleteFlyList", true);
                                        editor.putBoolean("CanAddFlyList", true);
                                    }
                                }
                                else
                                {
                                    editor.putBoolean("CanEditAttraction", false);
                                }
                                // אם מוצאים משתמש כזה ברשימה לפי האימייל שלו אז שומרים את הid שלו
                                String foundEmail = snapshot.child("mail").getValue(String.class);
                                Log.d(TAG, "User found: " + userId + " - " + foundEmail);
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
