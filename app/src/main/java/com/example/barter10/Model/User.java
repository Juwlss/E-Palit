package com.example.barter10.Model;

public class User {
    String userID, username, useremail, userpassword;


    public User(){

    }

    public User(String userID, String username, String useremail, String userpassword) {
        this.username = userID;
        this.username = username;
        this.useremail = useremail;
        this.userpassword = userpassword;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserpassword() {
        return userpassword;
    }


}
