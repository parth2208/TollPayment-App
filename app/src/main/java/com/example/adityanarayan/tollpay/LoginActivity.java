package com.example.adityanarayan.tollpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";


    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;

    private EditText userName;
    private EditText mpassword;
    private ProgressBar progressBar;
    private AppCompatImageButton loginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.login_progressbar);
        userName =  findViewById(R.id.login_editText_username);
        mpassword = findViewById(R.id.login_editText_password);

        progressBar.setVisibility(View.GONE);



        setupfirebaseAuth();
        btninitial();
    }





    private boolean checkinputs(String string){
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if(string.equals("")){
            return true;
        }else {
            return false;
        }
    }




    private void btninitial(){
        Log.d(TAG, "btoninitial: Initalization of button");
        loginButton = findViewById(R.id.login_imgButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Login button is clicked");
                final String username = userName.getText().toString();
                String password = mpassword.getText().toString();

                if (checkinputs(username) && checkinputs(password)){
                    Toast.makeText(LoginActivity.this,"Fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onClick: Its here to check the accessibility of the code");
                    //This will be the action when we click the login button
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (!task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:Failed",task.getException());
                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                        progressBar.setVisibility(View.GONE);

                                    } else {
                                        try{
                                            if (user.isEmailVerified()){
                                                Log.d(TAG, "onComplete: Email is verified");
                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                startActivity(intent);
                                            }else {
                                                Log.d(TAG, "onComplete: Email is not verified yet");
                                                Toast.makeText(LoginActivity.this,"Not verified Check your inbox",Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                Log.d(TAG, "onComplete: To check if the user verified the email or not");
                                                mAuth.signOut();
                                            }

                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: " + e.getMessage() );
                                        }
                                    }

                                }
                            });
                }

            }
        });
        // It will Navigate to Register Activity if there is a new user.

        TextView signUpLink = (TextView) findViewById(R.id.login_text_signUp);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to Register Activity");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });


        //It will Navigate to Home page if signed in already.
        if (mAuth.getCurrentUser()!=null){
            Log.d(TAG, "btninitial: Navigating to the MainActivity");
            Intent intent  = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }








    ///////////////////////Firebase Auth Methods///////////////////////////

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
    protected void onStop() {
        super.onStop();
        if (AuthListner!= null){
            mAuth.removeAuthStateListener(AuthListner);
        }
    }

}
