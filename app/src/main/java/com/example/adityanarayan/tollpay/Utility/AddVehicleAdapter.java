package com.example.adityanarayan.tollpay.Utility;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;

import java.util.List;

public class AddVehicleAdapter extends BaseAdapter {

    private static final String TAG = "AddVehicleAdapter";

    List<Vehicle> vehicleList;
    Context context;
    VehicleDatabase mVehicleDatabase;

    public AddVehicleAdapter(List<Vehicle> vehicleList, Context context, VehicleDatabase mVehicleDatabase) {
        Log.d(TAG, "AddVehicleAdapter: Adapter has been created");
        this.vehicleList = vehicleList;
        this.context = context;
        this.mVehicleDatabase = mVehicleDatabase;
    }

    @Override
    public int getCount() {

        Log.d(TAG, "getCount: The no of items is " + vehicleList.size());

        return vehicleList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId: working here");
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        Log.d(TAG, "getView: Its woking here");
        Vehicle vehicles = vehicleList.get(position);

        View view = View.inflate(context, R.layout.vehicle_list_view,null);

        TextView textView_vehicle_name = view.findViewById(R.id.list_vehicle_name);
        TextView textView_vehicle_number = view.findViewById(R.id.list_vehicle_number);

        textView_vehicle_name.setText(vehicles.getVehicle_name());

        Log.d(TAG, "getView: its working fine for vehicle name " + vehicles.getVehicle_name());
        textView_vehicle_number.setText(vehicles.getVehicle_no());
        Log.d(TAG, "getView: its working fine for vehicle name " + vehicles.getVehicle_no());

        return view;
    }
}
