package com.example.adityanarayan.tollpay.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VehicleDao {

    @Insert
      void newVehicle(Vehicle vehicle);

    @Update
    void addVehicle(Vehicle vehicle);

   @Query("SELECT vehicle_type, vehicle_name, vehicle_no, timeStamp FROM vehicle_details")
    List<Vehicle> getVehicle();

//   Query(Delete )
//    void delete(Vehicle vehicle_all);
//   @Query("SELECT vehicle_type FROM vehicle_details WHERE vehicle_name = :vehicle")
//    void getVehicleType(String vehicle);
//
//    @Query("SELECT timeStamp FROM vehicle_details WHERE vehicle_name = :time")
//    void getTime(String time);

}
