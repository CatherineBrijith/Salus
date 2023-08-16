package com.example.salus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AddContacts extends AppCompatActivity {
    FirebaseDatabase database;
    TextView name1,Phoneno1,name2,Phoneno2,name3,Phoneno3,name4,Phoneno4,name5,Phoneno5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        name1=findViewById(R.id.name1);
        Phoneno1=findViewById(R.id.phoneno1);
        name2=findViewById(R.id.name2);
        Phoneno2=findViewById(R.id.phoneno2);
        name3=findViewById(R.id.name3);
        Phoneno3=findViewById(R.id.phoneno3);
        name4=findViewById(R.id.name4);
        Phoneno4=findViewById(R.id.phoneno4);
        name5=findViewById(R.id.name5);
        Phoneno5=findViewById(R.id.phoneno5);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String database_email = sh.getString("email", "");
        String temp = database_email.replace(".", "&period;");
        Set<String> retrievedSet1 = sh.getStringSet("Name array", null);
        String[] Name = retrievedSet1.toArray(new String[0]);
        Set<String> retrievedSet2 = sh.getStringSet("Phoneno array", null);
        String[] Phoneno = retrievedSet2.toArray(new String[0]);

        name1.setText(Name[0]);
        Phoneno1.setText(Phoneno[0]);
        name2.setText(Name[1]);
        Phoneno2.setText(Phoneno[1]);
        name3.setText(Name[2]);
        Phoneno3.setText(Phoneno[2]);
        name4.setText(Name[3]);
        Phoneno4.setText(Phoneno[3]);
        name5.setText(Name[4]);
        Phoneno5.setText(Phoneno[4]);


    }

    public void gotonewcontact(View view) {
        Intent intent = new Intent(AddContacts.this, NewContact.class);
        startActivity(intent);


    }
}