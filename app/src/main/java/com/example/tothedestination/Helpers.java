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

    //פעולה המקבלת אימייל וshared preference ובודקת בfirebase realtime database האם ישנו משתמש קיים עם אותו האימייל שהיא קיבלה אם כן היא בודקת האם הוא admin או לא
    public static void searchUserByEmail(String email,SharedPreferences sp) {
        //עובדת על הרשימה של המשתמשים בפיירבייס ובודקת האם המייל שיש לנו פה כפרמטר שווה לאחד המיילים שם פעולה ש
        SharedPreferences.Editor editor = sp.edit();
        //firebase מצביע על רשימת הuserList
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersListRef = database.getReference("userList");
        //מסננים את הרשימה לפי האימייל שקיבלנו בפעולה
        usersListRef.orderByChild("mail").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //האם קיים, נשמר משהו ברשימה?
                        if (dataSnapshot.exists()) {
                            //אם כן עוברים על המשתמש על הנתונים שלו ובודקים האם הוא admin או לא
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                //קבלת המפתח של המשתמש
                                String userId = snapshot.getKey();
                                //בדיקה האם הוא admin או לא
                                if (snapshot.hasChild("canEditAttraction")) {
                                    AdminUser user = snapshot.getValue(AdminUser.class);
                                    if (user instanceof AdminUser) {
                                        //אם כן לשנות את כל הנתונים לtrue
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
                                    //אם לא
                                    editor.putBoolean("CanEditAttraction", false);
                                }
                                //אם מוצאים משתמש כזה ברשימה לפי האימייל שלו אז שומרים את הid שלו, את המפתח בshared preference
                                String foundEmail = snapshot.child("mail").getValue(String.class);
                                Log.d(TAG, "User found: " + userId + " - " + foundEmail);
                                editor.putString("key_user", userId);
                                editor.commit();
                            }
                        } else {
                            //אם לא קיים משתשמ עם אותו האימייל
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
