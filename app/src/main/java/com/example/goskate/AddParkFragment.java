package com.example.goskate;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddParkFragment extends Fragment {
    final String[] spinnerConditions = {"Bad", "Medium", "Good"};

    public AddParkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_park, container, false);

        // region Define Components
        Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
        TextView txt_prompt_login = (TextView) view.findViewById(R.id.txt_prompt_login);
        EditText input_name_park = (EditText) view.findViewById(R.id.input_name_park);
        EditText input_address_park = (EditText) view.findViewById(R.id.input_address_park);
        EditText input_desc_park = (EditText) view.findViewById(R.id.input_desc_park);
        Spinner input_cond_park = (Spinner) view.findViewById(R.id.input_cond_park);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerConditions);
        input_cond_park.setAdapter(adapter);
        // endregion

        // check if user is logged in and show relevant components
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            // if there is no logged in user, show a prompt to log in
            txt_prompt_login.setVisibility(View.VISIBLE);
        } else {
            // if the user is logged in, let them submit the location
            btn_submit.setVisibility(View.VISIBLE);
        }

        // Add functionality to the submit button
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // region Get data from components
                String name, locationName, description, condition;
                name = input_name_park.getText().toString();
                locationName = input_address_park.getText().toString();
                description = input_desc_park.getText().toString();
                condition = input_cond_park.getSelectedItem().toString();
                // endregion

                // region convert address to GeoPoint and add new location to fireStore
                Geocoder geoCoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> address = geoCoder.getFromLocationName(locationName, 1);
                    double latitude = address.get(0).getLatitude();
                    double longitude = address.get(0).getLongitude();

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("geolocation", new GeoPoint(latitude, longitude));
                    map.put("description", description);
                    map.put("condition", condition);
                    map.put("address", locationName);

                    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
                    fireStore.collection("approval_parks").add(map)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("addpark", "onSuccess: task successful");
                                    Toast.makeText(getActivity(), "Location submitted - Awaiting approval by moderators", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), MapActivity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("addpark", "onFailure: task failed");
                                    Toast.makeText(getActivity(), "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), MapActivity.class));
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // endregion
            }

        });

        return view;
    }


}