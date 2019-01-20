package com.example.denni.ab_controller;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class CreateCarActivity extends AppCompatActivity {
    EditText fahrnr, kennzeichen, km, pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        km = (EditText) findViewById(R.id.create_kennzeichen);
        kennzeichen = (EditText) findViewById(R.id.create_kennzeichen);
        fahrnr = (EditText) findViewById(R.id.create_fnr);
        pos = (EditText) findViewById(R.id.create_pos);

    }

    public void safe(View v)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("$class", "org.example.biznet.Car");
            jsonObject.put("fahrNR", fahrnr.getText().toString());
            jsonObject.put("kennzeichen", kennzeichen.getText().toString());
            jsonObject.put("km", km.getText().toString());
            jsonObject.put("pos", pos.getText().toString());
            jsonObject.put("schaden", "0");
            jsonObject.put("schadenHash", "0");
            jsonObject.put("status", "true");
            jsonObject.put("owner", "resource:org.example.biznet.Trader#1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/Car?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


        queue.add(jsonObjRequest);

    }
}
