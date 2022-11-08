package com.example.barter10.Model;

public class Message {
    String username;
    String userImage;
    String message;

    public Message(String username, String userImage, String message) {
        this.username = username;
        this.message = message;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
