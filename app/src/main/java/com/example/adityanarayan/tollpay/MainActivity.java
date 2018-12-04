package com.example.adityanarayan.tollpay;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.Utility.BottomNavigationVewHelper;
import com.example.adityanarayan.tollpay.Utility.SignOutDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";


//    AppCompatSpinner home_spinner;
//    AppCompatImageButton payment_submit_btn;
//    TextInputEditText home_vehicle_no, home_source, home_destination,home_distance, home_amount;
//    ImageView signOut_btn;
//
//    String home_vehicle_type;

    private static final int Activity_Num = 0;
    public static VehicleDatabase mVehicleDatabase;

    private Context mContext = MainActivity.this;

    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;


//    TransactionAdapter transactionAdapter;
//    ListView history_list;
    SectionsPageAdapter sectionsPageAdapter;
    ViewPager mViewPager;
    Toolbar toolbar;
    ImageView signOut_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut_btn = findViewById(R.id.signOut_btn);

        signOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                SignOutDialog dialog = new SignOutDialog();
                dialog.show(fragmentManager,"signOut");
                dialog.setCancelable(false);

            }
        });

//        home_spinner= findViewById(R.id.home_typ_spinner);
//        payment_submit_btn = findViewById(R.id.submit_payment_btn);
//        home_vehicle_no = findViewById(R.id.editText_vehicle_no);
//        home_source = findViewById(R.id.editText_home_source);
//        home_destination = findViewById(R.id.editText_home_destination);
//        home_distance = findViewById(R.id.editText_home_distance);
//        home_amount = findViewById(R.id.editText_home_amount);



//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.vehicle_type,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        home_spinner.setAdapter(adapter);
//        home_spinner.setOnItemSelectedListener(this);



        ///////Database Initialization
        Log.d(TAG, "onCreate: Database is initialized");
        mVehicleDatabase = Room.databaseBuilder(this,VehicleDatabase.class,"dbVehicle").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        mViewPager = findViewById(R.id.container);
        setupfirebaseAuth();
//        paymentSubmit();
        setupBottomNavigationView();
        setupViewPager(mViewPager);


        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);





        ///////////////////For debug process//////////////////////
//        history_list = findViewById(R.id.main_vehicle_list);
//        final List<Transaction> transactionHistory = mVehicleDatabase.transactionDao().gethistory();
//        transactionAdapter = new TransactionAdapter(transactionHistory,this);
//        history_list.setAdapter(transactionAdapter);
////////////////////////////////////////////////////////////////////////////////////

    }

//    static final Migration MIGRATION_1_2 = new Migration(1,2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `transaction_details` (`vehicle_no` TEXT, "
//                    + "`name` TEXT, PRIMARY KEY(`vehicle_no`))");
//        }
//    };



//    public void paymentSubmit(){
//
//        Log.d(TAG, "paymentSubmit: Initializing Payment");
//
//        payment_submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String home_number = home_vehicle_no.getText().toString();
//                String source = home_source.getText().toString();
//                String destination = home_destination.getText().toString();
//                String distance = home_distance.getText().toString();
//                String amount = home_amount.getText().toString();
//
//                String payment_time = DateFormat.getDateTimeInstance().format(new Date());
//
//                if (checkinputs(home_number,source,destination,distance,amount)) {
//                    final Transaction transaction = new Transaction();
//
//                    transaction.setAmount(Integer.valueOf(amount));
//                    transaction.setAmount(Integer.valueOf(distance));
//                    transaction.setDestination(destination);
//                    transaction.setSource(source);
//                    transaction.setVehicle_no(home_number);
//                    transaction.setTimeStamp(payment_time);
//
//                    mVehicleDatabase.transactionDao().addTransaction(transaction);
//
//                    Log.d(TAG, "onClick: Payment details add");
//
//                    Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
//                    startActivity(intent);
//
//                    home_vehicle_no.setText("");
//                    home_source.setText("");
//                    home_destination.setText("");
//                    home_distance.setText("");
//                    home_amount.setText("");
//
//                }
//
//            }
//        });
//    }



    private boolean checkinputs(String vehicle_no, String source, String destination, String distance, String amount) {
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if (vehicle_no.equals("") || source.equals("") || destination.equals("") || distance.equals("") || amount.equals("") ) {

            Toast.makeText(this, "Please fill all the field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    

    // -----------------------------FireBase Setup-----------------------------------------

    /**
     * Setting up firebase Auth
     */

    private void checkCurrentUser(FirebaseUser User){
        Log.d(TAG, "checkCurrentUser: checking current user");
        if (User == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void setupfirebaseAuth() {
        Log.d(TAG, "setupfirebaseAuth: SettingUp firebase Authentications");

        mAuth = FirebaseAuth.getInstance();
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                checkCurrentUser(user);

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
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListner!= null){
            mAuth.removeAuthStateListener(AuthListner);
        }
    }

    //Setting up BottomNavigation

    //Spinner Selection

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        Log.d(TAG, "onItemSelected: Selecting Vehicle Type");
//
//        home_vehicle_type = parent.getItemAtPosition(position).toString();
//
//
//        //Instantiating database table
//        final Transaction transaction = new Transaction();
//
//        //storing data into table
//        if (position ==0){
//
//            Log.d(TAG, "onItemSelected: Its the default vehicle type");
//
//        }else {
//            transaction.setVehicle_type(home_vehicle_type);
//            Log.d(TAG, "onItemSelected: Stored data is " + transaction.getVehicle_type());
//            Toast.makeText(this, home_vehicle_type, Toast.LENGTH_SHORT).show();
//        }
//
//
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }



    public void setupViewPager(ViewPager viewPager){

        SectionsPageAdapter pageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new HomeFragment(), "Home");
        viewPager.setAdapter(pageAdapter);
    }


    /*
    BottomNavigationBar Setup
     */

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavBarView);
        BottomNavigationVewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationVewHelper.enableNavigation(MainActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(Activity_Num);
        menuItem.setChecked(true);

    }
}
