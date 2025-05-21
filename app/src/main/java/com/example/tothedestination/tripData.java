package com.example.tothedestination;

import static android.content.ContentValues.TAG;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


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
            if (requestCode == REQUEST_CAMERA) {
                imageView.setImageURI(imageUri); // shows image taken by camera
            } else if (requestCode == REQUEST_GALLERY && data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri); // shows image selected from gallery
            }
        }




}
}