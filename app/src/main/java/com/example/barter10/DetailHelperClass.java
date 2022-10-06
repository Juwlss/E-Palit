package com.example.barter10;

public class DetailHelperClass {
    String post_id,itemName,itemDetails,itemCondition,itemValue,itemPreference,itemCategory,timeLimit;

    public DetailHelperClass() {
    }

    public DetailHelperClass(String post_id,String itemName, String itemDetails, String itemCondition, String itemValue, String itemPreference,String timeLimit,String itemCategory) {
        this.post_id = post_id;
        this.itemName = itemName;
        this.itemDetails = itemDetails;
        this.itemCondition = itemCondition;
        this.itemValue = itemValue;
        this.itemPreference = itemPreference;
        this.timeLimit = timeLimit;
        this.itemCategory = itemCategory;

    }
    public  String getPost_id() {

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

    public String getTimeLimit() {

        return timeLimit;
    }

    public void setItemTime(String timeLimit) {

        this.timeLimit = timeLimit;
    }

    public String getItemCategory() {

        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {

        this.itemCategory = itemCategory;
    }
}
