package com.example.adityanarayan.tollpay.JavaModels;

public class User {


    private String username;
    private String email;
    private String user_id;
    private long mobile_no;

    public User(String username, String email, String user_id, long mobile_no) {
        this.username = username;
        this.email = email;
        this.user_id = user_id;
        this.mobile_no = mobile_no;
    }

    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(long mobile_no) {
        this.mobile_no = mobile_no;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", mobile_no=" + mobile_no +
                '}';
    }
}
