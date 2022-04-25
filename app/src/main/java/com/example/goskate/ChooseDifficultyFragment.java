package com.example.goskate;
// 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
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChooseDifficultyFragment extends Fragment {
    // define directories for textfiles containing different tricks
    final String dir_easy    = "skate_dice/easy.txt";
    final String dir_medium  = "skate_dice/medium.txt";
    final String dir_hard    = "skate_dice/hard.txt";
    final String dir_extreme = "skate_dice/extreme.txt";

    private ArrayList<String>trickList = new ArrayList<>();

    public ChooseDifficultyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_difficulty, container, false);

        // region Define components
        Button btn_easy = (Button) view.findViewById(R.id.btn_easy);
        Button btn_medium = (Button) view.findViewById(R.id.btn_medium);
        Button btn_hard = (Button) view.findViewById(R.id.btn_hard);
        Button btn_extreme = (Button) view.findViewById(R.id.btn_extreme);
        // endregion

        // region Button functionality
        btn_easy.setOnClickListener(new View.OnClickListener() {
            // play game with easy difficulty
            @Override
            public void onClick(View view) {
                trickList.addAll(readTextFiles(dir_easy));
                playSkate(trickList);
            }
        });

        btn_medium.setOnClickListener(new View.OnClickListener() {
            // play game with medium difficulty
            @Override
            public void onClick(View view) {
                trickList.addAll(readTextFiles(dir_easy));
                trickList.addAll(readTextFiles(dir_medium));
                playSkate(trickList);
            }
        });

        btn_hard.setOnClickListener(new View.OnClickListener() {
            // play game with hard difficulty
            @Override
            public void onClick(View view) {
                trickList.addAll(readTextFiles(dir_easy));
                trickList.addAll(readTextFiles(dir_medium));
                trickList.addAll(readTextFiles(dir_hard));
                playSkate(trickList);
            }
        });

        btn_extreme.setOnClickListener(new View.OnClickListener() {
            // play game with extreme difficulty
            @Override
            public void onClick(View view) {
                trickList.addAll(readTextFiles(dir_easy));
                trickList.addAll(readTextFiles(dir_medium));
                trickList.addAll(readTextFiles(dir_hard));
                trickList.addAll(readTextFiles(dir_extreme));
                playSkate(trickList);
            }
        });
        // endregion

        return view;
    }

    private void playSkate(ArrayList<String> trickNames) {
        // loads the next fragment with an arraylist of String objects containing trick names
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("allTricks", trickNames);

        FragmentTransaction fragmentTransaction_playSkate = getActivity().getSupportFragmentManager().beginTransaction();

        // create new play fragment object with the parameters
        PlayGameFragment playGameFragment = new PlayGameFragment();
        playGameFragment.setArguments(bundle);

        fragmentTransaction_playSkate.replace(R.id.preset_game_container, playGameFragment).commit();
    }

    private ArrayList readTextFiles(String filename) {
        // Read textfiles for trick names and return them as an arraylist of String objects
        ArrayList<String> textTricks = new ArrayList<String>();

        /*
            Code taken from
                https://stackoverflow.com/questions/9544737/read-file-from-assets
            by HpTerm, answered Mar 3, 2012 at 8:53
         */
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open(filename)));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                textTricks.add(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return textTricks;
    }
}