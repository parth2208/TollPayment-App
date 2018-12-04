package com.example.adityanarayan.tollpay.AddVehicle;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;

import java.util.List;

public class VehicleDetails extends AppCompatActivity {

    private static final String TAG = "VehicleDetails";

    TextView vehicle_name, vehicle_type,vehicle_no,paid_amount,timeStamp;

    public static VehicleDatabase mVehicleDatabase;
    List<Vehicle> vehicles;
    List<Transaction> transactions;
    ImageView back_btn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle_details);

        vehicle_name =findViewById(R.id.text_details_name);
        vehicle_no = findViewById(R.id.text_details_no);
        vehicle_type = findViewById(R.id.dynamic_type);
        paid_amount = findViewById(R.id.dynamic_amount);
        timeStamp = findViewById(R.id.dynamic_time);
        back_btn = findViewById(R.id.details_back_btn);


        String name = getIntent().getStringExtra("Vehicle_name");
        String number = getIntent().getStringExtra("Vehicle_no");
        String typ = getIntent().getStringExtra("type");
        String added_time = getIntent().getStringExtra("time");

        final Vehicle vehicle_table = new Vehicle();
        final Transaction transaction_table = new Transaction();
        mVehicleDatabase = Room.databaseBuilder(this,VehicleDatabase.class,"dbVehicle").allowMainThreadQueries().build();


        vehicle_name.setText(name);
        vehicle_no.setText(number);
        vehicle_type.setText(typ);
        timeStamp.setText(added_time);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VehicleDetails.this,NewVehicleActivity.class);
                startActivity(intent);
            }
        });





    }
}
