package com.example.user.trackingmapusingfrosquire.Fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.Activity.ActivityTest;
import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ForsquireSearch extends Fragment {
TextView tvTime,tvEtime;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    List Items;
    String itemselected;
    LatLng latlog;
    MaterialSpinner spinner;
    int PLACE_STATUS=0;
    int PIC_SELECT=0;
    LinearLayout address;
    TextView location;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_home,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.ViewItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PLACE_STATUS >0 && PIC_SELECT >0)
                {


                    Intent i=new Intent(getContext(),ActivityTest.class);

                    String latitude=latlog.latitude+"";
                    String logitude=latlog.longitude+"";
                    String stlatlong=latitude+logitude;
                    Log.d("Latitudeandlogitude-->",stlatlong);
                    Bundle bundle = new Bundle();
                    bundle.putString("Item",itemselected);
                    bundle.putString("Latitude",latitude+"");
                    bundle.putString("Logitude",logitude+"");



                    i.putExtras(bundle);
                    Log.d("Location-->",stlatlong+"");
                    startActivity(i);



                }
                else
                {
                    Toast.makeText(getContext(),"Pleace select a place",Toast.LENGTH_SHORT).show();
                }


            }
        });

        view.findViewById(R.id.places).setOnClickListener(new View.OnClickListener() {
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

        spinner = (MaterialSpinner) view.findViewById(R.id.spItem);
        location=(TextView)view.findViewById(R.id.location);
        Items =new ArrayList();
        Items.add("Food");
        Items.add("Coffee");
        Items.add("NightLife");
        Items.add("Fun");
        Items.add("Shopping");
        spinner.setItems(Items);

        address=(LinearLayout)view.findViewById(R.id.layoutLoCation);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                itemselected=item.toString();
                PIC_SELECT++;
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                location.setText(place.getAddress());
                address.setVisibility(View.VISIBLE);
                latlog=place.getLatLng();
                PLACE_STATUS++;
                Log.d("Place Name", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.d("Error--->", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


  }
