package com.example.user.trackingmapusingfrosquire.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.Adapters.MapMainAdapter;
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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
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
    int Cafe=0,Breakfast=0,Lunch=0,Bar=0,Pub=0,Restaurant=0,HillStation=0,Beach=0,Church=0,Temple=0,Mosque=0,Museum=0,Park=0,Theater=0,Zoo=0;

    DatabaseHandler db;
    List<String> idarr;
JSONArray tempaaa;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_actvity);
        db=new DatabaseHandler(getApplicationContext());
        sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
        Uid=sharedPreferences.getString("User_Id",null);


        idarr =new ArrayList();
        idarr.add("4bf58dd8d48988d16d941735");
        idarr.add("4bf58dd8d48988d120951735");
        idarr.add( "4bf58dd8d48988d10f941735");
        idarr.add("4bf58dd8d48988d147941735");
        idarr.add("4bf58dd8d48988d116941735");
        idarr.add("4bf58dd8d48988d11b941735");
        idarr.add("4bf58dd8d48988d1e2941735");
        idarr.add("4eb1d4d54b900d56c88a45fc");
        idarr.add("4eb1d4d54b900d56c88a45fc");
        idarr.add("4bf58dd8d48988d132941735");
        idarr.add("4bf58dd8d48988d13a941735");
        idarr.add("4bf58dd8d48988d13a941735");
        idarr.add("4bf58dd8d48988d17f941735");
        idarr.add("4bf58dd8d48988d182941735");
        idarr.add("4bf58dd8d48988d181941735");
        idarr.add("4bf58dd8d48988d17b941735");


        rview = (RecyclerView) findViewById(R.id.recyclerview);
        rview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rview.setLayoutManager(layoutManager);


        rview2 = (RecyclerView) findViewById(R.id.rviewRute);
        rview2.setHasFixedSize(true);
//       LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rview2.setLayoutManager(layoutManager2);


        rute=new ArrayList();
        rute.add("Route 1");
        rute.add("Route 2");
        rute.add("Route 3");

        adapter=new MapMainAdapter(rute,getApplicationContext());
        rview.setAdapter(adapter);

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






            Log.e("JSONHotel@mapActivity-->",JSOnHotel+"");

        } catch (Exception e) {
           Log.e("ExceptionJson-->",e+"");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
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
            for (int j = tempaaa.length() - 1; j > 0; j--) {

                try {

                    JSONObject obj = tempaaa.getJSONObject(j);
                    Log.e("Hotel Array", tempaaa + "");
                    Log.e("Hotel Object", obj + "");
                    double lat = Double.parseDouble(obj.getString("lat"));
                    double lng = Double.parseDouble(obj.getString("lng"));
                    values = new LatLng(lat, lng);


                    googleMap.addMarker(new MarkerOptions().position(values).title(obj.getString("name")).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

                    googleMap.addPolyline(new PolylineOptions().add(templ, values).width(10).color(Color.RED));
                    templ = values;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }

    public class MapMainAdapter extends RecyclerView.Adapter<MapMainAdapter.MyViewHolder>{

        Context context;
        List jsonArray;
        List address;


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name;


            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tvRute);


            }

        }


        public MapMainAdapter(List jsonArray, Context context)
        {

            this.jsonArray=jsonArray;
            this.context=context;

        }

        @Override
        public MapMainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_rut, parent, false);
            return new MapMainAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MapMainAdapter.MyViewHolder holder, final int position) {
            try {

                holder.name.setText(jsonArray.get(position).toString());

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (holder.name.getText().toString().equals("Route 1")) {

                                ad2=new MapRuteAdapter(tempaaa,getApplicationContext(),gmap);
                                rview2.setAdapter(ad2);

                                JSONObject finalob = new JSONObject();
                                finalob.put("name", "Starting Point");

                                finalob.put("lat", lat);
                                finalob.put("lng", lng);
                                tempaaa.put(finalob);
                                finalOut=new JSONArray();
                                finalOut=tempaaa;
                            } else if (holder.name.getText().toString().equals("Route 2")) {
                                JSONArray arr = new JSONArray();

                                for (int i = tempaaa.length()-1; i >= 0; i--)

                                {
                                    JSONObject vsss = tempaaa.getJSONObject(i);
                                    JSONObject finalob = new JSONObject();

                                    finalob.put("name", vsss.getString("name"));

                                    finalob.put("lat", vsss.getString("lat"));
                                    finalob.put("lng", vsss.getString("lng"));


                                    arr.put(finalob);
                                }
                                JSONObject finalob = new JSONObject();
                                finalob.put("name", "Starting Point");

                                finalob.put("lat", lat);
                                finalob.put("lng", lng);
                                tempaaa.put(finalob);
                                finalOut=new JSONArray();
                                finalOut=arr;

                                ad2=new MapRuteAdapter(arr,getApplicationContext(),gmap);
                                rview2.setAdapter(ad2);

                            } else if (jsonArray.get(position).toString().equals("Route 3")) {

                                JSONArray arrdd=shuffleJsonArray(tempaaa);
                                JSONObject finalob = new JSONObject();
                                finalob.put("name", "Starting Point");

                                finalob.put("lat", lat);
                                finalob.put("lng", lng);
                                arrdd.put(finalob);
                                finalOut=new JSONArray();
                                finalOut=arrdd;
                                ad2=new MapRuteAdapter(arrdd,getApplicationContext(),gmap);
                                rview2.setAdapter(ad2);
                            }

                        } catch (Exception e) {
                            Log.e("Exception-->", e + "");
                        }
                    }
                });


            } catch (Exception e) {
                Log.d("Exception-->", e + "");

            }
        }

        @Override
        public int getItemCount() {
            return jsonArray.size();
        }
    }

public void SaveRute(View view)
{

    String output=db.AddLoc(finalOut,Uid);
    Toast.makeText(getApplicationContext(),output,Toast.LENGTH_SHORT).show();

}
    public static JSONArray shuffleJsonArray (JSONArray array) throws JSONException {
        // Implementing Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--)
        {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }
}




