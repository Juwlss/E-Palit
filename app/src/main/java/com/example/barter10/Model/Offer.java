package com.example.barter10.Model;


public class Offer {
    private String uid;
    private String imageUrl;
    private String profileUrl;
    private String userName;
    private String location;
    private String itemName;
    private String itemCondition;
    private String postKey;
    private String itemDetails;
    private String itemValue;
    private String posterId;
    private String offerKey;
    private Boolean pinValue;
    public Offer() {
    }

    public Offer(String uid, String imageUrl, String profileUrl, String userName, String location, String itemName, String itemCondition, String itemDetails, String itemValue, String postKey, String posterId, String offerKey, Boolean pinValue) {
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.userName = userName;
        this.location = location;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
        this.itemDetails = itemDetails;
        this.itemValue = itemValue;
        this.postKey = postKey;
        this.posterId = posterId;
        this.offerKey = offerKey;
        this.pinValue = pinValue;
    }

    public Boolean getPinValue() {
        return pinValue;
    }

    public void setPinValue(Boolean pinValue) {
        this.pinValue = pinValue;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getOfferKey() {
        return offerKey;
    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }


}
