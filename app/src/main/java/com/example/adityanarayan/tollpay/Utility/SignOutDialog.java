package com.example.adityanarayan.tollpay.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adityanarayan.tollpay.LoginActivity;
import com.example.adityanarayan.tollpay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutDialog extends DialogFragment {
    private static final String TAG = "SignOutDialog";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_sign_out,container,false);
        Log.d(TAG, "onCreateView: Dialog Fragment Started");

        Button confirm_btn = view.findViewById(R.id.dialog_signOut_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Confirming SignOut Request");

                mAuth.signOut();
                getDialog().dismiss();
            }
        });


        Button cancel_btn = view.findViewById(R.id.signOut_cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        setupfirebaseAuth();


        return view;

    }









    // -----------------------------Firebase Setup-----------------------------------------

    /**
     * Setting up firebase Auth
     */


    private void setupfirebaseAuth() {
        Log.d(TAG, "setupfirebaseAuth: SettingUp firebase Authentications");

        mAuth = FirebaseAuth.getInstance();
        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: Signed In" + user.getUid());
                } else {
                    // User is Signed out
                    Log.d(TAG, "onAuthStateChanged: Signed Out");

                    Log.d(TAG, "onAuthStateChanged: After signing Out it will naviagating back to login Activity");

                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(AuthListner);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListner!= null){
            mAuth.removeAuthStateListener(AuthListner);
        }
    }
}
