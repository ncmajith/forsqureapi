package com.example.user.trackingmapusingfrosquire.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.trackingmapusingfrosquire.Class.JSONHandler;
import com.example.user.trackingmapusingfrosquire.Controller.DatabaseHandler;
import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlottingMapForApurticularLocation extends AppCompatActivity {

    String apiCall = "https://api.foursquare.com/v2/venues/explore?ll=9.932495,76.275791&query=top%20picks&oauth_token=JDAV0J1MY5AKS2N3S1ZFUSLCOCWEKXCLSN53XHZZ3KOA4HKA&v=20180103";
    String apiCall2 = "https://api.foursquare.com/v2/venues/search?ll=";

    String call = "&oauth_token=JDAV0J1MY5AKS2N3S1ZFUSLCOCWEKXCLSN53XHZZ3KOA4HKA&v=20180103";
    String queryf = "&query=fun";
    String queryfo = "&query=food";
    JSONObject resultAPl,result;
    ProgressDialog dialog;
    JSONArray values;
    SupportMapFragment mapFragment;

    private ProgressDialog pDialog;

    double latitude, logitude,clat,clog;
    private ProgressBar progressBar;
    private TextView txtPercentage;
    public static final int progress_bar_type = 0;
    DatabaseHandler db;
    String catid="";
    JSONArray fulldetails;
    String userid;
    String cat="&categoryId=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Bundle bundle = getIntent().getExtras();
            latitude = Double.parseDouble(bundle.getString("Latitude"));
            logitude = Double.parseDouble(bundle.getString("Logitude"));

            SharedPreferences sharedPreferences = getSharedPreferences("Time", Context.MODE_PRIVATE);

            sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
            userid = sharedPreferences.getString("User_Id", null);


            dialog = new ProgressDialog(this);
            dialog.setMessage("Plotting a day for you... This might take some time depending on your nework connection");
            dialog.show();

            JSONArray ar = new JSONArray();
            JSONObject venueID = new JSONObject();
            venueID.put("Cafe", "4bf58dd8d48988d16d941735");
            venueID.put("Breakfast", "4bf58dd8d48988d120951735");
            venueID.put("Restaurant", "4bf58dd8d48988d10f941735");
            venueID.put("Lunch", "4bf58dd8d48988d147941735");
            venueID.put("Bar", "4bf58dd8d48988d116941735");
            venueID.put("Pub", "4bf58dd8d48988d11b941735");
            venueID.put("Beach", "4bf58dd8d48988d1e2941735");
            venueID.put("Mountain", "4eb1d4d54b900d56c88a45fc");
            venueID.put("HillStation", "4eb1d4d54b900d56c88a45fc");
            venueID.put("Church", "4bf58dd8d48988d132941735");
            venueID.put("Temple", "4bf58dd8d48988d13a941735");
            venueID.put("Mosque", "4bf58dd8d48988d13a941735");
            venueID.put("Theater", "4bf58dd8d48988d17f941735");
            venueID.put("Park", "4bf58dd8d48988d182941735");
            venueID.put("Museum", "4bf58dd8d48988d181941735");
            venueID.put("Zoo", "4bf58dd8d48988d17b941735");
            ar.put(venueID);

            db = new DatabaseHandler(getApplicationContext());
            String pics = db.getAllIntrests(userid);
            String picsArr[] = new String[pics.length()];
            Log.e("Picss---->", pics);
            picsArr = pics.split(",");
            for (String ss : picsArr) {
                if (catid.equals("")) {
                    JSONObject oo = ar.getJSONObject(0);
                    catid = catid + oo.getString(ss.trim());

                } else {
                    catid = catid + ",";
                    JSONObject oo = ar.getJSONObject(0);
                    catid = catid + oo.getString(ss.trim());
                }
            }

            Log.e("catid--->", catid);


            loadJson();
        }catch (Exception e)
        {
            Log.e("Exception-->",e+"");
        }

    }

    private void loadJson()
    {
        Log.e("Url=",apiCall2 + latitude + "," + logitude + catid+ call);

        StringRequest stringReques=new StringRequest(Request.Method.GET, apiCall2 + latitude + "," + logitude + cat+catid+ call,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            resultAPl=new JSONObject(response);

                            Log.e("Response-->",resultAPl.toString());
                            dialog.dismiss();
//                            getFun();

                            JSONHandler jsonHandler=new JSONHandler(resultAPl);
                            JSONArray array=jsonHandler.getOut();
                            Intent getIntent = new Intent(getApplicationContext(), MapActivityForOn.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Full", array + "");
                            bundle.putString("Lat", latitude + "");
                            bundle.putString("lng", logitude + "");
                            getIntent.putExtras(bundle);
                            startActivity(getIntent);

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


public void getFun()
{

    StringRequest stringReques=new StringRequest(Request.Method.GET, apiCall2 + latitude + "," + logitude + queryf + call, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {

                result=new JSONObject(response);
                Log.e("JSOn-->",result+"");
                Log.e("JSOn2-->",resultAPl+"");

                JSONHandler jsonHandler=new JSONHandler(result,resultAPl,clat,clog);

                Log.e("Fun-->",jsonHandler.Hotel()+"");
                Log.e("Food-->",jsonHandler.Food()+"");
                Log.e("Full-->",jsonHandler.Result()+"");

                Intent getIntent = new Intent(getApplicationContext(), MapActivityForOn.class);
                Bundle bundle = new Bundle();
                bundle.putString("JSOnFood", jsonHandler.Food() + "");
                bundle.putString("JSOnHotel", jsonHandler.Hotel() + "");
                bundle.putString("Full", jsonHandler.Result() + "");
                bundle.putString("Lat", clat + "");
                bundle.putString("lng", clog + "");
                getIntent.putExtras(bundle);
                startActivity(getIntent);


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
    dialog.dismiss();
}

}
