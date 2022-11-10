package com.example.barter10.Model;

public class viewOffers {
    private String imageUrl;
    private String userName;
    private String location;
    private String profileUrl;
    private String itemName;
    private String itemCondition;
    private String itemDetails;
    private String itemValue;

    public viewOffers() {
    }

    public viewOffers(String imageUrl, String userName, String profileUrl, String location,String itemName, String itemCondition, String itemDetails, String itemValue) {
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.location = profileUrl;
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
