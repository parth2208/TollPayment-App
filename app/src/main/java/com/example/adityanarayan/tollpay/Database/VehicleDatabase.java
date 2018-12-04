package com.example.adityanarayan.tollpay.Database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Vehicle.class, Transaction.class},version = 2,exportSchema = false)
public abstract class VehicleDatabase extends RoomDatabase {


    public abstract VehicleDao vehicleDao();
    public abstract TransactionDao transactionDao();

}
