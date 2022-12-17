package com.example.barter10.Model;

public class Feedback {

    String userId;
    String username;
    String feedback;
    String userImage;
    Float rating;

    public Feedback(String userId, String username, String feedback, String userImage, Float rating) {
        this.userId = userId;
        this.username = username;
        this.feedback = feedback;
        this.userImage = userImage;
        this.rating = rating;
    }

    public Feedback() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
