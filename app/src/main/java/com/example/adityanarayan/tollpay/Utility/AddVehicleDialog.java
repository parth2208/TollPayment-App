package com.example.adityanarayan.tollpay.Utility;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;

import java.text.DateFormat;
import java.util.Date;

public class AddVehicleDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddVehicleDialog";

    TextInputEditText vehicle_no, vehicle_name;
    AppCompatSpinner type_spinner;
    AppCompatImageButton submit_btn;
    Context context;

    VehicleDatabase mVehicleDatabase;
    TextView dismiss_btn;
    String vehicle_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_add_vehicle,container,false);


        vehicle_no = view.findViewById(R.id.dialog_editText_vehicle_no);
        vehicle_name = view.findViewById(R.id.dialog_editText_vehicle_name);
        submit_btn = view.findViewById(R.id.dialog_submit_vehicle_btn);

        dismiss_btn = view.findViewById(R.id.dismiss_dialog);
        dismiss_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        type_spinner = view.findViewById(R.id.dialog_typ_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.vehicle_type,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(arrayAdapter);
        type_spinner.setOnItemSelectedListener(this);


        mVehicleDatabase = Room.databaseBuilder(getActivity(),VehicleDatabase.class,"dbVehicle").allowMainThreadQueries().build();



        addVehicle();

        return view;
    }




    private boolean checkinputs(String vehicle_no, String vehicle_name) {
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if (vehicle_no.equals("") || vehicle_name.equals("") ) {

            Toast.makeText(getActivity(), "Please fill all the field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void addVehicle(){
        Log.d(TAG, "addVehicle: On clicking submit button to add vehicle");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = vehicle_no.getText().toString();
                String name = vehicle_name.getText().toString();


                String currentTime = DateFormat.getDateTimeInstance().format(new Date());

                if (checkinputs(number,name)) {
                    //Instantiating database table
                    final Vehicle vehicle = new Vehicle();

                    //Storing number and name of vehicle
                    vehicle.setVehicle_name(name);
                    vehicle.setVehicle_no(number);
                    vehicle.setTimeStamp(currentTime);
                    vehicle.setVehicle_type(vehicle_type);

                    mVehicleDatabase.vehicleDao().newVehicle(vehicle);

                    Log.d(TAG, "onClick: The stored vehicle no is " + vehicle.getVehicle_no());
                    Log.d(TAG, "onClick: The stored vehicle name is " + vehicle.getVehicle_name());
                    Log.d(TAG, "onClick: The stored time stamp is " + vehicle.getTimeStamp());

                    Toast.makeText(getActivity(), "Vehicle Added", Toast.LENGTH_SHORT).show();
                    vehicle_no.setText("");
                    vehicle_name.setText("");

                    getDialog().dismiss();

                }



            }
        });



    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, "onItemSelected: On selecting vehicle type");

        vehicle_type = parent.getItemAtPosition(position).toString();

        //Instantiating database table
        final Vehicle vehicle = new Vehicle();

        //storing data into table
        if (position ==0){

        }else {
            vehicle.setVehicle_type(vehicle_type);
            Log.d(TAG, "onItemSelected: Stored data is " + vehicle.getVehicle_type());
            Toast.makeText(getActivity(), vehicle_type, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
