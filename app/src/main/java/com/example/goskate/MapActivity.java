package com.example.goskate;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private LatLng centerLocation;

    private ImageButton btn_addLocation;

    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // set selected
        bottomNavigationView.setSelectedItemId(R.id.Map);

        // perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.Dice:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Map:
                        return true;

                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Tutorial:
                        startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btn_addLocation = findViewById(R.id.btn_addLocation);
        btn_addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddLocationActivity.class));
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // center location to uk
        centerLocation = new LatLng(51.506751202151534, -0.1271100905535411);

        loadParks();
        loadShops();
        loadSpots();
    }

    private void loadParks() {
        FirebaseFirestore.getInstance()
                .collection("parks")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        Log.d("TAG", "Successfully accessed firestore for parks");
                        for (DocumentSnapshot snapshot: snapshotList) {
                            Log.d("TAG", "onSuccess: " + snapshot.getData().toString());
                            Log.d("TAG", snapshot.getId());

                            GeoPoint geoPoint = snapshot.getGeoPoint("geolocation");

                            marker = new MarkerOptions()
                                    .title(snapshot.getString("name"))
                                    .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                                    .snippet(snapshot.getString("description"))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                            mMap.addMarker(marker);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });
    }

    private void loadShops() {
        FirebaseFirestore.getInstance()
                .collection("shops")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        Log.d("TAG", "Successfully accessed firestore for parks");
                        for (DocumentSnapshot snapshot: snapshotList) {
                            Log.d("TAG", "onSuccess: " + snapshot.getData().toString());
                            Log.d("TAG", snapshot.getId());

                            GeoPoint geoPoint = snapshot.getGeoPoint("geolocation");

                            marker = new MarkerOptions()
                                    .title(snapshot.getString("name"))
                                    .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                                    .snippet(snapshot.getString("description"))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                            mMap.addMarker(marker);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });
    }

    private void loadSpots() {
        FirebaseFirestore.getInstance()
                .collection("spots")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        Log.d("TAG", "Successfully accessed firestore for parks");
                        for (DocumentSnapshot snapshot: snapshotList) {
                            Log.d("TAG", "onSuccess: " + snapshot.getData().toString());
                            Log.d("TAG", snapshot.getId());

                            GeoPoint geoPoint = snapshot.getGeoPoint("geolocation");

                            marker = new MarkerOptions()
                                    .title(snapshot.getString("name"))
                                    .position(new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude()))
                                    .snippet(snapshot.getString("description"))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                            mMap.addMarker(marker);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: ", e);
                    }
                });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 11));
    }
}