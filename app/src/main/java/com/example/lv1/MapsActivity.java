package com.example.lv1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double extractedLat = 0.0;
    double extractedLng = 0.0;

    private GoogleMap mMap;

    public static TextView data;
    Button click, sendAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        data = findViewById(R.id.textViewData);
        click = findViewById(R.id.trackButton);
        sendAlarm = findViewById(R.id.alarmButton);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData process = new fetchData();
                process.execute();

                //isi lat & lng
                /*do {
                    extractedLat = fetchData.getPickLat();
                } while (fetchData.latReady==false);
                do {
                    extractedLng = fetchData.getPickLng();
                } while (fetchData.lngReady==false);*/

                extractedLat = fetchData.getPickLat();
                extractedLng = fetchData.getPickLng();
                //move cursor
                LatLng track = new LatLng(extractedLat, extractedLng);
                mMap.addMarker(new MarkerOptions().position(track).title("Your Kid's Location"));
                CameraPosition trackLoc = CameraPosition.builder().target(track).zoom(15).build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(trackLoc));
            }
        });

        sendAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmData process2 = new alarmData();
                process2.execute();
                Toast.makeText(getApplicationContext(), "Alarm Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Kid and move the camera
        /*LatLng Kid = new LatLng(-6.888161, 107.608963);
        mMap.addMarker(new MarkerOptions().position(Kid).title("Testing"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Kid));
        CameraPosition kidLoc = CameraPosition.builder().target(new LatLng(-6.88816, 107.60896)).zoom(15).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(kidLoc));*/

    }

}