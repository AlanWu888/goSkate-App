package com.example.goskate;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class CustomTricksFragment extends Fragment {

    private DatabaseHelper databaseHelper;

    public CustomTricksFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_tricks, container, false);

        // region Define Components
        EditText edit_trickName = (EditText)view.findViewById(R.id.edit_trickName);
        ListView listView = (ListView)view.findViewById(R.id.listView_tricks);
        Button btn_addTrick = (Button)view.findViewById(R.id.btn_addTrick);
        Button btn_play = (Button)view.findViewById(R.id.btn_play);
        // endregion

        ArrayList<String> trickNames = new ArrayList<String>();

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, trickNames
        );

        listView.setAdapter(listViewAdapter);

        databaseHelper = new DatabaseHelper(getContext());

        // region Button Functionality
        btn_addTrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currTrick = edit_trickName.getText().toString();
                if (newTrick(currTrick, trickNames) == true) {
                    trickNames.add(edit_trickName.getText().toString());
                    listViewAdapter.notifyDataSetChanged();
                    databaseHelper.insertData(new Data(currTrick));
                    Toast.makeText(getActivity(), currTrick + " added!", Toast.LENGTH_SHORT).show();
                    edit_trickName.setText("");
                }
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass arraylist as parameter to next fragment
                if (trickNames.size()<5) {
                    Toast.makeText(getActivity(), "Please Enter at least five Tricks!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("allTricks", trickNames);

                FragmentTransaction fragmentTransaction_playSkateCustom = getActivity().getSupportFragmentManager().beginTransaction();

                // create new play fragment object with the parameters
                PlayGameFragment playGameFragment = new PlayGameFragment();
                playGameFragment.setArguments(bundle);

                fragmentTransaction_playSkateCustom.replace(R.id.custom_game_container, playGameFragment).commit();
            }
        });
        // endregion

        return view;
    }

    private boolean newTrick(String trickName, ArrayList<String> trickNames) {
        // method to add a new trick to the list, will check input against two criteria - see comments

        if (trickName.isEmpty()) {
            // check that the entered text is non empty
            Toast.makeText(getActivity(), "Please Enter a Trick!", Toast.LENGTH_SHORT).show();
            return false;
        }

        for(int i=0; i<trickNames.size(); i++) {
            // iterate through the arraylist to check for duplicate trick
            // will ignore case and whitespace (via regex)
            if (trickName.replaceAll("\\s+","").equalsIgnoreCase(trickNames.get(i)
                    .replaceAll("\\s+",""))) {
                Toast.makeText(getActivity(), "Duplicate Trick!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}