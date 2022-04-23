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

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    Button btn_login;
    TextView txt_registerPrompt;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set selected
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Dice:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Map:
                        startActivity(new Intent(getApplicationContext(), MapActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Profile:
                        return true;

                    case R.id.Tutorial:
                        startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // collect field data
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btn_login = findViewById(R.id.btn_login);
        txt_registerPrompt = findViewById(R.id.txt_registerPrompt);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        // check if user has already made an account, send to profile screen
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                // guard conditions for validation
                if (TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is Required");
                    return;
                }
                if (loginPassword.length() < 6) {
                    loginPassword.setError("Password must be at least 6 Characters long");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // Authenticate the user
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // If registration is successful, direct the user to the profile page
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            // If registration failed, inform the user
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        txt_registerPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to register screen if the user has not got an account
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }


}