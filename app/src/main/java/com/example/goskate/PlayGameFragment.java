package com.example.goskate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PlayGameFragment extends Fragment {
    private int letters = 0;    // start a letters counter to count letters gained by player

    public PlayGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);

        // define components
        Button btn_start = (Button) view.findViewById(R.id.btn_startGame);
        ImageButton btn_make = (ImageButton) view.findViewById(R.id.btn_make);
        ImageButton btn_fail = (ImageButton) view.findViewById(R.id.btn_fail);
        TextView txt_instructions4 = (TextView) view.findViewById(R.id.txt_instructions4);
        TextView txt_nextTrick = (TextView) view.findViewById(R.id.txt_nextTrick);
        TextView lbl_S = (TextView) view.findViewById(R.id.lbl_S);
        TextView lbl_K = (TextView) view.findViewById(R.id.lbl_K);
        TextView lbl_A = (TextView) view.findViewById(R.id.lbl_A);
        TextView lbl_T = (TextView) view.findViewById(R.id.lbl_T);
        TextView lbl_E = (TextView) view.findViewById(R.id.lbl_E);

        Bundle bundle = getArguments();
        ArrayList enteredTricks = bundle.getStringArrayList("allTricks");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextTrick = getNextTrick(enteredTricks);
                txt_nextTrick.setText(nextTrick);
                enteredTricks.remove(nextTrick);

                // hide the start and instructions prompt when the game starts
                btn_start.setVisibility(View.INVISIBLE);
                txt_instructions4.setVisibility(View.INVISIBLE);
                btn_make.setVisibility(View.VISIBLE);
                btn_fail.setVisibility(View.VISIBLE);
            }
        });

        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((letters >= 5) || (enteredTricks.isEmpty())){
                    // stop the game if the user has accumulated all 5 letters
                    Toast.makeText(getActivity(), "Game Finished!", Toast.LENGTH_SHORT).show();
                    System.out.println("MAKE :: GAME ENDED");
                    return;
                }

                if (enteredTricks.size() == 1) {
                    // stop the game if there are no more tricks left
                    txt_nextTrick.setText(enteredTricks.get(0).toString());
                    enteredTricks.remove(0);
                } else {
                    // randomly pick tricks from the trick list and update the textView showing it
                    String nextTrick = getNextTrick(enteredTricks);
                    txt_nextTrick.setText(nextTrick);
                    enteredTricks.remove(nextTrick);
                    System.out.print(enteredTricks.size() + " :: ");
                    System.out.println(enteredTricks.toString());
                }
            }
        });

        btn_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letters = letters + 1;
                if ((letters >= 6) || (enteredTricks.isEmpty())){
                    Toast.makeText(getActivity(), "Game Finished!", Toast.LENGTH_SHORT).show();
                    System.out.println("FAIL :: GAME ENDED");
                    return;
                }
                if (enteredTricks.size() == 1) {
                    // stop the game if there are no more tricks left
                    txt_nextTrick.setText(enteredTricks.get(0).toString());
                    enteredTricks.remove(0);
                } else {
                    // randomly pick tricks from the trick list and update the textView showing it
                    String nextTrick = getNextTrick(enteredTricks);
                    txt_nextTrick.setText(nextTrick);
                    enteredTricks.remove(nextTrick);
                    System.out.print(enteredTricks.size() + " :: ");
                    System.out.println(enteredTricks.toString());
                }

                // display letters based on "letters counter"
                switch(letters) {
                    case 1:
                        lbl_S.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        lbl_K.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        lbl_A.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        lbl_T.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        lbl_E.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private String getNextTrick(ArrayList<String> enteredTricks) {
        String nextTrick = "";

        if (enteredTricks.size() > 1) {
            // randomly pick tricks from the trick list
            Random randomizer = new Random();
            nextTrick = enteredTricks.get(randomizer.nextInt(enteredTricks.size())).toString();
        }
        return nextTrick;
    }
}