package com.example.barter10.Model;

public class User {
    String userID, fullname, profilepic, username, userpassword, userPhone;


    public User(){

    }

    public User(String userID, String fullname, String profilepic, String username, String userpassword, String userPhone) {
        this.username = userID;
        this.fullname = fullname;
        this.profilepic = profilepic;
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

    public String getProfilepic() {
        return profilepic;
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

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
