package com.example.salus;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SignIn extends AppCompatActivity {
    Button login;
    EditText email,password;
    TextView signup,forgotpassword;
    FirebaseDatabase database;
    String  entered_email,entered_password;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //disable light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //disable screen orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //bindings.............
        email=findViewById(R.id.edit_email);
        password=findViewById(R.id.edit_password);
        forgotpassword=findViewById(R.id.text_forgotpassword);
        login=findViewById(R.id.btn_login);
        signup=findViewById(R.id.text_signup);
        //getting istances for Firebase database
        database = FirebaseDatabase.getInstance();
       // FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);

        //onclick listerner of forget password
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,NewPassword.class);
                startActivity(intent);
            }
        });


     //onclick listerner of login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entered_email=email.getText().toString().trim();
                entered_password=password.getText().toString().trim();


                if (entered_email.isEmpty()){
                    Toast.makeText(SignIn.this,"PLease Enter The  Email",Toast.LENGTH_SHORT).show();

                }
                else if(entered_password.isEmpty()){
                    Toast.makeText(SignIn.this,"PLease Enter The Password",Toast.LENGTH_SHORT).show();
                }
                else{
                    String editedemail=entered_email.replace(".","&period;");
                    loginfunction( editedemail);

                }

            }
        });



    }
    // function that checks  whether the user have already logged in!!

//    protected void onResume() {
//        super.onResume();
//        // Fetching the stored data from the SharedPreference
//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        String check_email = sh.getString("email", "");
//        String check_password = sh.getString("password", "");
//
//        if(!check_email.isEmpty() && !check_password.isEmpty()){
//            Intent intent1=new Intent(SignIn.this,MainActivity.class);
//            finish();
//            startActivity(intent1);
//
//        }
//    }

    private void loginfunction(String useremail) {
        String[] Phoneno = new String[5];
        String[] Name = new String[5];
        login.setEnabled(false);

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(useremail).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){


                        DataSnapshot dataSnapshot = task.getResult();
                        String database_password = String.valueOf(dataSnapshot.child("Password").getValue());

                        if(database_password .equals(entered_password)){
                            String database_username = String.valueOf(dataSnapshot.child("User Name").getValue());
                            String database_email = String.valueOf(dataSnapshot.child("Email").getValue());
                            String database_phoneno = String.valueOf(dataSnapshot.child("Phone no").getValue());
                            String database_gender = String.valueOf(dataSnapshot.child("Gender").getValue());
                            String database_age = String.valueOf(dataSnapshot.child("Age").getValue());



                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            // write all the data entered by the user in SharedPreference and apply
                            myEdit.putString("email", email.getText().toString());
                            myEdit.putString("password", password.getText().toString());
                            myEdit.putString("username",database_username);
                            myEdit.putString("phoneno",database_phoneno);
                            myEdit.putString("age",database_age);
                            myEdit.putString("gender",database_gender);

                            Set<String> set1 = new HashSet<String>(Arrays.asList(Name));
                            Set<String> set2 = new HashSet<String>(Arrays.asList(Phoneno));


                            myEdit.putStringSet("Name array", set1);
                            myEdit.putStringSet("Phoneno array", set2);
                            myEdit.apply();
                            Intent intent=new Intent(SignIn.this,MainActivity.class);
                            finish();
                            startActivity(intent);
                            Toast.makeText(SignIn.this,"login sucessful",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(SignIn.this,"Login failed!! Wrong Password",Toast.LENGTH_SHORT).show();
                            login.setEnabled(true);
                        }


                    }else {

                        Toast.makeText(SignIn.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();
                        login.setEnabled(true);

                    }


                }else {

                    Toast.makeText(SignIn.this,"Failed to Fetch data",Toast.LENGTH_SHORT).show();
                    login.setEnabled(true);
                }

               // return false;
            }
        });
        String temp =entered_email.replace(".","&period;");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("contacts/"+temp);

// Attach a ValueEventListener to read the data at the reference location
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i=0;i<5;i++){
                    Phoneno[i]= String.valueOf(dataSnapshot.child("Phone"+(i+1)).getValue());
                    Name[i]= String.valueOf(dataSnapshot.child("Name"+(i+1)).getValue());
                }
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                Set<String> set1 = new HashSet<String>(Arrays.asList(Name));
                Set<String> set2 = new HashSet<String>(Arrays.asList(Phoneno));


                myEdit.putStringSet("Name array", set1);
                myEdit.putStringSet("Phoneno array", set2);
                myEdit.apply();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur
                Log.e(TAG, "Error occurred: " + databaseError.getMessage());
            }
        });

    }

    public void gotosignup1(View view) {
        Intent intent=new Intent(SignIn.this, SignUp1.class);
        finish();
        startActivity(intent);
    }
}