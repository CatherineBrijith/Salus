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

public class ForgotPassword extends AppCompatActivity {

    String sphoneNo, semail, spassword;
    CountryCodePicker countryCodePicker;
    EditText phoneNumber;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.edit_phoneno1);
        semail = getIntent().getStringExtra("forgot_email");
        spassword = getIntent().getStringExtra("forgot_password");


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
        Intent intent = new Intent(ForgotPassword.this, OTPVerification_f.class);
        intent.putExtra("forgot_email", semail);
        intent.putExtra("forgot_password", spassword);
        intent.putExtra("forgot_phoneno", sphoneNo);

        finish();
        startActivity(intent);
    }
}