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
    private String itemUser;
    public Upload(){

    }

    public Upload(String itemUser, String uid, String userName, String imageUrl, String location, String itemName, String itemCondition) {
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.itemUser = itemUser;
//        this.profileUrl = profileUrl;
        this.location = location;
        this.itemName = itemName;

        this.itemCondition = itemCondition;

    }

    public String getItemUser() {
        return itemUser;
    }

    public void setItemUser(String itemUser) {
        this.itemUser = itemUser;
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

//    public String getProfileUrl() {
//        return profileUrl;
//    }
//
//    public void setProfileUrl(String profileUrl) {
//        this.profileUrl = profileUrl;
//    }

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

    @Exclude
    public String getKey(){
        return mKey;
    }

    @Exclude
    public void setKey(String key){
        this.mKey = key;
    }
}
