package com.example.goskate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.InflaterInputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText regUsername, regEmail, regPassword, regConfirmPassword;
    Button btn_register;
    TextView txt_loginPrompt;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set selected
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Dice:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Map:
                        startActivity(new Intent(getApplicationContext(), MapActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Profile:
                        return true;
                }
                return false;
            }
        });

        // collect field data
        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regConfirmPassword = findViewById(R.id.regConfirmPassword);
        btn_register = findViewById(R.id.btn_register);
        txt_loginPrompt = findViewById(R.id.txt_loginPrompt);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        // check if user has already made an account, send to profile screen
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String confirmed_password = regConfirmPassword.getText().toString().trim();

                // guard conditions for validation
                if (!(password.equals(confirmed_password))) {
                    regConfirmPassword.setError("Passwords do not match");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    regEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    regPassword.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    regConfirmPassword.setError("Please confirm your password");
                    return;
                }
                if (regPassword.length() < 6) {
                    regPassword.setError("Password must be at least 6 Characters long");
                    return;
                }

                // Show progress bar to show registration is progressing
                progressBar.setVisibility(View.VISIBLE);


                // Register the user to FireBase
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // If registration is successful, direct the user to the profile page
                            Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            // If registration failed, inform the user
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        txt_loginPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to login screen if the user has got an account
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}