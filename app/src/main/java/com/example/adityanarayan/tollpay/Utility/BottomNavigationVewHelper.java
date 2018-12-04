package com.example.adityanarayan.tollpay.Utility;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.adityanarayan.tollpay.AddVehicle.NewVehicleActivity;
import com.example.adityanarayan.tollpay.MainActivity;
import com.example.adityanarayan.tollpay.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationVewHelper {

    private static final String TAG = "BottomNavigationVewHelp";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "setupBottomNavigationView: Setting bottom helper");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }


    public static void enableNavigation(final Context context, BottomNavigationViewEx viewEx){

        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_home :

                        Intent intentHome = new Intent(context,MainActivity.class);
                        context.startActivity(intentHome);
                        break;

                    case R.id.ic_vehicle :

                        Intent intetVehicle = new Intent(context, NewVehicleActivity.class);
                        context.startActivity(intetVehicle);
                        break;

//                    case R.id.ic_transaction :
//                        Intent intentTransaction = new Intent(context, TransactionActivity.class);
//                        context.startActivity(intentTransaction);
//                        break;

                }

                return false;
            }
        });
    }

}
