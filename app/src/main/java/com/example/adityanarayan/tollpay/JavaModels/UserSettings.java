package com.example.adityanarayan.tollpay.JavaModels;

public class UserSettings {



    private String display_name;
    private String username;
    private String display_photo;

    public UserSettings(String display_name, String username, String display_photo) {
        this.display_name = display_name;
        this.username = username;
        this.display_photo = display_photo;
    }


    public UserSettings() {
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplay_photo() {
        return display_photo;
    }

    public void setDisplay_photo(String display_photo) {
        this.display_photo = display_photo;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "display_name='" + display_name + '\'' +
                ", username='" + username + '\'' +
                ", display_photo='" + display_photo + '\'' +
                '}';
    }
}
