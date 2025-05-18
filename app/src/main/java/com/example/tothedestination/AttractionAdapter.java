package com.example.tothedestination;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AttractionAdapter extends ArrayAdapter<Attraction>{
    Context context;
    List<Attraction> objects;

    public AttractionAdapter(Context context, int resource, int textViewResourceld, List<Attraction> objects) {
        super(context, resource, textViewResourceld, objects);
        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout3, parent, false);
        TextView tvAtName = (TextView) view.findViewById(R.id.tvAtName);
        TextView tvExplain = (TextView) view.findViewById(R.id.tvExplain);
        ImageView ivProduct = (ImageView) view.findViewById(R.id.ivProduct);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Attraction temp = objects.get(position);
        tvAtName.setText(temp.getAttName());
        tvExplain.setText(temp.getExplain());
        StorageReference imageRef = storageRef.child(temp.getImage());



        CheckBox cbChecked = view.findViewById(R.id.isSelected);
        cbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                 temp.setChecked(isChecked);
            }
        });

        //make it only for the admin to be able to tp to that activity
        ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("myPref", 0);
                boolean isAdmin = sp.getBoolean("CanEditAttraction",false);
                if(isAdmin)
                {
                    Intent i = new Intent(context, DetailsAttractionMain.class);
                    i.putExtra("attKey", temp.getKey());
                    context.startActivity(i);
                }
            }
        });



        // Download the image as a byte array
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Got the image data as a byte array
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // Display the bitmap in the ImageView
                ivProduct.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return view;
    }

}
