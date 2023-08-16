package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    RadioGroup gender;
    RadioButton selectedRadioButton,female,male,others;
    String susername,semail,spassword,sgender,sage;
    Button next2;
    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        gender=findViewById(R.id.radio_group);
        female=findViewById(R.id.female);
        male=findViewById(R.id.male);
        others=findViewById(R.id.others);
        next2=findViewById(R.id.btn_next2);
        datePicker=findViewById(R.id.age_picker);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        semail = getIntent().getStringExtra("email");
        susername = getIntent().getStringExtra("username");
        spassword = getIntent().getStringExtra("password");
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateGender() |  !validateAge() ){
                    return;
                }
                gotosignup3();
            }
        });

    }
    private boolean validateGender() {
        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if (isAgeValid ==0) {
            Toast.makeText(this, "Please select your DOB", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void gotosignup3() {
        Intent intent = new Intent(SignUp2.this, SignUp3.class);
        int selectedRadioButtonId = gender.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedRadioButtonId);
        sgender = selectedRadioButton.getText().toString().trim();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int birthyear = datePicker.getYear();
        int age = currentYear - birthyear;
        sage = Integer.toString(age);

        intent.putExtra("username",susername );
        intent.putExtra("email", semail);
        intent.putExtra("password", spassword);
        intent.putExtra("gender",sgender);
        intent.putExtra("age",sage);
        finish();
        startActivity(intent);
    }
}