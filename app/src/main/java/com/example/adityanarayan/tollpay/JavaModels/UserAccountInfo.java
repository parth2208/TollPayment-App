package com.example.adityanarayan.tollpay.JavaModels;

public class UserAccountInfo {

   private User user;
   private UserSettings userSettings;


    public UserAccountInfo(User user, UserSettings userSettings) {
        this.user = user;
        this.userSettings = userSettings;
    }


    public UserAccountInfo() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }


    @Override
    public String toString() {
        return "UserAccountInfo{" +
                "user=" + user +
                ", userSettings=" + userSettings +
                '}';
    }
}
