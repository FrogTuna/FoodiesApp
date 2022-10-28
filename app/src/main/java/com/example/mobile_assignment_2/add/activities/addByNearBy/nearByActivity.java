package com.example.mobile_assignment_2.add.activities.addByNearBy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.example.mobile_assignment_2.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;

public class nearByActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    LocationRequest locationRequest;
    LocationCallback locationCallBack;
    private final int Request_Code_Location = 22;

    // Widgets
    private TextView distance;
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbynearby);

        Log.d("nearby page", "Location updates");
        // location API settings
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);      // location updates every 5 seconds
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if(locationResult != null) {
                    Log.d("LocationTest", "Location updates");
                    updateUI(locationResult.getLastLocation());
                }else{
                    Log.d("LocationTest", "Location updates fail: null");
                }
            }
        };

        // Widgets
        distance = findViewById(R.id.showDistance);
        bt = findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLocation();
            }
        });



    }

    void updateLocation(){
        //if user grants permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null);

            // get the last location
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location == null) {
                                Log.d("LocationTest", "null");
                            }else {
                                Log.d("LocationTest", "Success");
                                updateUI(location);     // if successful, update the UI
                            }
                        }
                    });

        }else{
            //if user hasn't granted permission, ask for it explicitly
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code_Location);
        }
    }

    // update the UI according to the GPS information
    private void updateUI(Location location){
        double lat1 = location.getLatitude();
        double lon1 = location.getLongitude();

        double lat2 = 30;
        double lon2 = 150;

        final double R = 6371e3; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = Math.sqrt(R * c * 1000) /1000; // convert to kilometers
        DecimalFormat df = new DecimalFormat("0.00");
        String distOutput = df.format(dist) + " km";

        distance.setText(distOutput);

        Log.d("Latitude", String.valueOf(location.getLatitude()));
        Log.d("Longitude", String.valueOf(location.getLongitude()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Request_Code_Location){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                updateLocation();
            }
        }
    }
}
