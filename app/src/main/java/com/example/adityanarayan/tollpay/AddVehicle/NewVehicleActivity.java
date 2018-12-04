package com.example.adityanarayan.tollpay.AddVehicle;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.Database.Vehicle;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.R;
import com.example.adityanarayan.tollpay.Utility.AddVehicleAdapter;
import com.example.adityanarayan.tollpay.Utility.AddVehicleDialog;
import com.example.adityanarayan.tollpay.Utility.BottomNavigationVewHelper;
import com.example.adityanarayan.tollpay.Utility.DeleteDialog;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

public class NewVehicleActivity extends AppCompatActivity {

    private static final String TAG = "NewVehicleActivity";

    AppCompatSpinner spinner;
    AppCompatImageButton vehicle_submit_btn;
    TextInputEditText vehicle_no, vehicle_name;
    ImageView arrowBack;
    FloatingActionButton fab_btn;

    public static final int Activity_Num = 1;


    String vehicle_type;
    VehicleDatabase mVehicleDatabase;
    AddVehicleAdapter addVehicleAdapter;
    ListView vehicle_list;
    List<Vehicle> vehicles;


    TextView info ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: NewVehicleActivityStarted ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle);

//        vehicle_name = findViewById(R.id.editText_vehicle_name);
//        vehicle_no = findViewById(R.id.editText_vehicle_no);
//        vehicle_submit_btn = findViewById(R.id.submit_vehicle_btn);
//        arrowBack = findViewById(R.id.back_btn);


        mVehicleDatabase = Room.databaseBuilder(this,VehicleDatabase.class,"dbVehicle").allowMainThreadQueries().build();

        fab_btn = findViewById(R.id.add_fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                AddVehicleDialog dialog = new AddVehicleDialog();
                dialog.show(fragmentManager,"AddVehicle");
                dialog.setCancelable(false);

            }
        });

//        spinner = findViewById(R.id.typ_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_type,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


//        arrowBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(NewVehicleActivity.this,AddVehicleActivity.class);
//                intent.putExtra("type_passing",vehicle_type);
//                startActivity(intent);
//                finish();
//
//            }
//        });

//        addVehicle();




        //////////////////For Debugging Text///////////////////////////////////////
        String vehicle_type = getIntent().getStringExtra("type_passing");
        //Setting up list view
        vehicle_list = findViewById(R.id.vehicle_listview);
        final List<Vehicle> vehicles = mVehicleDatabase.vehicleDao().getVehicle();
        addVehicleAdapter = new AddVehicleAdapter(vehicles, this,mVehicleDatabase);

        vehicle_list.setAdapter(addVehicleAdapter);

        vehicle_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i <= position; i++) {

                    switch (i){
                        case 0:
                            //Toast.makeText(MainActivity.this, "The clicked position is"+ position, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(NewVehicleActivity.this,VehicleDetails.class);
                            intent.putExtra("Vehicle_no",vehicles.get(position).getVehicle_no());
                            intent.putExtra("Vehicle_name",vehicles.get(position).getVehicle_name());
                            intent.putExtra("type",vehicles.get(position).getVehicle_type());
                            intent.putExtra("time",vehicles.get(position).getTimeStamp());


//                                    Intent intent1 = new Intent(MainActivity.this,HistoryActivity.class);
//                                    intent1.putExtra("history_mobile",contacts.get(position).getMobile());
//                                    Log.d(TAG, "onItemClick: The transfer data"+ intent1);
                            startActivity(intent);
                    }
                }
            }
        });


        vehicle_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                DeleteDialog dialog = new DeleteDialog();
                dialog.show(fragmentManager,"AddVehicle");
                dialog.setCancelable(false);

                return true;
            }
        });

        setupBottomNavigationView();



//        List<Vehicle> vehicles = mVehicleDatabase.vehicleDao().getVehicle();
//
//        info = findViewById(R.id.text_db_info);
//
//        String text ="";
//
//        for (Vehicle v : vehicles) {
//
//            String vehicleNbr = v.getVehicle_no();
//            String vehicleName = v.getVehicle_name();
//            String vehicleType = v.getVehicle_type();
//            String dateStamp = v.getTimeStamp();
//
//            text = text + "\n\n" + "Vehicle Number : " + vehicleNbr + "\n Vehicle Name : " + vehicleName + "\n Vehicle Type : " + vehicleType + "\n Time : " + dateStamp;
//
//        }
//
//        info.setText(text);

//////////////////////////////////////////////////////////////////////////////////////
    }




    //    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Log.d(TAG, "onItemSelected: On selecting vehicle type");
//
//        vehicle_type = parent.getItemAtPosition(position).toString();
//
//        //Instantiating database table
//        final Vehicle vehicle = new Vehicle();
//
//        //storing data into table
//        if (position ==0){
//
//        }else {
//            vehicle.setVehicle_type(vehicle_type);
//            Log.d(TAG, "onItemSelected: Stored data is " + vehicle.getVehicle_type());
//            Toast.makeText(this, vehicle_type, Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        Log.d(TAG, "onNothingSelected: In default condition");
//
//    }

//    private void addVehicle(){
//        Log.d(TAG, "addVehicle: On clicking submit button to add vehicle");
//
//        vehicle_submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String number = vehicle_no.getText().toString();
//                String name = vehicle_name.getText().toString();
//
//
//                String currentTime = DateFormat.getDateTimeInstance().format(new Date());
//
//
//                //Instantiating database table
//                final Vehicle vehicle = new Vehicle();
//
//                //Storing number and name of vehicle
//                vehicle.setVehicle_name(name);
//                vehicle.setVehicle_no(number);
//                vehicle.setTimeStamp(currentTime);
//                vehicle.setVehicle_type(vehicle_type);
//
//                mVehicleDatabase.vehicleDao().newVehicle(vehicle);
//
//                Log.d(TAG, "onClick: The stored vehicle no is "+ vehicle.getVehicle_no());
//                Log.d(TAG, "onClick: The stored vehicle name is "+ vehicle.getVehicle_name());
//                Log.d(TAG, "onClick: The stored time stamp is "+ vehicle.getTimeStamp());
//
//                Toast.makeText(NewVehicleActivity.this, "Vehicle Added", Toast.LENGTH_SHORT).show();
//                vehicle_no.setText("");
//                vehicle_name.setText("");
//
//            }
//        });
//
//    }



      /*
    BottomNavigationBar Setup
     */

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBarView);
        BottomNavigationVewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationVewHelper.enableNavigation(NewVehicleActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(Activity_Num);
        menuItem.setChecked(true);

    }

}
