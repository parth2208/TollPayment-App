package com.example.adityanarayan.tollpay;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.AddVehicle.NewVehicleActivity;
import com.example.adityanarayan.tollpay.AddVehicle.VehicleDetails;
import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;

import java.util.List;

public class PaymentDetails extends AppCompatActivity {

    private static final String TAG = "PaymentDetails";

    TextView pay_distance, pay_vehicle_type, pay_vehicle_no , paid_amount, timeStamp, pay_source, pay_destination,unique_id;

    public static VehicleDatabase mVehicleDatabase;
    List<Vehicle> vehicles;
    List<Transaction> transactions;
    ImageView back_btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_details);

        pay_destination = findViewById(R.id.text_pay_destination_name);
//        pay_distance = findViewById(R.id.dynamic_distance);
        pay_vehicle_no = findViewById(R.id.text_details_no);
        paid_amount = findViewById(R.id.pay_dynamic_amount);
        timeStamp = findViewById(R.id.payment_dynamic_time);
        pay_source = findViewById(R.id.dynamic_source);
//        pay_vehicle_type = findViewById(R.id.pay_dynamic_type);
        back_btn = findViewById(R.id.pay_details_back_btn);
//        unique_id = findViewById(R.id.payment_dynamic_id);

        String Destiation = getIntent().getStringExtra("destination");
        String source = getIntent().getStringExtra("source");
        String time = getIntent().getStringExtra("time");
//        int Distance = getIntent().getIntExtra("distance",0);
//        String type = getIntent().getStringExtra("Vehicle_type");
        String number = getIntent().getStringExtra("Vehicle_no");
        int amount = getIntent().getIntExtra("amount",0);
        Log.d(TAG, "PaymentDetails: Checking amount " + amount);
//        String transaction_id = getIntent().getStringExtra("id");


        mVehicleDatabase = Room.databaseBuilder(this,VehicleDatabase.class,"dbVehicle").allowMainThreadQueries().build();

        pay_source.setText(source);
        pay_vehicle_no.setText(number);
//        pay_vehicle_type.setText(type);
//        pay_distance.setText(Integer.toString(Distance));
        timeStamp.setText(time);
        pay_destination.setText(Destiation);
        paid_amount.setText(Integer.toString(amount));
//        unique_id.setText(transaction_id);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }
}
