package com.example.user.trackingmapusingfrosquire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trackingmapusingfrosquire.Controller.DatabaseHandler;
import com.example.user.trackingmapusingfrosquire.R;

public class Login_Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        username=(TextView) findViewById(R.id.Username);
        password=(TextView)findViewById(R.id.Password);
    }


public void LoginFunction(View view)
{
    try {
        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
        String result = databaseHandler.login_check(username.getText().toString(), password.getText().toString());
        String[] resuls = result.split("&");
        Log.d("Result-->",result+"");

        String id = resuls[0];
        String login_Status = resuls[1];
        Log.d("Array..>",resuls[0]+"");
        if (login_Status.equals("Success")) {
            sharedPreferences = getSharedPreferences("Login_Id", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("User_Id", id);
            editor.commit();
            startActivity(new Intent(getApplicationContext(), UserHomeNav.class));
        } else {

            Toast.makeText(getApplicationContext(), "Login Faild", Toast.LENGTH_SHORT).show();

        }
    }catch(Exception e)
    {
        Log.d("Exception-->",e+"");}
}

    public void RegisterFunction(View view)
    {
        startActivity(new Intent(getApplicationContext(),RegisterUser.class));
    }
}
