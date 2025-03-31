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

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        attList = (ArrayList<Attraction>) intent.getSerializableExtra("attList");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng airtport1 = new LatLng(getIntent().getDoubleExtra("coordinatesX", 0), getIntent().getDoubleExtra("coordinatesY", 0));
        //LatLng airport = getIntent().getParcelableExtra("airport");

        mMap.addMarker(new MarkerOptions().position(airtport1).title("Marker in airtport"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(airtport1));

        // Add markers for each attraction
        for (Attraction attraction : attList) {
            LatLng location = new LatLng(attraction.getCoordinatesX(), attraction.getCoordinatesY());
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(attraction.getAttName()));

        }
    }
}