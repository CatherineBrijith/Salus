package com.example.salus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Set;

public class SoSscreen extends AppCompatActivity {
    private LocationManager locationManager;
    Boolean iscancelpressed=false;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_sscreen);
       TextView sos = findViewById(R.id.sos);
       Button cancel=findViewById(R.id.btn_cancel);
       cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoforegroundactions();
                iscancelpressed=true;
            }
        });
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update UI to show the remaining time
                 sos.setText("TIME LEFT" + "\n"+millisUntilFinished / 1000 );
            }


            public void onFinish() {
                if (iscancelpressed == false) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    // Request location permissions

                    if (ActivityCompat.checkSelfPermission(SoSscreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SoSscreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    // Check if location is null
                    if (location == null) {
                        Toast.makeText(SoSscreen.this, "Location not available", Toast.LENGTH_SHORT).show();
                        latitude=0;
                        longitude=0;

                    }
                    else{
                         latitude = location.getLatitude();
                         longitude = location.getLongitude();


                    }

                    // Get latitude and longitude

                    // Get phone number

                    // Send SMS message with Google Maps link
                    String message = "I am in Danger !!! Please Help!!! Here is my current location: https://www.google.com/maps/search/?api=1&query=" +
                            latitude + "," + longitude;
                    SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    Set<String> retrievedSet1 = sh.getStringSet("Name array", null);
                    String[] Name = retrievedSet1.toArray(new String[0]);
                    Set<String> retrievedSet2 = sh.getStringSet("Phoneno array", null);
                    String[] Phoneno = retrievedSet2.toArray(new String[0]);


                    for (int i = 0; i < 5; i++) {
                        SmsManager.getDefault().sendTextMessage(Phoneno[i], null, message, null, null);
                    }

                    // Show toast message
                    Toast.makeText(SoSscreen.this, "Location sent to all emergency contacts", Toast.LENGTH_SHORT).show();
                }
            }


        }.start();
    }

    public void gotoforegroundactions() {
        Intent intent = new Intent(SoSscreen.this, ForegroundActions.class);
        finish();
        startActivity(intent);
        Toast.makeText(SoSscreen.this, "SOS Trigger Cancelled" , Toast.LENGTH_SHORT).show();
    }
}