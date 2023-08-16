package com.example.salus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp1 extends AppCompatActivity {
    Context context;
    EditText username, email, password, confirm_password;
    Button next1;
    private FirebaseAuth mAuth;
    TextView signin;
    FirebaseDatabase database;
    private DatabaseReference userRef;
    DatabaseReference reference;

    String database_email;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        context = this;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        username = findViewById(R.id.edit_username);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_pass);
        confirm_password = findViewById(R.id.edit_confirmpass);
        next1 = findViewById(R.id.btn_next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFullName() &&  validateEmail() && validatePassword() && equalPassword()) {
                    //uniqueuser();
                    gotosignup2();
                }
            }
        });
    }
    private boolean validateFullName() {
        String val = username.getText().toString().trim();
        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private void uniqueuser(){
        String semail = email.getText().toString().trim();
        String temp=semail.replace(".","&period;");
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(temp).get().addOnCompleteListener(task -> {
            if (task.getResult().exists()){

                Toast.makeText(context, "User With this Email Id already exists", Toast.LENGTH_LONG).show();
            } else {
                gotosignup2();
            }
        });
    }


    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private boolean equalPassword() {
        String spassword1 = password.getText().toString().trim();
        String spassword2= confirm_password.getText().toString().trim();
        if(!spassword1.equals(spassword2)){
            Toast.makeText(this, "Passwords Doesn't Match ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    public void gotosignup2() {

        Intent intent = new Intent(SignUp1.this, SignUp2.class);
        String susername = username.getText().toString().trim();
        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        intent.putExtra("username",susername );
        intent.putExtra("email", semail);
        intent.putExtra("password", spassword);
        finish();
        startActivity(intent);
    }
    private boolean validatePassword() {
        String spassword = password.getText().toString().trim();
        if (spassword.length() < 8) {
            Toast.makeText(this, "Password should contain atleast 8 characters ", Toast.LENGTH_LONG).show();
            return false;
        }

        // Check for mixed case
        if (!spassword.matches(".*[a-z].*") || !spassword.matches(".*[A-Z].*")) {
            Toast.makeText(this, "Password must contain atleast one alphabet", Toast.LENGTH_LONG).show();

            return false;
        }

        // Check for special characters
        if (!spassword.matches(".*[!@#$%^&*?].*"))
            Toast.makeText(this, "Password should contain atleast one special character", Toast.LENGTH_LONG).show();


        return true;
    }


    public void gotosignin(View view) { Intent intent = new Intent(SignUp1.this, SignIn.class);
        finish();
        startActivity(intent);

    }
}