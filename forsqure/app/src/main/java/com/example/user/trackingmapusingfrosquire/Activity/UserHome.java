package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity{
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    List Items;
    String itemselected;
    LatLng latlog;
    MaterialSpinner spinner;
    int PLACE_STATUS=0;
    int PIC_SELECT=0;
    LinearLayout address;
    TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        spinner = (MaterialSpinner) findViewById(R.id.spItem);
        location=(TextView) findViewById(R.id.location);
        Items =new ArrayList();
        Items.add("Food");
        Items.add("Coffee");
        Items.add("NightLife");
        Items.add("Fun");
        Items.add("Shopping");
        spinner.setItems(Items);

        address=(LinearLayout)findViewById(R.id.layoutLoCation);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
               itemselected=item.toString();
                PIC_SELECT++;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                location.setText(place.getAddress());
                address.setVisibility(View.VISIBLE);
                latlog=place.getLatLng();
                PLACE_STATUS++;
                Log.d("Place Name", "Place: " + place.getLatLng());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.d("Error--->", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    public void ViewSelected_Item(View view)
    {


if(PLACE_STATUS >0 && PIC_SELECT >0)
{


    Intent i=new Intent(getApplicationContext(),ActivityTest.class);

    String latitude=latlog.latitude+"";
    String logitude=latlog.longitude+"";
    String stlatlong=latitude+logitude;
    Log.d("Latitudeandlogitude-->",stlatlong);
    Bundle bundle = new Bundle();
    bundle.putString("Item",itemselected);
    bundle.putString("Latitude",latitude+"");
    bundle.putString("Logitude",logitude+"");
//    intent.putExtra("Item",itemselected);
//    intent.putExtra("Place",latlog);

    i.putExtras(bundle);
    Log.d("Location-->",stlatlong+"");
    startActivity(i);



}
else
{
    Toast.makeText(getApplicationContext(),"Pleace select a place",Toast.LENGTH_SHORT).show();
}

    }

    public void LoadAutocompleat(View view)
{

    try {
        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException e) {

        Log.d("Exception-->",e+"");
    } catch (GooglePlayServicesNotAvailableException e) {

        Log.d("Exception-->",e+"");
    }
}

}
