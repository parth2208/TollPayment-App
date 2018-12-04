package com.example.adityanarayan.tollpay.Utility;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;

public class DeleteDialog extends DialogFragment {

    private static final String TAG = "DeleteDialog";

    Button delete_btn, cancel_btn;

    VehicleDatabase mVehicleDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete_vehicle,container,false);

        delete_btn = view.findViewById(R.id.dialog_delete_btn);
        cancel_btn = view.findViewById(R.id.dialog_cancel_btn);

        mVehicleDatabase = Room.databaseBuilder(getActivity(),VehicleDatabase.class,"dbVehicle").allowMainThreadQueries().build();
        final Vehicle vehicle = new Vehicle();


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mVehicleDatabase.vehicleDao().delete(vehicle);

                getDialog().dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
