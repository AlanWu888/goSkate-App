package com.example.goskate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayCustomGameFragment extends Fragment {
    public PlayCustomGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_custom_game, container, false);

        Bundle bundle = getArguments();
        ArrayList enteredTricks = bundle.getStringArrayList("allTricks");

        TextView txt_test = (TextView) view.findViewById(R.id.txt_TEST);
        txt_test.setText(enteredTricks.toString());

        // Inflate the layout for this fragment
        return view;
    }
}