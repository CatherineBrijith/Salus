package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUp3 extends AppCompatActivity {

    String susername,semail,spassword,sgender,sage,sphoneNo;
    CountryCodePicker countryCodePicker;
    EditText phoneNumber;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        countryCodePicker=findViewById(R.id.country_code_picker);
        phoneNumber=findViewById(R.id.edit_phoneno1);
        semail = getIntent().getStringExtra("email");
        susername = getIntent().getStringExtra("username");
        spassword = getIntent().getStringExtra("password");
        sgender = getIntent().getStringExtra("gender");
        sage=getIntent().getStringExtra("age");


    }

    public void gotoOtpverfication(View view) {
        String _getUserEnteredPhoneNumber = phoneNumber.getText().toString().trim();
//Remove first zero if entered!
        if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
            _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
        }
//Complete phone number
       sphoneNo = "+" + countryCodePicker.getSelectedCountryCode() + _getUserEnteredPhoneNumber;

        //final String sphoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;
        Intent intent = new Intent(SignUp3.this, OTPVerification.class);
        intent.putExtra("username",susername );
        intent.putExtra("email", semail);
        intent.putExtra("password", spassword);
        intent.putExtra("gender",sgender);
        intent.putExtra("age",sage);
        intent.putExtra("phoneno",sphoneNo);
        finish();
        startActivity(intent);
    }
}