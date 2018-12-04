package com.example.adityanarayan.tollpay.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    public void addTransaction(Transaction history);

    @Query("SELECT vehicle_no,amount,timeStamp,distance,destination,source,vehicle_type,transaction_id,card_cvv,success from transaction_details")
    List<Transaction> gethistory();


//    @Query("SELECT amount FROM transaction_details WHERE vehicle_no = :number")
//    void getVehicleAmount(String number);


}
