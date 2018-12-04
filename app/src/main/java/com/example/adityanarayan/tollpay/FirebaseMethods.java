package com.example.adityanarayan.tollpay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.adityanarayan.tollpay.JavaModels.User;
import com.example.adityanarayan.tollpay.JavaModels.UserAccountInfo;
import com.example.adityanarayan.tollpay.JavaModels.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener AuthListner;
    private String userID;
    private FirebaseDatabase database;
    private DatabaseReference myref;

    private Context mContext;

    public FirebaseMethods(Context mContext) {
        mAuth = FirebaseAuth.getInstance();
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }


    public void registerNewUser(final String username, final String email, String password, final String display_name) {
        Log.d(TAG, "registerNewUser: Method registering new user");
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        } else if (task.isSuccessful()) {

                            //sending verification mail
                            sendVerificationEmail();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: AuthState changed" + userID);

                        }

                    }
                });
    }


    //For sending verification email to the user
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    } else {
                        Toast.makeText(mContext, "Can't send the verification email", Toast.LENGTH_LONG);
                    }
                }
            });
        }
    }

    public void updateUserSettings(String display_Name, final long mobile_no){
        Log.d(TAG, "changeUsername: Updating  "+ display_Name);
        if (display_Name!= null) {
            myref.child(mContext.getString(R.string.db_user_settings)).child(userID)
                    .child(mContext.getString(R.string.field_dispalyName)).setValue(display_Name);
        }

        if (mobile_no!= 0) {
            myref.child(mContext.getString(R.string.db_user_settings)).child(userID)
                    .child(mContext.getString(R.string.field_mobileNo)).setValue(mobile_no);
        }
    }


    public void changeUsername(String username){
        Log.d(TAG, "changeUsername: Changing username "+ username);

        myref.child(mContext.getString(R.string.db_user)).child(userID)
                .child(mContext.getString(R.string.field_username)).setValue(username);

        myref.child(mContext.getString(R.string.db_user_settings)).child(userID)
                .child(mContext.getString(R.string.field_username)).setValue(username);

    }



    public void addNewUser(String username, String email, String display_name, String display_photo) {

        //this will add the values of the child in the User node
        User user = new User(
                username,
                email,
                userID,
                1);

        myref.child(mContext.getString(R.string.db_user))
                .child(userID).setValue(user);

        //This will add the values of the child in the UserSettings node

        UserSettings userSettings = new UserSettings(
                display_name,
                display_photo,
                username);

        myref.child(mContext.getString(R.string.db_user_settings))
                .child(userID)
                .setValue(userSettings);

    }






    //Get the user settings info form the database using datasnapshot
    //for the users that are using the app
    // Into the app design.


    public UserAccountInfo getuserInfo(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getuserInfo: getting user general Info");

        UserSettings settings = new UserSettings();
        User user = new User();

        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


            //This if will work for the User_Account_Setting node particularly
            if (dataSnapshot1.getKey().equals(mContext.getString(R.string.db_user_settings))){
                Log.d(TAG, "getuserInfo: getting information from firebase" + dataSnapshot1);


                try {
                    settings.setDisplay_name(
                            dataSnapshot1.child(userID)
                            .getValue(UserSettings.class)
                            .getUsername()
                    );

                    settings.setUsername(
                            dataSnapshot1.child(userID)
                                    .getValue(UserSettings.class)
                                    .getUsername()
                    );
                    settings.setDisplay_photo(
                            dataSnapshot1.child(userID)
                                    .getValue(UserSettings.class)
                                    .getDisplay_photo()
                    );
                }catch (NullPointerException e){
                    Log.e(TAG, "getuserInfo: NullPointerException"+ e );
                }
            }

            //This if will work for the User node particularly
            if (dataSnapshot1.getKey().equals(mContext.getString(R.string.db_user))) {
                Log.d(TAG, "getuserInfo: " + dataSnapshot1);

                user.setUsername(
                        dataSnapshot1.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );

                user.setEmail(
                        dataSnapshot1.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );

                user.setMobile_no(
                        dataSnapshot1.child(userID)
                                .getValue(User.class)
                                .getMobile_no()
                );

                user.setUser_id(
                        dataSnapshot1.child(userID)
                                .getValue(User.class)
                                .getUser_id()
                );
                Log.d(TAG, "getuserInfo: got the info from user node" + user.toString());


            }
        }
        return new UserAccountInfo(user,settings);

    }




}
