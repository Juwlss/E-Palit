package com.example.barter10.Model;

public class User {
    String userID, fullname, profilepic, username, userpassword, userPhone;

    int rating;

    public User() {
    }

    public User(String userID, String fullname, String profilepic, String username, String userpassword, String userPhone, int rating) {
        this.userID = userID;
        this.fullname = fullname;
        this.profilepic = profilepic;
        this.username = username;
        this.userpassword = userpassword;
        this.userPhone = userPhone;
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
