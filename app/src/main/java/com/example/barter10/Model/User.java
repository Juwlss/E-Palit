package com.example.barter10.Model;

public class User {
    String userID, fullname, username, userpassword, userPhone;


    public User(){

    }

    public User(String userID, String fullname, String username, String userpassword, String userPhone) {
        this.username = userID;
        this.fullname = fullname;
        this.username = username;
        this.userpassword = userpassword;
        this.userPhone = userPhone;

    }

    public String getUserID() {
        return userID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }


    public String getUserpassword() {
        return userpassword;
    }

    public String getUserPhone() {
        return userPhone;
    }
}
