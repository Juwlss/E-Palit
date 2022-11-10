package com.example.barter10.Model;

import com.google.firebase.database.Exclude;

public class Offer {
    private String uid;
    private String imageUrl;
    private String profileUrl;
    private String userName;
    private String location;
    private String itemName;
    private String itemCondition;
    private String mKey;
    private String itemDetails;
    private String itemValue;

    public Offer() {
    }

    public Offer(String uid, String imageUrl, String profileUrl, String userName, String location, String itemName, String itemCondition, String itemDetails, String itemValue) {
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
        this.userName = userName;
        this.location = location;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
        this.itemDetails = itemDetails;
        this.itemValue = itemValue;
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

    @Exclude
    public String getKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String key){
        this.mKey = key;
    }


}
