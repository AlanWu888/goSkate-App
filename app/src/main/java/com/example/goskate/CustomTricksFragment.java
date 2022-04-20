package com.example.goskate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class CustomTricksFragment extends Fragment {

    public CustomTricksFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_tricks, container, false);

        String[] menuItems = {"Ollie", "Nollie", "FrontSide 180"};

        ListView listView = (ListView)view.findViewById(R.id.listView_tricks);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItems
        );

        listView.setAdapter(listViewAdapter);


        return view;
    }
}