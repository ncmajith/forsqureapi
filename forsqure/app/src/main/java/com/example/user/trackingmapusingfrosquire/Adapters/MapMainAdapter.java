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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28-12-2017.
 */

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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rut, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {

          holder.name.setText( jsonArray.get(position).toString());





      }catch (Exception e)
      {
          Log.d("Exception-->",e+"");

      }

    }

    @Override
    public int getItemCount() {
        return jsonArray.size();
    }
}
