package com.example.goskate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LocationTypeFragment extends Fragment {

    public LocationTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_type, container, false);

        // region Define Components
        Button btn_addShop = (Button) view.findViewById(R.id.btn_addShop);
        Button btn_addSpot = (Button) view.findViewById(R.id.btn_addSpot);
        Button btn_addPark = (Button) view.findViewById(R.id.btn_addPark);
        // endregion

        // region Button Functionality
        btn_addShop.setOnClickListener(new View.OnClickListener() {
            // Replaces the fragment in add_location_container to display the fragment to add a shop
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction_addLocation = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction_addLocation.replace(R.id.add_location_container, new AddShopFragment()).commit();
            }
        });

        btn_addSpot.setOnClickListener(new View.OnClickListener() {
            // Replaces the fragment in add_location_container to display the fragment to add a shop
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction_addLocation = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction_addLocation.replace(R.id.add_location_container, new AddSpotFragment()).commit();
            }
        });

        btn_addPark.setOnClickListener(new View.OnClickListener() {
            // Replaces the fragment in add_location_container to display the fragment to add a shop
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction_addLocation = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction_addLocation.replace(R.id.add_location_container, new AddParkFragment()).commit();
            }
        });
        // endregion

        return view;
    }
}