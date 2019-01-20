package com.example.denni.ab_controller;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class UserCarAdapter extends RecyclerView.Adapter<UserCarAdapter.MyViewHolder>  {
    Context context;
    private List<UserCar> userCarList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView km, stat, kennzeichen, fNr;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            km = (TextView) view.findViewById(R.id.km);
            stat = (TextView) view.findViewById(R.id.stat);
            kennzeichen = (TextView) view.findViewById(R.id.kennzeichen);
            fNr = (TextView) view.findViewById(R.id.fNr);
        }
        @Override
        public void onClick(View v) {
            maps(userCarList.get(getAdapterPosition()).getPos());
        }

    }

    public void maps(String co){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+co);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        context.startActivity(mapIntent);
    }

    public UserCarAdapter(List<UserCar> userCarList) {
        this.userCarList = userCarList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usercar_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserCar car = userCarList.get(position);
        holder.kennzeichen.setText("Kennzeichen: "+ car.getKennzeichen());
        holder.stat.setText(car.getStatus().toString());
        holder.km.setText("KM: "+car.getKm());
        holder.fNr.setText("Fahr-Nr.: "+car.getFahrNR());
    }

    @Override
    public int getItemCount() {
        return userCarList.size();
    }


}