package com.example.adityanarayan.tollpay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";


    private Context mContext;
    private String email, username, password, display_name;
    private EditText mEmail, mPassword, mUsername, mDisplay_name;
    private AppCompatImageButton registerbtn;
    private ProgressBar mProgressBar;

    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    private String append ="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        mContext = RegisterActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);



        initialWidget();
        initialBtn();
        setupfirebaseAuth();

    }




    private void initialBtn() {
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                display_name = mDisplay_name.getText().toString();

                if (checkinputs(email, username, password, display_name)) {

                    mProgressBar.setVisibility(View.VISIBLE);

                    firebaseMethods.registerNewUser(username,email,password,display_name);
                }
            }

        });
    }


    // Activity widget
    private void initialWidget() {
        Log.d(TAG, "initialWidget: This will initialize all the widget of the register page");
        mEmail = findViewById(R.id.register_editText_email);
        mPassword = findViewById(R.id.register_editText_password);
        mUsername = findViewById(R.id.register_editText_username);
        mDisplay_name = findViewById(R.id.register_editText_displayName);
        mProgressBar = findViewById(R.id.register_progressbar);
        registerbtn = findViewById(R.id.register_imgButton);
        mContext = RegisterActivity.this;
        mProgressBar.setVisibility(View.GONE);

    }

    private boolean checkinputs(String email, String username, String password, String display_name) {
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if (email.equals("") || username.equals("") || password.equals("") || display_name.equals("")) {

            Toast.makeText(mContext, "Please fill all the field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




    ////////////////////FireBase--------------------------

    private void usernameExist(final String username) {
        Log.d(TAG, "usernameExist: Existance of username" +username);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.db_user)).orderByChild(getString(R.string.field_username)).equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot oneTimeSnapshot : dataSnapshot.getChildren()){
                    if (oneTimeSnapshot.exists()){
                        Log.d(TAG, "onDataChange: Already exist");
                        append = myRef.push().getKey().substring(3,10);
                        Log.d(TAG, "onDataChange: The required username already exist");


                    }
                }
                // registration will be false if the user already exist.

                String mUsername = "";
                mUsername = username + append;

                firebaseMethods.addNewUser(mUsername,email,display_name,"");

                Toast.makeText(mContext,"Successful. Sending Verification Email",Toast.LENGTH_SHORT).show();
                // The user will be signed out till the email is not verified.
                mAuth.signOut();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /////////////FireBase Auth Methods///////////////////////

    private void setupfirebaseAuth() {

        Log.d(TAG, "setupfirebaseAuth: SettingUp firebase Authentications");

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        AuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user is signed in
                    Log.d(TAG, "onAuthStateChanged: Signed In" + user.getUid());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            usernameExist(username);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    finish();

                } else {
                    // User is Signed out
                    Log.d(TAG, "onAuthStateChanged: Signed Out");
                }
            }
        };
    }

    /*
      Firebase setup and the auth object.
     */

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(AuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (AuthListner != null) {
            mAuth.removeAuthStateListener(AuthListner);
        }
    }
}