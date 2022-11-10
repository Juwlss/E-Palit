package com.example.barter10.Model;

public class FullPost {
    String post_id,itemName,itemDetails,itemCondition,itemValue,itemPreference,itemCategory,timeLimit;

    public FullPost() {
    }

    public FullPost(String post_id, String itemName, String itemDetails, String itemCondition, String itemValue, String itemPreference, String itemCategory, String timeLimit) {
        this.post_id = post_id;
        this.itemName = itemName;
        this.itemDetails = itemDetails;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.itemPreference = itemPreference;
        this.itemCategory = itemCategory;
        this.timeLimit = timeLimit;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(String itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }
}
