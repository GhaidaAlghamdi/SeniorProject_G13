package com.example.loginnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.loginnew.databinding.ActivityGoogleMapsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener {

    private GoogleMap mMap;
    private ActivityGoogleMapsBinding binding;
    int LOCATION_REFRESH_TIME = 5000; // 5 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update
    int LOCATION_PERMISSION_REQUEST = 101;
    Location currentLocation;
    private LocationManager mLocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLocation = location;
            
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // check if the app have the permissions to get current location and if not request locations permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // request location permissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        getLocationService();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public void getLocationService() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        /// check if location is turned on
        if (mLocationManager.isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                        LOCATION_REFRESH_DISTANCE, mLocationListener);
                currentLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else
            {
                Toast.makeText(this,"location must be turned on first!",Toast.LENGTH_LONG).show();

            }
            }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
              getLocationService();

            }

        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng current_loc = new LatLng(21.5799, 39.1808);
        mMap.addMarker(new MarkerOptions().position(current_loc).title("Marker in UJ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_loc));
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);


    }
    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.", Toast.LENGTH_SHORT).show();
        }
        else if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.", Toast.LENGTH_SHORT).show();
        }
        else if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onCameraMove() {
        Toast.makeText(this, "The camera is moving.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCameraMoveCanceled() {
        Toast.makeText(this, "Camera movement canceled.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "The camera has stopped moving.", Toast.LENGTH_SHORT).show();
    }


}