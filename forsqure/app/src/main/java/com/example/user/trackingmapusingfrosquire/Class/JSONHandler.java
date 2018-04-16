package com.example.user.trackingmapusingfrosquire.Class;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHandler {
    JSONObject resfood;
    JSONObject resfun;
JSONObject fun;
JSONObject food;
double clat,clog;
    int Cafe=0,Breakfast=0,Lunch=0,Bar=0,Pub=0,Restaurant=0,HillStation=0,Beach=0,Church=0,Temple=0,Mosque=0,Museum=0,Park=0,Theater=0,Zoo=0;
JSONObject cat;
    public  JSONHandler(JSONObject fun,JSONObject food)
    {
      try {

          this.resfood = food.getJSONObject("response");
          this.resfun=fun.getJSONObject("response");
          Log.d("funres-->",resfood+"");
          Log.d("foodres-->",resfun+"");
      }catch (Exception e)

      {

          Log.e("Exception in json-->",e+"");

      }

    }

    public  JSONHandler(JSONObject cat)
    {
        try {

           this.cat=cat.getJSONObject("response");
        }catch (Exception e)

        {

            Log.e("Exception in json-->",e+"");

        }

    }

public  JSONArray getOut()
{ JSONArray arrayFinal=new JSONArray();
    try {


        JSONArray venueArray=cat.getJSONArray("venues");

        for(int i=0;i<venueArray.length();i++)
        {   JSONObject finalout=new JSONObject();
            JSONObject location=venueArray.getJSONObject(i);
            JSONObject loc=location.getJSONObject("location");
            JSONArray arrrr=location.getJSONArray("categories");

            finalout.put("lat", loc.getString("lat"));
             finalout.put("lng", loc.getString("lng"));
             finalout.put("name", location.getString("name"));
            finalout.put("id", arrrr.getJSONObject(0).getString("id"));
             arrayFinal.put(finalout);




        }
Log.e("Final Out-->",arrayFinal.toString());

    }catch (Exception e)
    {

        Log.e("Exception-->",e+"");

    }
    return arrayFinal;
}


    public  JSONHandler(JSONObject fun,JSONObject food,double clat,double clog)
    {
        try {

            this.resfood = food.getJSONObject("response");
            this.resfun=fun.getJSONObject("response");
            this.clat=clat;
            this.clog=clog;

            Log.d("funres-->",resfood+"");
            Log.d("foodres-->",resfun+"");
        }catch (Exception e)

        {

            Log.e("Exception in json-->",e+"");

        }

    }


    public JSONArray Result()
    {


        JSONArray result=new JSONArray();
        JSONObject finafun=new JSONObject();
        JSONObject finalFood=new JSONObject();
        try{

            JSONArray groupsFood=resfood.getJSONArray("groups");


            JSONArray groupsFun=resfun.getJSONArray("groups");

            Log.e("grp---food>",groupsFood+"");
            Log.e("grp---fun>",groupsFun+"");

            for(int j = 0; j <groupsFood.length(); j++)
            {

                JSONObject itemFood=groupsFood.getJSONObject(j);
                JSONObject itemFun=groupsFun.getJSONObject(j);

                JSONArray fooditemArray=itemFood.getJSONArray("items");
                Log.e("items---food>",fooditemArray+"");


                JSONArray funitemArray=itemFun.getJSONArray("items");
                Log.e("items---fun>",funitemArray+"");

                for(int x = 0; x <funitemArray.length(); x++)
                {
                    JSONObject obfun=funitemArray.getJSONObject(x);
                    JSONObject obfood=fooditemArray.getJSONObject(x);


                    JSONObject venuessFood=obfood.getJSONObject("venue");
                    JSONObject venuessFun=obfun.getJSONObject("venue");
                    Log.e("venue---food>",venuessFood+"");
                    Log.e("venue---fun>",venuessFun+"");

                    JSONObject locationFood=venuessFood.getJSONObject("location");
                    JSONObject locationFun=venuessFun.getJSONObject("location");
                    Log.e("location---food>",locationFood+"");
                    Log.e("location---fun>",locationFun+"");



                    JSONArray catFood=venuessFood.getJSONArray("categories");
                    JSONArray catFun=venuessFun.getJSONArray("categories");
                    Log.e("cat---food>",catFood+"");
                    Log.e("cat---fun>",catFun+"");

                    finafun.put("name",venuessFun.getString("name"));
                    finalFood.put("name",venuessFood.getString("name"));

                    finafun.put("ratings",venuessFun.getString("rating"));
                    finalFood.put("ratings",venuessFood.getString("rating"));

                    finafun.put("distance",locationFun.getString("distance"));
                    finalFood.put("distance",locationFood.getString("distance"));

                    finafun.put("lat",locationFun.getString("lat"));
                    finalFood.put("lat",locationFood.getString("lat"));

                    finafun.put("lng",locationFun.getString("lng"));
                    finalFood.put("lng",locationFood.getString("lng"));

                    finafun.put("address",locationFun.getString("address"));
                    finalFood.put("address",locationFood.getString("address"));

                    for(int y=0;y<catFood.length();y++)
                    {
                        JSONObject catobjfood=catFood.getJSONObject(y);
                        JSONObject catobjfun=catFun.getJSONObject(y);

                        finafun.put("categories",catobjfood.getString("name"));
                        finalFood.put("categories",catobjfun.getString("name"));
                    }


                    result.put(finafun);
                    result.put(finalFood);
                    Log.wtf("values-->",venuessFood.getString("name"));
                }


            }

        }catch (Exception e)
        {

            Log.e("JSON-->",e+"");

        }
        return result;
    }




    public JSONArray Food()
    {
        JSONArray result=new JSONArray();

        try {

            JSONArray groupsobj=resfood.getJSONArray("groups");
            Log.d("Groups-->",groupsobj+"");
            for(int j = 0; j <groupsobj.length(); j++)
            {
                JSONObject itm=groupsobj.getJSONObject(j);
                Log.d("values of item-->",itm+"");
                JSONArray it=itm.getJSONArray("items");

                for(int x = 0; x <it.length(); x++)
                {
                    JSONObject ob=it.getJSONObject(x);
                    JSONObject venuess=ob.getJSONObject("venue");
                    JSONObject location=venuess.getJSONObject("location");
                    JSONObject finalob=new JSONObject();

                    JSONArray cat=venuess.getJSONArray("categories");
                    finalob.put("name",venuess.getString("name"));
                    finalob.put("ratings",venuess.getString("rating"));

                    finalob.put("distance",location.getString("distance"));
                    finalob.put("lat",location.getString("lat"));
                    finalob.put("lng",location.getString("lng"));
                    finalob.put("address",location.getString("address"));

                    for(int y=0;y<cat.length();y++)
                    {
                        JSONObject catobj=cat.getJSONObject(y);
                        finalob.put("categories",catobj.getString("name"));
                    }


                    result.put(finalob);
                    Log.wtf("values-->",venuess.getString("name"));
                }


            }
        } catch (Exception e) {

            Log.e("Exception-->",e+"");
        }
        return result;
    }

    public JSONArray Hotel()
    {
        JSONArray hotel=new JSONArray();

        try {

            JSONArray groupsobj=resfun.getJSONArray("groups");
            Log.d("Groups-->",groupsobj+"");
            for(int j = 0; j <groupsobj.length(); j++)
            {
                JSONObject itm=groupsobj.getJSONObject(j);
                Log.d("values of item-->",itm+"");
                JSONArray it=itm.getJSONArray("items");

                for(int x = 0; x <it.length(); x++)
                {
                    JSONObject ob=it.getJSONObject(x);
                    JSONObject venuess=ob.getJSONObject("venue");
                    JSONObject location=venuess.getJSONObject("location");
                    JSONObject finalob=new JSONObject();

                    JSONArray cat=venuess.getJSONArray("categories");
                    finalob.put("name",venuess.getString("name"));
                    finalob.put("ratings",venuess.getString("rating"));

                    finalob.put("distance",location.getString("distance"));
                    finalob.put("lat",location.getString("lat"));
                    finalob.put("lng",location.getString("lng"));
                    finalob.put("address",location.getString("address"));

                    for(int y=0;y<cat.length();y++)
                    {
                        JSONObject catobj=cat.getJSONObject(y);
                        finalob.put("categories",catobj.getString("name"));
                    }


                    hotel.put(finalob);
                    Log.wtf("values-->",venuess.getString("name"));
                }


            }
        } catch (Exception e) {

            Log.e("Exception-->",e+"");
        }
        return hotel;
    }


}
