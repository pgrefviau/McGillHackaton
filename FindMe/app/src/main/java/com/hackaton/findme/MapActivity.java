package com.hackaton.findme;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    static String REQUESTING_LOCATION_KEY;
    static String LOCATION_KEY;
    GoogleApiClient GoogleAC;
    MapFragment mFragment;
    LocationRequest locRequest;
    Location currentLocation;
    boolean reqLocationUpdates = true;

    static long realTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        buildGoogleAPI();
        updateValFromBundle(savedInstanceState);
    }

    protected synchronized void buildGoogleAPI() {
       GoogleAC = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    protected void createLocationRequest() {
        locRequest = new LocationRequest();
        locRequest.setInterval(5000);
        locRequest.setFastestInterval(2500);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        createLocationRequest();
        if(reqLocationUpdates) {
            startRcvLocation();
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(GoogleAC);
        Toast.makeText(this, "Connected to Google Play Services", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void startRcvLocation() {
        LocationServices.FusedLocationApi.requestLocationUpdates(GoogleAC, locRequest, this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setMyLocationEnabled(true);
        
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        //add sending to couchbase the new location info
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopLocUpdates();
    }

    protected void stopLocUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(GoogleAC, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(GoogleAC.isConnected() && !reqLocationUpdates) {
            startRcvLocation();
        }
    }

    //Save instance
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_KEY, reqLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, currentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    //Instance value updates
    private void updateValFromBundle(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_KEY)) {
                reqLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                currentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
        }
    }


}
