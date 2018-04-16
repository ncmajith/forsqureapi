package com.example.user.trackingmapusingfrosquire.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.Activity.PlottingMap;
import com.example.user.trackingmapusingfrosquire.Activity.PlottingMapForApurticularLocation;
import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TravelFragment extends Fragment {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE1 = 2;
    TextView placeName;
    LatLng latLng,clatLng;
    double currentlat;
    double currentlog;
    Button btnGo;
    LocationCallback mLocationCallback;
    Location currentLocation;
    private FusedLocationProviderClient mFusedLocationClient;
ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_layout, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);









      //  edCurrent=(TextView)view.findViewById(R.id.edCurrent);
        placeName=(TextView)view.findViewById(R.id.edPlace);
        btnGo=(Button)view.findViewById(R.id.btnGoo);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                if(placeName.getText().toString().equals("")||placeName.getText().toString().equals(null))
                {
                    flag=1;
                    Toast.makeText(getContext(),"Plece Select a location",Toast.LENGTH_SHORT).show();
                }
                              else {
                    Intent i=new Intent(getContext(),PlottingMapForApurticularLocation.class);
                    String latitude=latLng.latitude+"";
                    String logitude=latLng.longitude+"";
                    String stlatlong=latitude+logitude;
                    Log.d("Latitudeandlogitude-->",stlatlong);
                    Bundle bundle = new Bundle();
                    bundle.putString("Latitude",latitude+"");
                    bundle.putString("Logitude",logitude+"");

                           i.putExtras(bundle);
                    Log.d("Location-->",stlatlong+"");
                    startActivity(i);
                }
            }
        });

        view.findViewById(R.id.edPlace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {

                    Log.d("Exception-->",e+"");
                } catch (GooglePlayServicesNotAvailableException e) {

                    Log.d("Exception-->",e+"");
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                placeName.setText(place.getAddress());

                latLng=place.getLatLng();

                Log.d("Place Name", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.d("Error--->", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }


    }
}
