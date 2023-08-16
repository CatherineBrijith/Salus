package com.example.salus;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



public class ForegroundActions extends AppCompatActivity implements LocationListener {

    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final float GRAVITY_THRESHOLD = 10.0f;
    private static final long FALL_TIME_THRESHOLD = 650;
    private static final long SOS_TIME_THRESHOLD = 3000;
    private LocationManager locationManager;
    private boolean isFalling = false;
    private long lastFallTime = 0;
    private long lastSOSTime = 0;
    Boolean sostriggerd=false,isenable=false;
    private static final String PREF_SWITCH_STATE1 = "switch_state",PREF_SWITCH_STATE2 = "switch_state",PREF_SWITCH_STATE3 = "switch_state";
    private Switch switch_motion,switch_voice,switch_battery;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_actions);

        sostriggerd=false;

        switch_motion = findViewById(R.id.switch1);
        switch_voice=findViewById(R.id.switch2);
        switch_battery=findViewById(R.id.switch3);


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Retrieve switch state from SharedPreferences
        boolean switchState_motion = sharedPreferences.getBoolean(PREF_SWITCH_STATE1, false);
        switch_motion.setChecked(switchState_motion);

        switch_motion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save switch state in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_SWITCH_STATE1, isChecked);
                editor.apply();

                // Handle switch state change
                if (isChecked) {
                    // Switch is enabled
                    if (ActivityCompat.checkSelfPermission(ForegroundActions.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(ForegroundActions.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(ForegroundActions.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ForegroundActions.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE},
                                PERMISSIONS_REQUEST_CODE);
                        return;
                    }

                    // Start location updates
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ForegroundActions.this);

                    // Set up accelerometer listener
                    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

                    // Update U
                    Toast.makeText(ForegroundActions.this, "Motion Detection Enabled" , Toast.LENGTH_SHORT).show();

                } else {
                    // Switch is disabled
                    locationManager.removeUpdates(ForegroundActions.this);

                    // Remove accelerometer listener
                    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                    sensorManager.unregisterListener(accelerometerListener);

                    Toast.makeText(ForegroundActions.this, "Motion Detection Disabled" , Toast.LENGTH_SHORT).show();

                }
            }
        });
        boolean switchState_voice = sharedPreferences.getBoolean(PREF_SWITCH_STATE2, false);
        switch_voice.setChecked(switchState_voice);

        switch_voice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save switch state in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREF_SWITCH_STATE2, isChecked);
                editor.apply();

                // Handle switch state change
                if (isChecked) {
                    // Switch is enabled


                    // Update U
                    Toast.makeText(ForegroundActions.this, "Voice Detection Enabled" , Toast.LENGTH_SHORT).show();

                } else {
                    // Switch is disabled


                    Toast.makeText(ForegroundActions.this, "Voice Detection Disabled" , Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gravity = (float) Math.sqrt(x*x + y*y + z*z);

            // Check if acceleration is above threshold
            if (gravity > GRAVITY_THRESHOLD) {
                // Check if already falling
                if (!isFalling) {
                    isFalling = true;
                    lastFallTime = System.currentTimeMillis();
                } else {
                    // Check if fall time threshold has passed
                    long timeSinceFall = System.currentTimeMillis() - lastFallTime;
                    if (timeSinceFall > FALL_TIME_THRESHOLD) {
                        // Check if SOS time threshold has passed
                        long timeSinceSOS = System.currentTimeMillis() - lastSOSTime;
                        if (timeSinceSOS > SOS_TIME_THRESHOLD) {
                            // Trigger SOS
                            triggerSOS();
                            lastSOSTime = System.currentTimeMillis();
                            return;
                        }
                    }
                }
            } else {
                isFalling = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Not used
        }
    };

    private void triggerSOS() {

        if(sostriggerd==false) {
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.example.salus", "com.example.salus.SoSscreen");
            intent.setComponent(componentName);
            startActivity(intent);
            sostriggerd=true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "permissinon not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Not used
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Not used
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Not used
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Not used
    }
}