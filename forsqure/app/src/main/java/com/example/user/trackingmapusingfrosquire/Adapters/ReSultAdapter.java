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

public class ReSultAdapter extends RecyclerView.Adapter<ReSultAdapter.MyViewHolder>{

    Context context;
    JSONArray jsonArray;
        List address;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, bio;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            bio = (TextView) view.findViewById(R.id.About);

            imageView = (ImageView) view.findViewById(R.id.ImageViewww);

        }

    }


    public ReSultAdapter(JSONArray jsonArray, Context context)
    {

        this.jsonArray=jsonArray;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        final ResultBean beanClass = result.get(position);
      try {
          JSONObject ss=jsonArray.getJSONObject(position);
          JSONArray categories = ss.getJSONArray("categories");
            address=new ArrayList();
          for(int i=0;i<categories.length();i++)
          {JSONObject location=ss.getJSONObject("location");
//              JSONObject cat=categories.getJSONObject(i);
//              JSONObject icon=cat.getJSONObject("icon");
              JSONArray formattedadd=location.getJSONArray("formattedAddress");


                  Log.d("Values-->",formattedadd+"");

                    String address=formattedadd.toString();
                    String tem=address.replace("[","");
                    String newadd=tem.replace("]","");

                    holder.bio.setText(newadd);

              holder.name.setText(ss.getString("name"));


          }



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
