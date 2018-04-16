package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.user.trackingmapusingfrosquire.Adapters.MapRuteAdapter;
import com.example.user.trackingmapusingfrosquire.Controller.DatabaseHandler;
import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapActivityForSaved extends AppCompatActivity implements OnMapReadyCallback {
SharedPreferences sharedPreferences;
String Uid;
RecyclerView rview2;
JSONArray jrr;
RecyclerView.Adapter ad2;
    LatLng values,cmloc,templ;
    DatabaseHandler db;
    double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_for_saved);
try {
    sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
    Uid = sharedPreferences.getString("User_Id", null);

    sharedPreferences = getSharedPreferences("Location", Context.MODE_PRIVATE);

    lat = Double.parseDouble(sharedPreferences.getString("Lat", null));
    lon = Double.parseDouble(sharedPreferences.getString("Lon", null));

    db= new DatabaseHandler(getApplicationContext());
    String result = db.GetALLLoc(Uid);
    jrr = new JSONArray(result);

    JSONObject object=new JSONObject();

    object.put("lng",lon);

    object.put("lat",lat);
    object.put("name","Starting point");
    jrr.put(object);
    rview2 = (RecyclerView) findViewById(R.id.rviewRute);
    rview2.setHasFixedSize(true);
    LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    rview2.setLayoutManager(layoutManager2);


    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync((OnMapReadyCallback) this);
}catch (Exception e)
{
    Log.e("Exception-->",e+"");
}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        cmloc = new LatLng(lat, lon);

        ad2=new MapRuteAdapter(jrr,getApplicationContext(),googleMap);
        rview2.setAdapter(ad2);
        templ=cmloc;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cmloc, 18));
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style));
        googleMap.addMarker(new MarkerOptions().position(templ).title("Starting Point").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        for (int i = 0; i < jrr.length(); i++) {
            try {

                JSONObject obj = jrr.getJSONObject(i);

                double lat = Double.parseDouble(obj.getString("lat"));
                double lng = Double.parseDouble(obj.getString("lng"));
                values = new LatLng(lat, lng);


                googleMap.addMarker(new MarkerOptions().position(values).title(obj.getString("name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                googleMap.addPolyline(new PolylineOptions().add(templ, values).width(10).color(Color.BLUE));
                templ = values;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void DeleteButton(View view)
    {
        db.Delete(Uid);
        startActivity(new Intent(getApplicationContext(),UserHomeNav.class));
    }
}
