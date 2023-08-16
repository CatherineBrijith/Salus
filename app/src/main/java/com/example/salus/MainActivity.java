package com.example.salus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int PERMISSION_REQUEST_CODE = 1;
    Button profile, sos;
    private LocationManager locationManager;
    private String phoneNumber = "8608632156";
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        profile = findViewById(R.id.btn_profile);
        sos = findViewById(R.id.btn_sos);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Request location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Set up button click listener
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get current location
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                // Check if location is null
                if (location == null) {
                    Toast.makeText(MainActivity.this, "Location not available", Toast.LENGTH_SHORT).show();
                    latitude=0;
                    longitude=0;

                }
                else{
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }

                // Get phone number

                // Send SMS message with Google Maps link
                String message = "I am in Danger !!! Please Help!!! Here is my current location: https://www.google.com/maps/search/?api=1&query=" +
                        latitude + "," + longitude;
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Set<String> retrievedSet1 = sh.getStringSet("Name array", null);
                String[] Name = retrievedSet1.toArray(new String[0]);
                Set<String> retrievedSet2 = sh.getStringSet("Phoneno array", null);
                String[] Phoneno = retrievedSet2.toArray(new String[0]);


                for(int i=0;i<5;i++) {
                    SmsManager.getDefault().sendTextMessage( Phoneno[i] , null, message, null, null);
                }

                // Show toast message
                Toast.makeText(MainActivity.this, "Location sent to all emergency contacts" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void gotouserprofile(View view) {
        Intent intent1 = new Intent(MainActivity.this, UserProfile.class);
        startActivity(intent1);
    }

    public void logout(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("email", "");
        myEdit.putString("password", "");
        myEdit.putString("username", "");
        myEdit.putString("phoneno", "");
        myEdit.putString("age", "");
        myEdit.putString("gender", "");
        myEdit.putString("Name array", "");
        myEdit.putString("Phoneno array", "");


        myEdit.apply();
        Toast.makeText(MainActivity.this, "Log Out sucessfully!!", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(MainActivity.this, SignIn.class);
        finish();
        startActivity(intent2);
    }

    public void gotoaddcontacts(View view) {
        Intent intent3 = new Intent(MainActivity.this, AddContacts.class);
        startActivity(intent3);
    }

    public void gotomapactivity(View view) {
        Intent intent4 = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent4);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    public void foreground(View view) {
        Intent intent5 = new Intent(MainActivity.this, ForegroundActions.class);
        startActivity(intent5);
    }
}