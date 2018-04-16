package com.example.user.trackingmapusingfrosquire.Activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class loc extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    private TextView mLatitudeTextview;
    LatLng latLng;
    double currentlat;
    double currentlog;
    private GoogleApiClient mGoogleApClient;
    private Location mlocaion;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;
    private long FASTEST_INTERVAL = 2000;
    private LocationManager locationManager;
    //    private UpdateUserLoc mloc;
    Button btn;

    Geocoder geocoder;
    List<Address> addresses;
    LinearLayout linlaHeaderProgress;
    double plong, plat;
    private ProgressDialog progressDialog;
    TextView placeName;
   Button btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_next_layout);
        placeName=(TextView)findViewById(R.id.edPlace);
        btnGo=(Button)findViewById(R.id.btnGoo);
        mGoogleApClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding your current Location..!", true);
        checkLocation();

        placeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(loc.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {

                    Log.d("Exception-->",e+"");
                } catch (GooglePlayServicesNotAvailableException e) {

                    Log.d("Exception-->",e+"");
                }

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                if(placeName.getText().toString().equals("")||placeName.getText().toString().equals(null))
                {
                    flag=1;
                    Toast.makeText(getApplicationContext(),"Plece Select a location",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i=new Intent(getApplicationContext(),PlottingMapForApurticularLocation.class);
                    String latitude=latLng.latitude+"";
                    String logitude=latLng.longitude+"";
                    String stlatlong=latitude+logitude;
                    Log.d("Latitudeandlogitude-->",stlatlong);
                    Bundle bundle = new Bundle();
                    bundle.putString("Latitude",latitude+"");
                    bundle.putString("Logitude",logitude+"");

                    bundle.putString("CurrentLocationlat",currentlat+"");
                    bundle.putString("CurrentLocationLog",currentlog+"");

                    i.putExtras(bundle);
                    Log.d("Location-->",stlatlong+"");
                    startActivity(i);
                }
            }
        });

    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdate();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocaion = LocationServices.FusedLocationApi.getLastLocation(mGoogleApClient);
        if (mlocaion == null) {


            startLocationUpdate();
        }
        if (mlocaion != null) {

            currentlat = mlocaion.getLongitude();
            currentlog = mlocaion.getLatitude();
            progressDialog.dismiss();
        } else {
            Toast.makeText(this, "Location not detected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error:" + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApClient != null) {
            mGoogleApClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApClient.isConnected()) {
            mGoogleApClient.disconnect();
        }
    }

    protected void startLocationUpdate() {
        mLocationRequest = mLocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApClient, mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg="Update Location: "+ Double.toString(location.getLatitude())+","+ Double.toString(location.getLongitude());
//        mLatitudeTextview.setText(String.valueOf(location.getLatitude()));
//        mLogitudeTextView.setText(String.valueOf(location.getLongitude()));


        try {


            //LatLng position=new LatLng(plong,plat);
//    Bundle args=new Bundle();
            //  args.putParcelable("longlat_dataprovider",position);

        }catch(Exception e)
        {

            System.out.print(e);

        }
    }
//  public void  Update(View view)
//  {
//      mloc=new UpdateUserLoc();
//      mloc.execute();
//
//  }




    private boolean checkLocation(){
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    public void showAlert(){
        final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Enabel Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +"use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialog.show();

    }
    private boolean isLocationEnabled(){
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getApplicationContext(), data);
                placeName.setText(place.getAddress());

                latLng=place.getLatLng();

                Log.d("Place Name", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getApplicationContext(), data);
                // TODO: Handle the error.
                Log.d("Error--->", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

}
