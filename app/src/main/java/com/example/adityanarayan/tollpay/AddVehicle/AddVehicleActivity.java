package com.example.adityanarayan.tollpay.AddVehicle;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;
import com.example.adityanarayan.tollpay.Utility.BottomNavigationVewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AddVehicleActivity extends AppCompatActivity {
    private static final String TAG = "AddVehicleActivity";

    private static final int Activity_Num = 1;

    private Context mContext = AddVehicleActivity.this;

    FloatingActionButton add_button;
//    AddVehicleAdapter vehicleAdapter;
   // ListView vehicle_listView;
   // List<Vehicle> vehicles;
    VehicleDatabase vehicleDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: AddVehicleActivity started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvehicle);
       // vehicle_listView = findViewById(R.id.addVehicle_listView);
        add_button = findViewById(R.id.add_fab);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddVehicleActivity.this, NewVehicleActivity.class);
                startActivity(intent);
            }
        });

        vehicleDatabase = Room.databaseBuilder(this, VehicleDatabase.class, "dbContact").allowMainThreadQueries().build();

//        String vehicle_type = getIntent().getStringExtra("type_passing");
//        //Setting up list view
//        List<Vehicle> vehicles = vehicleDatabase.vehicleDao().getVehicle();
//        vehicleAdapter = new AddVehicleAdapter(vehicles, this,vehicleDatabase);

       // vehicle_listView.setAdapter(vehicleAdapter);

//        vehicle_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                for (int i = 0; i <= position; i++) {
//
//                    switch (i) {
//                        case 0:
//                            Toast.makeText(AddVehicleActivity.this, "The clicked position is"+ position, Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(AddVehicleActivity.this, VehicleDetails.class);
//                            intent.putExtra("vehicleNumber", vehicles.get(position).getVehicle_no());
//                            intent.putExtra("VehicleName", vehicles.get(position).getVehicle_name());
//                            intent.putExtra("VehicleType", vehicles.get(position).getVehicle_type());
//                            intent.putExtra("timeStamp", vehicles.get(position).getTimeStamp());
//                    }
//
//                }
//            }
//        });






//        setupBottomNavigationView();
    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: The activity is just resumed");
        super.onResume();

//        vehicle_listView = findViewById(R.id.addVehicle_listView);
//
//        Log.d(TAG, "onResume: Checking List View Again");
//
//        //Setting up list view
//        final List<Vehicle> vehicles = vehicleDatabase.vehicleDao().getVehicle();
//        vehicleAdapter = new AddVehicleAdapter(vehicles, this,vehicleDatabase);
//        vehicle_listView.setAdapter(vehicleAdapter);
    }


    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: The activity is just Paused");
        super.onPause();

    }


    /*
    BottomNavigationBar
   */

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBarView);
        BottomNavigationVewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationVewHelper.enableNavigation(AddVehicleActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(Activity_Num);
        menuItem.setChecked(true);
    }

}
