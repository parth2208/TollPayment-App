package com.example.adityanarayan.tollpay;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.Utility.BottomNavigationVewHelper;
import com.example.adityanarayan.tollpay.Utility.TransactionAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";

    public static final int Activity_Num = 2;

    ListView transaction_list;
    List<Transaction> transactions;
    TransactionAdapter transactionAdapter;
    public static VehicleDatabase mVehicleDatabase;

    private Context mContext = TransactionActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        transaction_list = findViewById(R.id.transaction_listView);
        Transaction history = new Transaction();


        mVehicleDatabase = Room.databaseBuilder(this, VehicleDatabase.class, "dbContact").allowMainThreadQueries().build();


//        final List<Transaction> transactionHistory = mVehicleDatabase.transactionDao().gethistory();
//        transactionAdapter = new TransactionAdapter(transactionHistory,this);
//        transaction_list.setAdapter(transactionAdapter);


        setupBottomNavigationView();

    }


    /*
    BottomNavigation setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBarView);
        BottomNavigationVewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationVewHelper.enableNavigation(TransactionActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(Activity_Num);
        menuItem.setChecked(true);
    }
}
