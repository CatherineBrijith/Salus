package com.example.salus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class NewPassword extends AppCompatActivity {
    EditText password, confirm_password, email;
    Button submit;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String semail, spassword;
    private DatabaseReference userRef;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        password = findViewById(R.id.edit_pass);
        confirm_password = findViewById(R.id.edit_confirmpass);
        email = findViewById(R.id.edit_email);
        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validatePassword() | !equalPassword() | !validateEmail()) {
                    return;
                }
                updatepassword();
            }
        });
    }

    private void updatepassword() {
        semail = email.getText().toString().trim();
        spassword = confirm_password.getText().toString().trim();
        Intent intent1 = new Intent(NewPassword.this, ForgotPassword.class);
        intent1.putExtra("forgot_email", semail);
        intent1.putExtra("forgot_password", spassword);
        finish();
        startActivity(intent1);

    }


    private boolean equalPassword() {
        String spassword1 = password.getText().toString().trim();
        String spassword2 = confirm_password.getText().toString().trim();
        if (!spassword1.equals(spassword2)) {
            Toast.makeText(this, "Passwords Doesn't Match ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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



}