package com.example.adityanarayan.tollpay;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adityanarayan.tollpay.Database.Transaction;
import com.example.adityanarayan.tollpay.Database.VehicleDatabase;
import com.example.adityanarayan.tollpay.Utility.ExpireSlashFormat;
import com.example.adityanarayan.tollpay.Utility.FourDigitFormat;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.zip.Inflater;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

//    AppCompatEditText card_no, holder_name, cvv;
////    AppCompatSpinner expiry_year, expiry_month;
    AppCompatImageButton payment_button;

    AppCompatEditText card_no,cvv,exp_date,holder_name;

    TextView uuid;
    VehicleDatabase mVehicleDatabase;

    EasyFlipView easyFlipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        
        payment_button = findViewById(R.id.submit_payment_btn);
        card_no = findViewById(R.id.edit_card_no);
        holder_name = findViewById(R.id.edit_card_holder);
        cvv = findViewById(R.id.edit_card_cvv);
        exp_date = findViewById(R.id.edit_exp_date);
        easyFlipView = findViewById(R.id.easy_card_flip);
        uuid = findViewById(R.id.db_unique_id);


        int unique_id = getIntent().getIntExtra("unique_id",0);
        uuid.setText(Integer.toString(unique_id));

        card_no.addTextChangedListener(new FourDigitFormat());
        exp_date.addTextChangedListener(new ExpireSlashFormat());

        mVehicleDatabase = Room.databaseBuilder(this,VehicleDatabase.class,"dbVehicle").fallbackToDestructiveMigration().allowMainThreadQueries().build();


//        expiry_year = findViewById(R.id.expiry_spinner_year);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        expiry_year.setAdapter(adapter);
//        expiry_year.setOnItemSelectedListener(this);
//
//
//        //Months
//
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.date,android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        expiry_month.setAdapter(adapter1);
//        expiry_month.setOnItemSelectedListener(this);

        payment_next_btn();

    }

    public void payment_next_btn(){

        payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Its working correctly ");

                String payment_time = DateFormat.getDateTimeInstance().format(new Date());
                String card_number = card_no.getText().toString();
                String customer_name = holder_name.getText().toString();
                String card_cvv = cvv.getText().toString();
                String expiry = exp_date.getText().toString();


                if (checkinputs(card_number,customer_name,card_cvv,expiry)){

                    final Transaction transaction = new Transaction();

                    transaction.setCard_cvv(Integer.valueOf(card_cvv));
                    transaction.setCard_no(card_number);
                    transaction.setCardTimeStamp(payment_time);

//                    mVehicleDatabase.transactionDao().addTransaction(transaction);
                    Toast.makeText(PaymentActivity.this, "Payment Done Successful", Toast.LENGTH_SHORT).show();

                    card_no.setText("");
                    holder_name.setText("");
                    cvv.setText("");
                    exp_date.setText("");

                    Intent intent = new Intent();
                }

            }
        });

    }

    private boolean checkinputs(String number, String name, String cvv, String expiry) {
        Log.d(TAG, "checkinputs: Checking if input field is filled or not");

        if (number.equals("") || name.equals("") || cvv.equals("") || expiry.equals("")) {

            Toast.makeText(this, "Please fill all the field.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//        Toast.makeText(this, "Card is valid ", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
