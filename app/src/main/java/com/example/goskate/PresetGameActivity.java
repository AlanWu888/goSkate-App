package com.example.goskate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PresetGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_game);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set selected
        bottomNavigationView.setSelectedItemId(R.id.Dice);
        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Dice:
                        return true;

                    case R.id.Map:
                        startActivity(new Intent(getApplicationContext(), MapActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Tutorial:
                        startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FragmentTransaction fragmentTransaction_chooseDifficulty = getSupportFragmentManager().beginTransaction();
        fragmentTransaction_chooseDifficulty.add(R.id.preset_game_container, new ChooseDifficultyFragment()).commit();
    }
}