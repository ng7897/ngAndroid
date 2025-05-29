package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
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

        keyTrip = getIntent().getStringExtra("vacKey");

        addPic = findViewById(R.id.addPicture);
        imageView = findViewById(R.id.imageView);
        tvNameC = findViewById(R.id.country2);
        tvHours = findViewById(R.id.hoursflight2);
        tvSeason = findViewById(R.id.season2);
        tvAgeChild = findViewById(R.id.ageOfChild2);
        tvAttraction = findViewById(R.id.kindOfAttraction2);
        tvAirport = findViewById(R.id.airport2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference vacRef = database.getReference("vacations").child(keyTrip);

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
        addPic.setOnClickListener(v -> showImagePickerDialog());
    }

    private void showImagePickerDialog() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image From");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else {
                openGallery();
            }
        });
        builder.show();
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == REQUEST_CAMERA) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1000, 800, true);
                    imageView.setImageBitmap(resizedBitmap);
                    uploadImageToFirebase(imageUri);
                } else if (requestCode == REQUEST_GALLERY && data != null) {
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 1000, 800, true);
                    imageView.setImageBitmap(resizedBitmap);
                    uploadImageToFirebase(imageUri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri == null) return;

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String fileName = "images/" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);

        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    // Step 1: Get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();

                        // Step 2: Save the URL to a new field called "image" inside "vacation"
                        DatabaseReference vacationRef = FirebaseDatabase.getInstance().getReference("vacations").child(keyTrip);

                        HashMap<String, Object> data = new HashMap<>();
                        data.put("image", imageUrl);

                        vacationRef.updateChildren(data)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Image uploaded & URL saved under 'vacation/image'", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "DB update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to get image URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent1=new Intent(tripData.this, loginMain.class);
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
    }





