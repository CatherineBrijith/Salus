package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class NewContact extends AppCompatActivity {
    CountryCodePicker countryCodePicker1,countryCodePicker2,countryCodePicker3,countryCodePicker4,countryCodePicker5;
    EditText phoneNumber1,phoneNumber2,phoneNumber3,phoneNumber4,phoneNumber5;
    EditText name1,name2,name3,name4,name5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        countryCodePicker1=findViewById(R.id.country_code_picker1);
        phoneNumber1=findViewById(R.id.edit_phoneno1);
        name1=findViewById(R.id.edit_contactname1);

        countryCodePicker2=findViewById(R.id.country_code_picker2);
        phoneNumber2=findViewById(R.id.edit_phoneno2);
        name2=findViewById(R.id.edit_contactname2);

        countryCodePicker3=findViewById(R.id.country_code_picker3);
        phoneNumber3=findViewById(R.id.edit_phoneno3);
        name3=findViewById(R.id.edit_contactname3);

        countryCodePicker4=findViewById(R.id.country_code_picker4);
        phoneNumber4=findViewById(R.id.edit_phoneno4);
        name4=findViewById(R.id.edit_contactname4);

        countryCodePicker5=findViewById(R.id.country_code_picker5);
        phoneNumber5=findViewById(R.id.edit_phoneno5);
        name5=findViewById(R.id.edit_contactname5);

    }

    public void addcontactstodatabase(View view) {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String semail= sh.getString("email", "");

        String sphoneNo1 = "+" + countryCodePicker1.getSelectedCountryCode() + phoneNumber1.getText().toString().trim();
        String sphoneNo2 = "+" + countryCodePicker2.getSelectedCountryCode() + phoneNumber2.getText().toString().trim();
        String sphoneNo3 = "+" + countryCodePicker3.getSelectedCountryCode() + phoneNumber3.getText().toString().trim();
        String sphoneNo4 = "+" + countryCodePicker4.getSelectedCountryCode() + phoneNumber4.getText().toString().trim();
        String sphoneNo5 = "+" + countryCodePicker5.getSelectedCountryCode() + phoneNumber5.getText().toString().trim();

        String sname1=name1.getText().toString().trim();
        String sname2=name2.getText().toString().trim();
        String sname3=name3.getText().toString().trim();
        String sname4=name4.getText().toString().trim();
        String sname5=name5.getText().toString().trim();

        String temp=semail.replace(".","&period;");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contacts/"+temp);
        Map<String, Object> contacts = new HashMap<>();
        contacts.put("Name1",sname1);
        contacts.put("Name2",sname2);
        contacts.put("Name3",sname3);
        contacts.put("Name4",sname4);
        contacts.put("Name5",sname5);
        contacts.put("Phone1",sphoneNo1);
        contacts.put("Phone2",sphoneNo2);
        contacts.put("Phone3",sphoneNo3);
        contacts.put("Phone4",sphoneNo4);
        contacts.put("Phone5",sphoneNo5);
        myRef.setValue(contacts);

        Intent intent = new Intent(NewContact.this,AddContacts.class);
        startActivity(intent);
        finish();
    }
}