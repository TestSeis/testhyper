package com.example.denni.ab_controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class CarMenu extends AppCompatActivity {
    public TextView km, kennzeichen, fNr;
    public ProgressBar progressBar;
    String carId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);
        getData(getIntent().getExtras().getString("id"));
        km = (TextView) findViewById(R.id.c_km);
        kennzeichen = (TextView) findViewById(R.id.c_kennzeichen);
        fNr = (TextView) findViewById(R.id.c_fnr);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void getData(final String s){
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("car", "resource:org.example.biznet.Car#" + s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        System.out.println(preferences.getString("Url","")+"/api/CarForUser?access_token="+preferences.getString("Code",""));
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/CarForUser?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                        ObjectMapper m = new ObjectMapper();
                        try {
                            UserCar userCar = m.readValue(response.toString(), UserCar.class);
                            km.setText("KM: "+userCar.getKm());
                            kennzeichen.setText("Kennzeichen: "+userCar.getKennzeichen());
                            fNr.setText("F-Nr.: "+userCar.getFahrNR());
                            carId = s;
                            if (!userCar.getStatus()){
                                Intent myIntent = new Intent(getApplicationContext(), UserActivity.class);
                                startActivity(myIntent);
                            }else{
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                makeTransaction(carId, preferences.getString("Id", ""), userCar.getKm());
                            }
                            progressBar.setVisibility(View.INVISIBLE);

                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

// Add the request to the RequestQueue.
        queue.add(jsonObjRequest);
    }

    public void makeTransaction(String s, String u, String km){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("KM eingeben");

        final String[] m_Text = new String[1];
        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Text = input.getText().toString();
                m_Text[0] = Text;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("$class", "org.example.biznet.Trade");
            jsonObject.put("car", "resource:org.example.biznet.Car#" + s);
            jsonObject.put("newOwner", "resource:org.example.biznet.Trader#" + u);
            jsonObject.put("km", m_Text[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/CarForUser?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
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

    public void logout(View v){


        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("$class", "org.example.biznet.ChangeStatus");
            jsonObject.put("car", "resource:org.example.biznet.Car#" + carId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/ChangeStatus?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(getApplicationContext(), UserActivity.class);
                        startActivity(myIntent);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

// Add the request to the RequestQueue.
        queue.add(jsonObjRequest);
    }

    public void damage(View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("$class", "org.example.biznet.ChangeSchaden");
            jsonObject.put("car", "resource:org.example.biznet.Car#" + carId);
            jsonObject.put("schaden", "BeispielUrl");
            jsonObject.put("schadenHash", "a8razzfazfz211");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest
                (Request.Method.POST, preferences.getString("Url","")+"/api/ChangeSchaden?access_token="+preferences.getString("Code",""), jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

// Add the request to the RequestQueue.
        queue.add(jsonObjRequest);

    }
}
