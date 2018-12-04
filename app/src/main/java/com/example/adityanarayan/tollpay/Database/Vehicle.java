package com.example.adityanarayan.tollpay.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "vehicle_details")
public class Vehicle {

    @PrimaryKey
    @NonNull
    private String vehicle_no;

    private String vehicle_name;

    private String timeStamp;

    private String vehicle_type;



    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }


}
