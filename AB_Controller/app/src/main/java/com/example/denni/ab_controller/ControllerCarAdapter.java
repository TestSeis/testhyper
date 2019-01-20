package com.example.denni.ab_controller;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ControllerCarAdapter extends RecyclerView.Adapter<ControllerCarAdapter.MyViewHolder>  {
    Context context;
    private List<ControllerCar> controllerCarList;

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
            Toast.makeText(context, "In Zukunft soll das zu den Schäden führen", Toast.LENGTH_LONG).show();

        }

    }



    public ControllerCarAdapter(List<ControllerCar> controllerCarList) {
        this.controllerCarList = controllerCarList;
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
        ControllerCar car = controllerCarList.get(position);
        holder.kennzeichen.setText("Kennzeichen: "+car.getKennzeichen());
        holder.km.setText("KM: "+car.getKm());
        holder.fNr.setText("Fahr-Nr.: "+car.getFahrNR());
    }

    @Override
    public int getItemCount() {
        return controllerCarList.size();
    }


}