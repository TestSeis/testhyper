package com.example.denni.ab_controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("URL eingeben");


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Url",m_Text);
                    editor.apply();
                new IntentIntegrator(mActivity).initiateScan();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();



         // `this` is the current Activity
        //Intent myIntent = new Intent(getApplicationContext(), UserActivity.class);
        //Intent myIntent = new Intent(getApplicationContext(), AdminUserActivity.class);
        //Intent myIntent = new Intent(getApplicationContext(), ControllerCarActivity.class);
        //startActivity(myIntent);
    }



    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String currentString = result.getContents();
                String text = "";
                byte[] en = Base64.decode(currentString, Base64.DEFAULT);
                try {
                    text = new String(en, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String[] separated = text.split(";");
                if(separated[1].contains("User")||separated[1].contains("Controller")||separated[1].contains("Admin")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Passwort eingeben");


                    final EditText input = new EditText(this);

                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String m_Text = input.getText().toString();
                            if (m_Text.equals(separated[2])) {

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Name",separated[0]);
                                editor.putString("Code",separated[3]);
                                editor.putString("Id",separated[4]);
                                editor.apply();

                                if(separated[1].contains("User") ){
                                    Intent myIntent = new Intent(getApplicationContext(), UserActivity.class);
                                    startActivity(myIntent);
                                }
                                if(separated[1].contains("Controller") ){
                                    Intent myIntent = new Intent(getApplicationContext(), ControllerCarActivity.class);
                                    startActivity(myIntent);
                                }
                                if(separated[1].contains("Admin") ){
                                    Intent myIntent = new Intent(getApplicationContext(), AdminUserActivity.class);
                                    startActivity(myIntent);
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();


                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
