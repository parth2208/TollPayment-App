package com.example.adityanarayan.tollpay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    public static final int Activity_Num = 3;

    private Context mContext = ProfileActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        setupBottomNavigationView();
    }


//        private void setupBottomNavigationView(){
//            Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//            BottomNavigationViewEx navigationView = findViewById(R.id.bottom_nav);
//            BottomNavigationViewHelper.setupBottomNavigationView(navigationView);
//            BottomNavigationViewHelper.enableNavigation(mContext, navigationView);
//            Menu menu = navigationView.getMenu();
//            MenuItem menuItem = menu.getItem(Activity_Num);
//            menuItem.setChecked(true);
//        }
}
