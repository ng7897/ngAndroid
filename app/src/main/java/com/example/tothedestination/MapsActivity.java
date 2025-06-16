package com.example.tothedestination;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tothedestination.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ArrayList<Attraction> attList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding הוא מייצג את הרכיבים בxml ומאפשר גישה אליהם למשל:binding.tvTitle.setText("something");
        //ActivityMapsBinding זאת מחלקה אוטומטית שנוצרת על סמך בסיס  הקובץ בxml, היא יוצרת באמצעות הinflate(getLayoutInfalater()) את התצוגה בפועל של הxml
        //ViewBinding זה במקום findViewById, ViewBinding יוצר אובייקט שמייצג את כל הרכיבים שבxml
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        //מציג את הlayout על המסך
        //binding.getRoot - מחזיר את הסוג layout למשל LinerLayout או ConstraintLayout
        setContentView(binding.getRoot());

        //getSerializableExtra - מיועד לשליפת אובייקט מורכב מintent
        //קבלת רשימת האטרקציות שהמשתמש סימן בcheckBox
        Intent intent = getIntent();
        attList = (ArrayList<Attraction>) intent.getSerializableExtra("attList");

        //getSupportFragmentManager - מחזיר את המנהל של הFragments , של המפה
        //Fragment= המפה שלי
        // Fragment Manager - צריך כי אם יש אובייקט של מפה למשל צריך שיהיה אחראי שינהל אותו, תפקידו הוא למצוא את הFragment הקיים, את המפה בxml
        //getSupportFragmentManager -
        //findFragmentById(R.id.map); - מוצא את המפה לפי הid בxml
        //איתור רכיב המפה לפי הid בxml(map) והמרתו לSupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //טוען את המפה בצורה שלא חוסמת את המשך הקוד, טעינת המפה לוקחת זמן בגלל שהיא מהאיטרנט ולכן מבקשים להתעדכן כשזה מוכן
        //getMapAsync - מפעיל את טעינת המפה
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //סימון נמל התעופה והזזת המצלמה
        LatLng airtport1 = new LatLng(getIntent().getDoubleExtra("coordinatesX", 0), getIntent().getDoubleExtra("coordinatesY", 0));
        mMap.addMarker(new MarkerOptions().position(airtport1).title("Marker in airtport"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(airtport1));

        // סימון כל אטרקציה שקיבלנו
        for (Attraction attraction : attList) {
            LatLng location = new LatLng(attraction.getCoordinatesX(), attraction.getCoordinatesY());
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(attraction.getAttName()));

        }
    }
}