package com.example.barter10.Model;

import android.widget.ImageView;

import com.google.firebase.database.Exclude;

public class Upload {

    private String uid;
    private String imageUrl;
    private String userName;
    private String profileUrl;
    private String location;
    private String itemName;
    private String itemCondition;
    private String mKey;
    private String category1;
    private String itemDetails;
    private String itemValue;
    private String itemPreference;
    private String timer;

    public Upload() {
    }

    public Upload(String uid, String imageUrl, String userName, String profileUrl, String location, String itemName, String itemCondition, String category1, String itemDetails, String itemValue, String itemPreference, String timer) {
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.profileUrl = profileUrl;
        this.location = location;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
        this.category1 = category1;
        this.itemDetails = itemDetails;
        this.itemValue = itemValue;
        this.itemPreference = itemPreference;
        this.timer = timer;
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

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
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

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
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

    public String getItemPreference() {
        return itemPreference;
    }

    public void setItemPreference(String itemPreference) {
        this.itemPreference = itemPreference;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
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
