package com.example.user.trackingmapusingfrosquire.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.trackingmapusingfrosquire.Adapters.ReSultAdapter;

import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActivityTest extends AppCompatActivity {
    private static final String API_URL = "https://api.foursquare.com/v2/venues/search?";

    private static final String ACCESS_TOCKEN = "&oauth_token=JDAV0J1MY5AKS2N3S1ZFUSLCOCWEKXCLSN53XHZZ3KOA4HKA";
    private static final String LOCATION="ll=";
    private static final String QUERY="&query=";
    private static final String VERSION="&v=20180103";
    private static final String CLIENT_ID = "MNPPPR4QVDF0TMNGO5GNH1IRWP4JBACJ4CRGMIFI53ICXJAP";
    private static final String CLIENT_SE="5YJPZOBZH4Q04KNN1W4BFOZHZUTBDKQVV1S5WJUM2P5MKYPQ";
static  String apicall="https://api.foursquare.com/v2/venues/search?ll=9.932495,76.275791&query=coffee&oauth_token=JDAV0J1MY5AKS2N3S1ZFUSLCOCWEKXCLSN53XHZZ3KOA4HKA&v=20180103";
    JSONArray idArray;
    JSONObject jrr;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ProgressDialog builder;
    String qry;
    double latitude,logitude;
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bundle bundle = getIntent().getExtras();
        qry=bundle.getString("Item");
        latitude=Double.parseDouble(bundle.getString("Latitude"));
        logitude=Double.parseDouble(bundle.getString("Logitude"));
        location=new LatLng(latitude,logitude);

        builder=new ProgressDialog(this);
        builder.setMessage("Please Wait");
        builder.show();
        recyclerView = (RecyclerView) findViewById(R.id.rview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadJson();

    }

    private void loadJson()
    {

        StringRequest stringReques=new StringRequest(Request.Method.GET, API_URL+LOCATION+latitude+","+logitude+QUERY+qry+ACCESS_TOCKEN+VERSION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject s=new JSONObject(response);
                            Log.d("Actual_Out..>",""+s);
                            JSONObject jsonResponse = s.getJSONObject("response");
                            Log.d("JSON_resPonse_Object..>",""+jsonResponse);
                            JSONArray name = jsonResponse.getJSONArray("venues");

                            adapter=new ReSultAdapter(name,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            Log.d("name_array-->",name+"");

                            for(int i=0;i<name.length();i++)
                            {
                                JSONObject o=name.getJSONObject(i);

                                Log.e("Valuesssss",o.getString("id"));

                                GetPicture(o.getString("id"));

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringReques);

    }

    public void GetPicture(String iddd)
    {
        StringRequest stringReques=new StringRequest(Request.Method.GET, "https://api.foursquare.com/v2/venues/"+iddd+"/photos?group=venue&limit=1"+ACCESS_TOCKEN+VERSION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            builder.dismiss();

//                            adapter=new ReSultAdapter(name,getApplicationContext());
//                            recyclerView.setAdapter(adapter);
                            Log.e("Picture-->",response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringReques);
    }

}
