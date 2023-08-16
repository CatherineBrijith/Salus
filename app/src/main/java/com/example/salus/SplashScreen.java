package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Animation animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        View rootLayout = findViewById(R.id.ss);
        rootLayout.startAnimation(animZoomIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String check_email = sh.getString("email", "");
                String check_password = sh.getString("password", "");

                if(!check_email.isEmpty() && !check_password.isEmpty()){
                    Intent intent1=new Intent(SplashScreen.this,MainActivity.class);
                    finish();
                    startActivity(intent1);

                }
                else {
                    Intent intent = new Intent(SplashScreen.this, SignIn.class);
                    finish();
                    startActivity(intent);
                }

            }
        }, 2000); // delay for 2 seconds

    }


}