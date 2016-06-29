package com.emil.taskmanager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button btnGo, btnSave, btnCancell;
    private EditText etToSearch;
    private LatLng loc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancell = (Button) findViewById(R.id.btnCancel);
        etToSearch = (EditText) findViewById(R.id.etToSearch);

        btnGo.setOnClickListener(clickListener);
        btnSave.setOnClickListener(clickListener);
        btnCancell.setOnClickListener(clickListener);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {

        @Override

        public void onClick(View v) {

            if (v == btnGo)
            {
                search(etToSearch.getText().toString());
            }
            if (v == btnCancell) {
                setResult(RESULT_CANCELED);
                finish();

            }
            if (v == btnSave) {
                Intent intent = new Intent();

                //intent.putExtra("loc",loc);

                intent.putExtra("lat",loc.latitude);
                intent.putExtra("lng",loc.longitude);

                setResult(RESULT_OK,intent);
                finish();

            }
        }
    };



    private void search(final String s)
    {
//1.
        AsyncTask<Void,Integer,List<Address>> asyncTask = new AsyncTask<Void, Integer, List<Address>>() {
            Geocoder geocoder;
            List<Address> locations;

            @Override

            protected void onPreExecute()
            {
                locations = null;
                geocoder = new Geocoder(MapsActivity2.this, Locale.getDefault());
                super.onPreExecute();
            }
//5
            @Override
            protected List<Address> doInBackground(Void... params) {

                try {
                    locations=geocoder.getFromLocationName(s,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return locations;
            }

            @Override
            protected void onPostExecute(List<Address> addresses) {
                super.onPostExecute(addresses);
                for (Address a:addresses)
                {
                    //6 baloon ahmar
                     loc = new LatLng(a.getLatitude(),a.getLongitude());
                    MarkerOptions m = new MarkerOptions().position(loc).title(s);
                    mMap.addMarker(m);
                    //7. hzazat mzlima
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16));

                }
            }
        };

        //8.
        asyncTask.execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
