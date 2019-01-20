package com.example.denni.ab_controller;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserCarActivity extends AppCompatActivity {
    private List<UserCar> carList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserCarAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_car);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        mAdapter = new UserCarAdapter(carList);
        mAdapter.setContext(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getCar(1);
        prepareData();
    }

    private void prepareData() {

        mAdapter.notifyDataSetChanged();
    }


    public void getCar (int j){
        int i = j;
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("car", "resource:org.example.biznet.Car#" + i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject.toString());
        final int i2 = j+1;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/CarForUser?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        ObjectMapper m = new ObjectMapper();
                        try {
                            UserCar userCar = m.readValue(response.toString(), UserCar.class);
                            System.out.println("FahNR: " + userCar.getFahrNR());
                            carList.add(userCar);
                            mAdapter.notifyDataSetChanged();
                            getCar(i2);

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                               //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

// Add the request to the RequestQueue.
        queue.add(jsonObjRequest);



    }
}
