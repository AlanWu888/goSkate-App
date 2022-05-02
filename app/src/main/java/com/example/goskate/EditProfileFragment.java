package com.example.goskate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditProfileFragment extends Fragment {
    private EditText username, biography;
    private String newUsername, newBiography, userID;
    private Button btn_submit;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // region Define Components
        username = view.findViewById(R.id.et_editUsername);
        biography = view.findViewById(R.id.et_editBiography);
        btn_submit = view.findViewById(R.id.btn_submitChanges);
        // endregion

        // instantiate firebase objects
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        // get User ID
        userID = fAuth.getCurrentUser().getUid();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data
                newUsername = username.getText().toString().trim();
                newBiography = biography.getText().toString().trim();

                if (newUsername.isEmpty() && newBiography.isEmpty()) {
                    Toast.makeText(getActivity(), "No changes detected", Toast.LENGTH_SHORT).show();
                }

                DocumentReference dc = fStore.collection("users").document(userID);

                // check which fields to update for the user
                if (!newUsername.isEmpty()) {
                    dc
                            .update("username", newUsername)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Account updated", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                if (!newBiography.isEmpty()) {
                    dc
                            .update("biography", newBiography)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Account updated", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        return view;
    }
}