package com.example.salus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    TextView username,email,phoneno,gender,age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username=findViewById(R.id.text_username);
       email=findViewById(R.id.text_email);
       phoneno=findViewById(R.id.text_phoneno);
       gender=findViewById(R.id.text_gender);
        age=findViewById(R.id.text_age);
        database = FirebaseDatabase.getInstance();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String database_email = sh.getString("email", "");
        String database_username = sh.getString("username", "");
        String database_phoneno = sh.getString("phoneno", "");
        String database_gender = sh.getString("gender", "");
        String database_age = sh.getString("age", "");
        username.setText(database_username);
        email.setText(database_email);
        phoneno.setText(database_phoneno);
        gender.setText(database_gender);
        age.setText(database_age);




    }
}