package com.example.user.trackingmapusingfrosquire.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MapActivityForOn extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    JSONArray tempaaa;
    LatLng templ;
    JSONArray valueArr,JSOnHotel;
    JSONArray valuesHotel;
    LatLng values,values2,cmloc,dests;
    double lat,lng;
    int time=0;
    int temp=0;
    int TimeStamp;
    List rute;
    RecyclerView rview,rview2;
    RecyclerView.Adapter adapter,ad2;
    GoogleMap gmap;
    JSONArray finalOut;
    String Uid;
    SharedPreferences sharedPreferences;
    DatabaseHandler db;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_actvityone);
        db=new DatabaseHandler(getApplicationContext());
        sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
        Uid=sharedPreferences.getString("User_Id",null);


        rview2 = (RecyclerView) findViewById(R.id.rviewRute);
        rview2.setHasFixedSize(true);
//       LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rview2.setLayoutManager(layoutManager2);

         Bundle bundle = getIntent().getExtras();
        try {
            valueArr=new JSONArray(bundle.getString("Full"));
            lat=Double.parseDouble(bundle.getString("Lat"));
            lng=Double.parseDouble(bundle.getString("lng"));



            for(int n = 0; n < JSOnHotel.length(); n++)
            {
                JSONObject object = JSOnHotel.getJSONObject(n);

                valueArr.put(object);
                // do some stuff....
            }
            JSONObject object=new JSONObject();

            object.put("lng",lng);

            object.put("lat",lat);
            object.put("name","Starting point");
            valueArr.put(object);

            Log.e("JSONFood@mapActivity-->",valueArr+"");
            Log.e("JSONHotel@mapActivity-->",JSOnHotel+"");

            ad2=new MapRuteAdapter(valueArr,getApplicationContext(),gmap);
            rview2.setAdapter(ad2);

        } catch (Exception e) {
            Log.e("ExceptionJson-->",e+"");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cmloc = new LatLng(lat, lng);
        this.gmap=googleMap;
        tempaaa=new JSONArray();
        tempaaa=checkDuplicity(valueArr);
        ad2=new MapRuteAdapter(tempaaa,getApplicationContext(),googleMap);
        rview2.setAdapter(ad2);

        Log.wtf("Location-->", cmloc + "");
        templ = cmloc;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cmloc, 18));
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style));

        googleMap.addMarker(new MarkerOptions().position(templ).title("Starting Point").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        for (int i = 0; i < tempaaa.length(); i++) {
            try {

                JSONObject obj = tempaaa.getJSONObject(i);
                Log.e("Hotel Array", tempaaa + "");
                Log.e("Hotel Object", obj + "");
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
    public  JSONArray checkDuplicity(JSONArray array) {
        JSONArray tempArray = new JSONArray();
        try {

            Set<String> stationCodes = new HashSet<String>();

            for (int i = 0; i < array.length(); i++) {
                String stationCode = array.getJSONObject(i).getString("id");
                if (stationCodes.contains(stationCode)) {
                    continue;
                } else {
                    stationCodes.add(stationCode);
                    Log.e("Station Codes-->",stationCode);
                    Log.e("Set-->",stationCodes.toString());
                    tempArray.put(array.getJSONObject(i));
                }

            }

        } catch (Exception e) {
            Log.e("Exception-->", e + "");
        }

        Log.e("Final TempArray",tempArray.toString()+"");
        return tempArray;
    }
}




