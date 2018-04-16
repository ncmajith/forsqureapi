package com.example.user.trackingmapusingfrosquire.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.trackingmapusingfrosquire.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28-12-2017.
 */

public class MapRuteAdapter extends RecyclerView.Adapter<MapRuteAdapter.MyViewHolder>{

    Context context;
    JSONArray jsonArray;

GoogleMap googleMap;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, bio;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvRute);

        }

    }


    public MapRuteAdapter(JSONArray jsonArray, Context context, GoogleMap googleMap)
    {

        this.jsonArray=jsonArray;
        this.context=context;
        this.googleMap=googleMap;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rut, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        final ResultBean beanClass = result.get(position);
      try {
          final JSONObject ss=jsonArray.getJSONObject(position);
              holder.name.setText(ss.getString("name"));

              holder.name.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
try {
    double lat = Double.parseDouble(ss.getString("lat"));
    double lng = Double.parseDouble(ss.getString("lng"));
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 18));
}catch (Exception e)
{
    Log.e("Exception-->",e+"");
}
                  }
              });
      }catch (Exception e)
      {
          Log.d("Exception-->",e+"");

      }

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
