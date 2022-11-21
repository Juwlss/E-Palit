package com.example.barter10.Model;

public class User {
    String userID, email, profilepic, username, userPhone;

    float rating;

    public User() {

    }

    public User(String userID, String email, String profilepic, String username, String userPhone, float rating) {
        this.userID = userID;
        this.email = email;
        this.profilepic = profilepic;
        this.username = username;
        this.userPhone = userPhone;
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
