package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.trackingmapusingfrosquire.Activity.Workouts.VerticalPagerAdapter;
import com.example.user.trackingmapusingfrosquire.Fragment.ForOneLocation;
import com.example.user.trackingmapusingfrosquire.Fragment.ForsquireSearch;

import com.example.user.trackingmapusingfrosquire.Fragment.TestingFragment;
import com.example.user.trackingmapusingfrosquire.Fragment.TravelFragment;
import com.example.user.trackingmapusingfrosquire.Fragment.UserMainFragment;
import com.example.user.trackingmapusingfrosquire.R;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserHomeNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    TextView placeName;
    LatLng latLng;
    private SliderLayout mDemoSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);




        HashMap<String, String> url_maps = new HashMap<String, String>();
        ArrayList<String> listUrl = new ArrayList<String>();
        ArrayList<String> listName = new ArrayList<String>();

        listUrl.add("https://images.pexels.com/photos/338515/pexels-photo-338515.jpeg?auto=compress&cs=tinysrgb&h=350");
        listName.add("Effel tower");

        listUrl.add("https://d3hne3c382ip58.cloudfront.net/files/uploads/bookmundi/resized/cmsfeatured/historic-sanctuary-of-machu-picchu-1500451311-1000X561.jpg");
        listName.add("Machu Picchu");



        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(listName.get(i))
                    .image(listUrl.get(i))

                    .setOnSliderClickListener(this);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

//        final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
//                (VerticalInfiniteCycleViewPager) findViewById(R.id.hicvp);
//        verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(getApplicationContext()));
//
//        verticalInfiniteCycleViewPager.setScrollDuration(1000);
//        verticalInfiniteCycleViewPager.startAutoScroll(true);




        placeName=(TextView)findViewById(R.id.edPlace);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.screen_area, new UserMainFragment());
        tx.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public  void onSelectFragment(View view){
        try {
            EditText times = (EditText) findViewById(R.id.edTime);
            EditText endTime = (EditText) findViewById(R.id.endTime);
            double stime = Double.parseDouble(times.getText().toString());
            double Etime = Double.parseDouble(endTime.getText().toString());




//            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//            Date st = format.parse(stime + ":" + "00");
//            Date et = format.parse(Etime + ":" + "00");
            double diff = Etime-stime;
//            long diffMinutes = diff / 3600;


            Log.d("SimpleDateFormat ---->", stime + "..>" + Etime);
//            Log.d("Times ---->", st.getTime() + "..>" + et.getTime());
            Log.d("Diffrences ---->", diff + "");

            if (diff > 2) {

                SharedPreferences sharedPreferences=getSharedPreferences("Time", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("time",diff+"");
                editor.commit();

//                startActivity(new Intent(getApplicationContext(),PlottingMap.class));

                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.screen_area, new TestingFragment());
                tx.commit();

            } else {
                Toast.makeText(getApplicationContext(), "Please select at least 2 hours diffrence", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Log.e("Error..>",e+"");
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            startActivity(new Intent(getApplicationContext(),Login_Activity.class));
        }
        if (id == R.id.action_Update) {

            startActivity(new Intent(getApplicationContext(),UpdateUser.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment=null;
        int id = item.getItemId();
//
        if (id == R.id.nav_camera) {

            fragment=new UserMainFragment();


        } else
            if (id == R.id.nav_gallery) {
                fragment=new ForOneLocation();




        } else if (id == R.id.nav_forsqure) {

            fragment=new ForsquireSearch();

        }
        else if(id==R.id.nav_savedrute)
            {
startActivity(new Intent(getApplicationContext(),MapActivityForSaved.class));
            }

        if(fragment!=null)
        {
            FragmentManager fman=getSupportFragmentManager();
            FragmentTransaction ft=fman.beginTransaction();
            ft.replace(R.id.screen_area,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
