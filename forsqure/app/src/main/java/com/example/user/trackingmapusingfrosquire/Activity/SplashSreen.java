package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.trackingmapusingfrosquire.R;

public class SplashSreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        startActivity(new Intent(getApplicationContext(),Login_Activity.class));
    }
},4000);
    }
}
