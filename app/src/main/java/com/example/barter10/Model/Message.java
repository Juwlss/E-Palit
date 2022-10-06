package com.example.barter10.Model;

public class Message {
    String username;
    String message;
    int receiverImage;

    public Message(String username, String message, int receiverImage) {
        this.username = username;
        this.message = message;
        this.receiverImage = receiverImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(int receiverImage) {
        this.receiverImage = receiverImage;
    }
}
