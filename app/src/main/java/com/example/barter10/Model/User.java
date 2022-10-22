package com.example.barter10.Model;

public class User {
    String username, useremail, userpassword;


    public User(){

    }

    public User(String username, String useremail, String usepassword) {
        this.username = username;
        this.useremail = useremail;
        this.userpassword = usepassword;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUsepassword() {
        return userpassword;
    }
}
