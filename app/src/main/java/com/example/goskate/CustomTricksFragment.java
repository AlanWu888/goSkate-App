package com.example.goskate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CustomTricksFragment extends Fragment {

    public CustomTricksFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_tricks, container, false);

        ArrayList<String> trickNames = new ArrayList<String>();

        EditText edit_trickName = (EditText)view.findViewById(R.id.edit_trickName);
        ListView listView = (ListView)view.findViewById(R.id.listView_tricks);
        Button btn_addTrick = (Button)view.findViewById(R.id.btn_addTrick);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, trickNames
        );

        listView.setAdapter(listViewAdapter);

        btn_addTrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trickNames.add(edit_trickName.getText().toString());
                listViewAdapter.notifyDataSetChanged();

                edit_trickName.setText("");
            }
        });

        return view;
    }
}