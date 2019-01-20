package com.example.denni.ab_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

    }

    public void createUser(View v)
    {
        Intent myIntent = new Intent(getApplicationContext(), CreateUserActivity.class);
        startActivity(myIntent);


    }

    public void createCar(View v)
    {

        Intent myIntent = new Intent(getApplicationContext(), CreateCarActivity.class);
        startActivity(myIntent);
    }
}
