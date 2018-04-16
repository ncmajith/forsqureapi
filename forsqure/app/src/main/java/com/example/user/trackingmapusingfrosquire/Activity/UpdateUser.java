package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.Activity.Workouts.MultiSelectionSpinner;
import com.example.user.trackingmapusingfrosquire.Controller.DatabaseHandler;
import com.example.user.trackingmapusingfrosquire.Model.Tb_User;
import com.example.user.trackingmapusingfrosquire.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateUser extends AppCompatActivity {

    EditText name,phone,email,username,password;

    List st;
    List place;
    List Holisites;
    List TopSpots;

    DatabaseHandler db;
    String userid;
    MultiSelectionSpinner spinner,placesp,holysp,toppic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);




       SharedPreferences sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("User_Id", null);



        db=new DatabaseHandler(this);
        name=(EditText)findViewById(R.id.userName);
        phone=(EditText)findViewById(R.id.userPhone);
        email=(EditText)findViewById(R.id.userEmail);
        username=(EditText)findViewById(R.id.userUserName);
        password=(EditText)findViewById(R.id.userPassword);

        spinner = (MultiSelectionSpinner)findViewById(R.id.mySpinner1);
        placesp = (MultiSelectionSpinner)findViewById(R.id.placep);
        holysp = (MultiSelectionSpinner)findViewById(R.id.holy);
        toppic = (MultiSelectionSpinner)findViewById(R.id.topspots);

        st=new ArrayList<>();
        st.add("Cafe");
        st.add("Breakfast");
        st.add("Lunch");
        st.add("Bar");
        st.add("Pub");
        st.add("Restaurant");
        spinner.setItems(st);


        place=new ArrayList<>();
        place.add("HillStation");
        place.add("Beach");
        place.add("Mountain");

        placesp.setItems(place);

        Holisites=new ArrayList<>();
        Holisites.add("Church");
        Holisites.add("Temple");
        Holisites.add("Mosque");
        holysp.setItems(Holisites);

        TopSpots=new ArrayList();
        TopSpots.add("Museum");
        TopSpots.add("Park");
        TopSpots.add("Theater");
        TopSpots.add("Zoo");
        toppic.setItems(TopSpots);



    }

    public void ViewRegisterd(View view)
    {

        startActivity(new Intent(getApplicationContext(),Login_Activity.class));

    }


    public void foodCheckBox(View view)
    {




    }

    public void registerUser(View view)
    {
        String food=st.toString();
        String plce=place.toString();
        String temp=food.replace("[","");
        String finalFood=temp.replace("]","");
        String tempp=plce.replace("[","");
        String finalPlace=tempp.replace("]","");
        Log.d("Food",finalFood);
        Log.d("Place",finalPlace);


        Log.e("Selected food",spinner.getSelectedItemsAsString());


        String place=placesp.getSelectedItemsAsString();
        String Food=spinner.getSelectedItemsAsString();
        String holi=holysp.getSelectedItemsAsString();
        String topspot=toppic.getSelectedItemsAsString();

        Log.e("Selected Holi",holi);
        Log.e("Selected Top",topspot);

       String replay=db.updateUser(new Tb_User(name.getText().toString(),email.getText().toString(),phone.getText().toString(),username.getText().toString(),password.getText().toString(),Food,place,holi,topspot),userid);
        Toast.makeText(getApplicationContext(),replay+"",Toast.LENGTH_SHORT).show();
    }
}
