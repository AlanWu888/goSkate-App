package com.example.goskate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText regUsername, regEmail, regPassword, regConfirmPassword;
    private Button btn_register;
    private TextView txt_loginPrompt;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    private FirebaseFirestore fStore;        // create Firestore instance
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // region BottomNavigation Code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Dice:
                        startActivity(new Intent(getApplicationContext(), DiceActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Map:
                        startActivity(new Intent(getApplicationContext(), MapActivity.class));
                        overridePendingTransition(0,0);
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
        // endregion

        // region Define Components
        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPassword = findViewById(R.id.regPassword);
        regConfirmPassword = findViewById(R.id.regConfirmPassword);
        btn_register = findViewById(R.id.btn_register);
        txt_loginPrompt = findViewById(R.id.txt_loginPrompt);
        progressBar = findViewById(R.id.progressBar);
        // endregion

        firebaseAuth = FirebaseAuth.getInstance();      // instantiate
        fStore = FirebaseFirestore.getInstance();       // instantiate

        // check if user has already made an account, send to profile screen
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
        }

        btn_register.setOnClickListener(new View.OnClickListener() {
            // Register the user based on the inputs made by user
            @Override
            public void onClick(View view) {
                // region Collect Data
                String email = regEmail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String confirmed_password = regConfirmPassword.getText().toString().trim();
                String username = regUsername.getText().toString().trim();
                // endregion

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
                            Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();

                            // data is also inserted to the firebase firestore collections here
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            // create user data via hashmap
                            Map<String, Object> user = new HashMap<>();
                            user.put("username", username);
                            user.put("email", email);
                            user.put("biography", "no biography yet");
                            user.put("contributions", "0");
                            user.put("reviews", "0");

                            // insert to cloud database here
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "onSuccess: user profile is created for " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.toString());
                                }
                            });

                            // If registration is successful, direct the user to the profile page
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