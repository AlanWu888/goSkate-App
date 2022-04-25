package com.example.goskate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AddParkFragment extends Fragment {

    public AddParkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_park, container, false);

        // region Define Components
        TextView txt_prompt_login = (TextView) view.findViewById(R.id.txt_prompt_login);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // endregion


        // check if user has already made an account, send to profile screen
        if(firebaseAuth.getCurrentUser() != null) {
            txt_prompt_login.setVisibility(View.VISIBLE);
        }

        return view;
    }
}