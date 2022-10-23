package com.example.barter10.Model;

public class viewSearch {
    String itemName,itemCondition;

    public viewSearch(String itemName, String itemCondition) {
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
}
