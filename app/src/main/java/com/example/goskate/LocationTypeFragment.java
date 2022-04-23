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

        // Define Components
        Button btn_addShop = (Button) view.findViewById(R.id.btn_addShop);
        Button btn_addSpot = (Button) view.findViewById(R.id.btn_addSpot);
        Button btn_addPark = (Button) view.findViewById(R.id.btn_addPark);


        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        AddShopFragment addShopFragment = new AddShopFragment();
        fragmentTransaction.replace(R.id.add_location_container, addShopFragment).commit();

        return view;
    }
}