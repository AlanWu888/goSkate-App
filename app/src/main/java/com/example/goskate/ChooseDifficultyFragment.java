package com.example.goskate;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChooseDifficultyFragment extends Fragment {
    // define directories for textfiles containing different tricks
    final String dir_easy    = "easy.txt";
    final String dir_medium  = "medium.txt";
    final String dir_hard    = "hard.txt";
    final String dir_extreme = "extreme.txt";

    private ArrayList<String>trickList = new ArrayList<>();

    public ChooseDifficultyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_difficulty, container, false);

        // define components
        Button btn_easy = (Button) view.findViewById(R.id.btn_easy);
        Button btn_medium = (Button) view.findViewById(R.id.btn_medium);
        Button btn_hard = (Button) view.findViewById(R.id.btn_hard);
        Button btn_extreme = (Button) view.findViewById(R.id.btn_extreme);

        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    private ArrayList readTextFiles(String filename) {
        // Read textfiles for trick names and return them as an arraylist of String objects

        return trickList;
    }
}