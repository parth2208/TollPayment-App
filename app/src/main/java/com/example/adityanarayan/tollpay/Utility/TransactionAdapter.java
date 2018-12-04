package com.example.adityanarayan.tollpay.Utility;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.PaymentDetails;
import com.example.adityanarayan.tollpay.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends BaseAdapter {

    private static final String TAG = "TransactionAdapter";

    List<Transaction> transactionList ;
    Context context;
    VehicleDatabase mVehicleDatabase;

    public TransactionAdapter(List<Transaction> transactionList, Context context, VehicleDatabase mVehicleDatabase) {
        Log.d(TAG, "TransactionAdapter: Adapters constructor has been created");
        this.transactionList = transactionList;
        this.context = context;
        this.mVehicleDatabase = mVehicleDatabase;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: The no of items is " + transactionList.size());
        return transactionList.size();
    }

    @Override
    public Object getItem(int position) {

        Log.d(TAG, "getItem: Its woking here");
        return transactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId: Its getting item id also");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView: This method is functioning fluently");

        Transaction transaction = transactionList.get(position);

        View view = View.inflate(context, R.layout.transaction_list_view,null);
        TextView textView_vehicle_no = view.findViewById(R.id.list_transaction_number);
        TextView textView_payment_amount = view.findViewById(R.id.payment_amount);

        textView_vehicle_no.setText(transaction.getVehicle_no());
        Log.d(TAG, "getView: It is working fine for Vehicle No. -"+ transaction.getVehicle_no());
        textView_payment_amount.setText(Integer.toString(transaction.getAmount()));
        Log.d(TAG, "getView: It is working fine for Amount Paid -" + transaction.getAmount());

        return view;
    }
}