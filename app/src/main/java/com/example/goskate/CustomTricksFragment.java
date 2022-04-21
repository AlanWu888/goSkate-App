package com.example.goskate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
        Button btn_play = (Button)view.findViewById(R.id.btn_play);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, trickNames
        );

        listView.setAdapter(listViewAdapter);

        btn_addTrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currTrick = edit_trickName.getText().toString();
                if (newTrick(currTrick, trickNames) == true) {
                    trickNames.add(edit_trickName.getText().toString());
                    listViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), currTrick + " + added!", Toast.LENGTH_SHORT).show();
                    edit_trickName.setText("");
                }
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass arraylist as parameter to next fragment
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("allTricks", trickNames);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                // create new play fragment object with the parameters
                PlayGameFragment playGameFragment = new PlayGameFragment();
                playGameFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.custom_game_container, playGameFragment).commit();
            }
        });

        return view;
    }

    private boolean newTrick(String trickName, ArrayList<String> trickNames) {
        // check that the entered text is non empty
        if (trickName.isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter a Trick!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // iterate through the arraylist to check for duplicate trick
        for(int i=0; i<trickNames.size(); i++) {
            // will ignore case and whitespace (via regex - clean up this code if time avail)
            if (trickName.replaceAll("\\s+","").equalsIgnoreCase(trickNames.get(i)
                    .replaceAll("\\s+",""))) {
                Toast.makeText(getActivity(), "Duplicate Trick!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}