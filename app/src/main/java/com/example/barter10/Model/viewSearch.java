package com.example.barter10.Model;

public class viewSearch {

    private String imageUrl;
    private String userName;
    private String profileUrl;
    private String location;
    private String itemName;
    private String itemCondition;

    public viewSearch(String userName, String location, String imageUrl, String itemName, String itemCondition) {
        this.userName = userName;
        this.location = location;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
    }

    public viewSearch() {
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
}
