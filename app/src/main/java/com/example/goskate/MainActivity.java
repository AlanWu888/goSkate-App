package com.example.goskate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> tricks;
    private ArrayAdapter<String> tricksAdapter;
    private ListView listView_tricks;
    private Button btn_add_trick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView_tricks = findViewById(R.id.listView_tricks);
        btn_add_trick = findViewById(R.id.btn_add_trick);

        btn_add_trick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });

        tricks = new ArrayList<>();
        tricksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tricks);
        listView_tricks.setAdapter(tricksAdapter);
        setUpListViewListener();

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
                }
                return false;
            }
        });
    }

    private void setUpListViewListener() {
        listView_tricks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "'" + tricks.get(i)  + "' removed", Toast.LENGTH_LONG).show();

                tricks.remove(i);
                tricksAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View view) {
        EditText input = findViewById(R.id.edit_trick_name);
        String trickText = input.getText().toString();

        if(tricks.contains(trickText)) {
            Toast.makeText(getApplicationContext(), "Duplicate Trick!", Toast.LENGTH_LONG).show();
        } else {
            if (!(trickText.equals(""))) {
                tricksAdapter.add(trickText);
                Toast.makeText(getApplicationContext(), "'" + trickText  + "' added", Toast.LENGTH_LONG).show();
                input.setText("");
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a trick", Toast.LENGTH_LONG).show();
            }
        }
    }

}