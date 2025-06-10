package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class tripData extends AppCompatActivity {

    private TextView tvNameC, tvHours, tvSeason, tvAgeChild, tvAttraction, tvAirport;
    private String keyTrip;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 200;
    private Button addPic;

    private Uri imageUri;
    private ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_data);

        //קבלת ושמירת המפתח של הטיול שהמשתמש בחר
        keyTrip = getIntent().getStringExtra("vacKey");

        //קישור המשתנים לערכים בxml
        addPic = findViewById(R.id.addPicture);
        imageView = findViewById(R.id.imageView);
        tvNameC = findViewById(R.id.country2);
        tvHours = findViewById(R.id.hoursflight2);
        tvSeason = findViewById(R.id.season2);
        tvAgeChild = findViewById(R.id.ageOfChild2);
        tvAttraction = findViewById(R.id.kindOfAttraction2);
        tvAirport = findViewById(R.id.airport2);

        //firebase הצבעה על הטיול שהמשתמש בחר
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference vacRef = database.getReference("vacations").child(keyTrip);

        //להכניס למשתנים את הערכים של הטיול
        vacRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot vacListDataSnapshot) {
                vacation currentVac = vacListDataSnapshot.getValue(vacation.class);
                tvNameC.setText(currentVac.getCountry());
                tvHours.setText(Integer.toString(currentVac.getHoursFlight()));
                tvSeason.setText(currentVac.getSeason());
                tvAttraction.setText(currentVac.getAttraction());
                tvAgeChild.setText(currentVac.getAgeOfChild());
                tvAirport.setText(currentVac.getAirport());

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        //בלחיצה על הכפתור הוספת תמונה משגר לפעולה הבאה
        addPic.setOnClickListener(v -> showImagePickerDialog());
    }

    //בלחיצה על הכפתור מוצג בפני המשתמש את הdialog האם הוא רוצה לבחור camera או gallery
    private void showImagePickerDialog() {
        String[] options = {"Camera", "Gallery"};
        //מחלקה שבונה תיבת דו שיח בהתאמה אישית(dialog זה התיבת דו שיח)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //כותרת שבחרנו
        builder.setTitle("Choose Image From");
        //מוסיף את האפשרויות שבתיבת דו שיח
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else {
                openGallery();
            }
        });
        builder.show();
    }

    //אם המשתמש בוחר מצלמה
    private void openCamera() {
        ContentValues values = new ContentValues();
        //בחירת שם תמונה New Picture, והתיאור From Camera
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        //ליצור מקום בו נוכל לשמור את התמונה, יוצרים URI שזה כתובת המייצגת את המיקום של הקובץ במערכת של אנדרואיד
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //שמירת הuri של התמונה
        SharedPreferences sp1 = getSharedPreferences("myPref", 0);
        SharedPreferences.Editor editor = sp1.edit();
        editor.putString("imageUri", imageUri.toString()); // שמירה כמחרוזת
        editor.commit();
        //מפעיל את אפליקציית המצלמה של המכשיר
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //לשמור את התמונה בimageUri
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //מפעיל את המצלמה בפועל
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    //אם המשתמש בוחר גלריה
    private void openGallery() {
        //שמהשתמש יבחר פריט כלשהו מהגלריה
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //הפעלת הגלריה בפועל
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }
    //מקבלים את התוצאה של הפעולות openGallery oepnCamera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //בודקים האם התוצאה תקינה
        if (resultCode == RESULT_OK) {
            try {
                //אם המשתמש צילם תמונה
                if (requestCode == REQUEST_CAMERA) {
                    //טוען את התמונה ועושה שגודלה יהיה 800x1000 ומציג אותה בפני המשתמש ושומר אותה בfirebase
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1000, 800, true);
                    imageView.setImageBitmap(resizedBitmap);
                    uploadImageToFirebase(imageUri);
                }
                //אם המשתמש בחר תמונה מהגלריה, בודקים גם אם היא null
                else if (requestCode == REQUEST_GALLERY && data != null) {
                    //מקבלים את הuri של התמונה ועושה שגודלה יהיה 800x1000 ומציג אותה בפני המשתמש ושומר אותה בfirebase
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1000, 800, true);
                    imageView.setImageBitmap(resizedBitmap);
                    uploadImageToFirebase(imageUri);
                }
            } catch (IOException e) {
                //אם אין תמונה, לא עובד
                e.printStackTrace();
            }
        }
        }
        //שומר את הuri של התמונה בfirebase ברשימה vacation
    private void uploadImageToFirebase(Uri imageUri) {
        //בודק האם קיימת תמונה
        if (imageUri == null) return;

        //יצירת הפניה לstorage
        //יוצר קובץ חדש בstorage בשם images
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String fileName = "images/" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

        //העלאה לfirebase storage
        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    //כאשר העלאה הצליחה מבקשים קישור לצפייה בתמונה
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();

                        // Step 2: Save the URL to a new field called "image" inside "vacation"
                        //מצביע לרשימה vacations הנמצאת בfirebase וקבלת הטיול שהמשתמש בחר
                        DatabaseReference vacationRef = FirebaseDatabase.getInstance().getReference("vacations").child(keyTrip);

                        //HashMap- זה מבנה נתונים שמאפשר שמירה של זוגות, מפתח וערך
                        HashMap<String, Object> data = new HashMap<>();
                        //שומרים את המפתח ואת הuri
                        data.put("image", imageUrl);

                        //עושים update וכותבים הערה האם זה עבד או לא
                        vacationRef.updateChildren(data)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Image uploaded & URL saved under 'vacation/image'", Toast.LENGTH_SHORT).show();
                                })
                                //בעיה בהכנסת הuri לfirebase vacations
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "DB update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                        //לא מצליחים לקבל את הקישור לצפייה בתמונה
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to get image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                //התמונה לא עלתה בהצלחה
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    //תפריט menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //אם המשתמש בחר משהו בmenu מה עושים
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
            Intent intent1=new Intent(tripData.this, loginMain.class);
            startActivity(intent1);
        }
        else if(id==R.id.action_exit)
        {
            Toast.makeText(this,"you selected exit", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    }





