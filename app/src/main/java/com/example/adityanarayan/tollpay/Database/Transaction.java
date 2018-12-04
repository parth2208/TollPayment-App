package com.example.adityanarayan.tollpay.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "transaction_details")
public class Transaction {

    @PrimaryKey
    @NonNull
    private String vehicle_no;

    private String vehicle_type;

    private String timeStamp;

    private String source;

    private String destination;

    private int distance;

    private int amount;

    private int transaction_id;

    private String cardTimeStamp;

    private int card_cvv;

    private String card_no;

    private boolean success;


    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCardTimeStamp() {
        return cardTimeStamp;
    }

    public void setCardTimeStamp(String cardTimeStamp) {
        this.cardTimeStamp = cardTimeStamp;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getCard_cvv() {
        return card_cvv;
    }

    public void setCard_cvv(int card_cvv) {
        this.card_cvv = card_cvv;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
