package com.example.loginnew;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WelcomeNew extends AppCompatActivity {
    private Button bGoogle;
    private LocationManager locationManager;
    private Location currentLocation;
    int LOCATION_PERMISSION_REQUEST = 1001;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            currentLocation = location;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_new);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("نٌـظـم");
        bGoogle  = findViewById(R.id.button) ;
    }

    @Override
    protected void onResume() {
        //-----------------------------------------------location------------------------------------------
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
                    500, locationListener);
            getLocation();
        }
    }
    //--------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
               getLocation();
            }
        }
    }
    public void getLocation()
    {
        if (locationManager.isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else
            {
                Toast.makeText(this,"location must be turned on first!",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onClickGoogle(View view) {

        if (checkLocation(currentLocation))
        {

            AlertDialog.Builder alert =  new AlertDialog.Builder(this);
            alert.setTitle("تنبيه");
            alert.setMessage("هل تريد نداء طفلك ؟");
            alert.setPositiveButton("نعم", (dialog, id) -> {

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String str = formatter.format(date);
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String , String> myMap = new HashMap<>();
                myMap.put("Date", str);
                myMap.put("userId" , userId);
                myMap.put("isComming", "true");
                FirebaseDatabase.getInstance().getReference().child("Notification").child(str).child(userId).setValue(myMap);
                dialog.cancel();
            });
            alert.setNegativeButton("لا", (dialog, id) -> {
                //  Action for 'NO' Button
                dialog.cancel();
            });
            alert.create().show();

//            Intent intent = new Intent(WelcomeNew.this, GoogleMapsActivity.class);
//            startActivity(intent);
        }else
        {
            Toast.makeText(this,"يجب أن تكون في محيط المدرسة",Toast.LENGTH_LONG).show();
        }

    }//onclick

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        return true;
    }
    public boolean checkLocation(Location location)
    {
        Gfg test = new Gfg();
        Gfg.Point[] polygon
                = { new Gfg.Point(21.582379, 39.180316 ), new Gfg.Point(21.577414, 39.181160 ), new Gfg.Point(21.576422, 39.184345), new Gfg.Point(21.581614, 39.186154 ) };
        Gfg.Point p = new Gfg.Point(location.getLatitude(), location.getLongitude() );
        int n = 4;
        return test.checkInside(polygon, n, p)==1 ;
    }
//-----------------------------------end--------------------------------------------------------
public void onClick1(View view) {
    Intent intent = new Intent(WelcomeNew.this, riseabsence.class);
    startActivity(intent);}
    //---------------------------------------------

    public void Earlypermission(View view) {
        Intent intent = new Intent(WelcomeNew.this, EarlyPermission.class);
        startActivity(intent);

    }
    //-------------------------------------------------------

}