package com.example.adityanarayan.tollpay;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityanarayan.tollpay.AddVehicle.VehicleDetails;
import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.Utility.TransactionAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "HomeFragment";

    AppCompatSpinner home_spinner;
    AppCompatImageButton payment_submit_btn;
    TextInputEditText home_vehicle_no, home_source, home_destination,home_distance, home_amount;


    String home_vehicle_type;

    VehicleDatabase mVehicleDatabase;
    List<Transaction> transactions;
    TransactionAdapter transactionAdapter;
    Context context;


    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;


    TextView info ;
    TextView transaction_details;
    ListView transaction_list;
    ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);


        // Initializing Widget
//        scrollView = view.findViewById(R.id.scroll_view);
        transaction_details = view.findViewById(R.id.text_transactions);
        transaction_list = view.findViewById(R.id.transaction_listView);
        home_spinner= view.findViewById(R.id.home_typ_spinner);
        payment_submit_btn = view.findViewById(R.id.submit_payment_btn);
        home_vehicle_no = view.findViewById(R.id.editText_vehicle_no);
        home_source = view.findViewById(R.id.editText_home_source);
        home_destination = view.findViewById(R.id.editText_home_destination);
        home_distance = view.findViewById(R.id.editText_home_distance);
        home_amount = view.findViewById(R.id.editText_home_amount);


        mVehicleDatabase = Room.databaseBuilder(getActivity(),VehicleDatabase.class,"dbVehicle").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        final List<Transaction> transactionHistory = mVehicleDatabase.transactionDao().gethistory();
        transactionAdapter = new TransactionAdapter(transactionHistory,getActivity(),mVehicleDatabase);
        transaction_list.setAdapter(transactionAdapter);


        transaction_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i <= position; i++) {

                    switch (i){
                        case 0:
                            //Toast.makeText(MainActivity.this, "The clicked position is"+ position, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(),PaymentDetails.class);
                            intent.putExtra("Vehicle_no",transactionHistory.get(position).getVehicle_no());
                            intent.putExtra("Vehicle_type",transactionHistory.get(position).getVehicle_type());
                            intent.putExtra("distance",transactionHistory.get(position).getDistance());
                            intent.putExtra("source",transactionHistory.get(position).getSource());
                            intent.putExtra("destination",transactionHistory.get(position).getDestination());
                            intent.putExtra("time",transactionHistory.get(position).getTimeStamp());
                            intent.putExtra("amount",transactionHistory.get(position).getAmount());

                            Log.d(TAG, "onItemClick: Database Check for amount"+ transactionHistory.get(position).getAmount());
                            Log.d(TAG, "onItemClick: Database Check for amount"+ transactionHistory.get(position).getVehicle_type());
                            Log.d(TAG, "onItemClick: Database Check for amount"+ transactionHistory.get(position).getDistance());


//                                    Intent intent1 = new Intent(MainActivity.this,HistoryActivity.class);
//                                    intent1.putExtra("history_mobile",contacts.get(position).getMobile());
//                                    Log.d(TAG, "onItemClick: The transfer data"+ intent1);
                            startActivity(intent);
                    }
                }
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.vehicle_type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        home_spinner.setAdapter(adapter);
        home_spinner.setOnItemSelectedListener(this);

        paymentSubmit();
        setupfirebaseAuth();



//        final List<Transaction> transactionHistory = mVehicleDatabase.transactionDao().gethistory();
//        info = view.findViewById(R.id.text_db_info);
//        String text ="";
//
//        for (Transaction v : transactionHistory) {
//
//            String vehicleNbr = v.getVehicle_no();
//            String vehicleType = v.getVehicle_type();
//            String dateStamp = v.getTimeStamp();
//
//            text = text + "\n\n" + "Vehicle Number : " + vehicleNbr + "\n Vehicle Type : " + vehicleType + "\n Time : " + dateStamp;
//
//        }
//
//        info.setText(text);

        return view;
    }



    //Required For Fill Entire Forms

    private boolean checkinputs(String vehicle_no, String source, String destination, String distance, String amount) {
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if (vehicle_no.equals("") || source.equals("") || destination.equals("") || distance.equals("") || amount.equals("") ) {

            Toast.makeText(getActivity(), "Please fill all the field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    //Enabling Payment Submit Button
        public void paymentSubmit(){

        Log.d(TAG, "paymentSubmit: Initializing Payment");

        payment_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String home_number = home_vehicle_no.getText().toString();
                String source = home_source.getText().toString();
                String destination = home_destination.getText().toString();
                String distance = home_distance.getText().toString();
                String amount = home_amount.getText().toString();

                String payment_time = DateFormat.getDateTimeInstance().format(new Date());

                if (checkinputs(home_number,source,destination,distance,amount)) {
                    final Transaction transaction = new Transaction();

                    transaction.setAmount(Integer.valueOf(amount));
                    transaction.setAmount(Integer.valueOf(distance));
                    transaction.setDestination(destination);
                    transaction.setSource(source);
                    transaction.setVehicle_no(home_number);
                    transaction.setTimeStamp(payment_time);



                    Log.d(TAG, "onClick: Payment details add");

                    int getUUID = UUID.randomUUID().hashCode();
                    transaction.setTransaction_id(getUUID);

                    mVehicleDatabase.transactionDao().addTransaction(transaction);

                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                    intent.putExtra("unique_id",getUUID);
                    getActivity().overridePendingTransition(0,0);
                    startActivity(intent);


                    home_vehicle_no.setText("");
                    home_source.setText("");
                    home_destination.setText("");
                    home_distance.setText("");
                    home_amount.setText("");

                }

            }
        });
    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, "onItemSelected: Selecting Vehicle Type");

        home_vehicle_type = parent.getItemAtPosition(position).toString();


        //Instantiating database table
        final Transaction transaction = new Transaction();

        //storing data into table
        if (position ==0){

            Log.d(TAG, "onItemSelected: Its the default vehicle type");
        }else {
            transaction.setVehicle_type(home_vehicle_type);
            Log.d(TAG, "onItemSelected: Stored data is " + transaction.getVehicle_type());
            Toast.makeText(getActivity(), home_vehicle_type, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: The home fragment is resumed now");
        super.onResume();

        final List<Transaction> transactionHistory = mVehicleDatabase.transactionDao().gethistory();
        transactionAdapter = new TransactionAdapter(transactionHistory,getActivity(),mVehicleDatabase);
        transaction_list.setAdapter(transactionAdapter);

    }

    @Override
    public void onPause() {

        Log.d(TAG, "onPause: The Home Fragment has been paused");
        super.onPause();

    }


    // -----------------------------FireBase Setup-----------------------------------------

    /**
     * Setting up firebase Auth
     */

//    private void checkCurrentUser(FirebaseUser User){
//        Log.d(TAG, "checkCurrentUser: checking current user");
//        if (User == null) {
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }

    private void setupfirebaseAuth() {
        Log.d(TAG, "setupfirebaseAuth: SettingUp firebase Authentications");

        mAuth = FirebaseAuth.getInstance();
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
//                checkCurrentUser(user);

                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: Signed In" + user.getUid());
                } else {
                    // User is Signed out
                    Log.d(TAG, "onAuthStateChanged: Signed Out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(AuthListner);
//        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListner!= null){
            mAuth.removeAuthStateListener(AuthListner);
        }
    }
}
