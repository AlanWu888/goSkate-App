package com.example.goskate;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DisplayProfileFragment extends Fragment {
    private TextView username, email, biography, contributions, reviews;
    private Button btn_logout, btn_edit;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    public DisplayProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_profile, container, false);

        // region Define Components
        username = view.findViewById(R.id.txt_username);
        email = view.findViewById(R.id.txt_email);
        biography = view.findViewById(R.id.txt_bio);
        contributions = view.findViewById(R.id.txt_valueContributions);
        reviews = view.findViewById(R.id.txt_valueReviews);
        btn_logout = view.findViewById(R.id.btn_profileLogout);
        btn_edit = view.findViewById(R.id.btn_editProfile);
        // endregion

        // instantiate firebase objects
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        // get User ID
        userID = fAuth.getCurrentUser().getUid();

        // retrieve data from firebase and display in correct places
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                username.setText(documentSnapshot.getString("username"));
                email.setText(documentSnapshot.getString("email"));
                biography.setText(documentSnapshot.getString("biography"));
                contributions.setText(documentSnapshot.getString("contributions"));
                reviews.setText(documentSnapshot.getString("reviews"));
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction_editProfile = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction_editProfile.replace(R.id.profile_container, new EditProfileFragment()).commit();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        return view;
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}