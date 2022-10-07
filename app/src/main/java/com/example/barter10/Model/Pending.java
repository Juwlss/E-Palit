package com.example.barter10.Model;

public class Pending {
    String offeree;
    String offerer;

    int offeree_image;
    int offerer_image;

    int offeree_trade;
    int offerer_trade;

    public Pending(String offeree, String offerer, int offeree_image, int offerer_image, int offeree_trade, int offerer_trade) {
        this.offeree = offeree;
        this.offerer = offerer;
        this.offeree_image = offeree_image;
        this.offerer_image = offerer_image;
        this.offeree_trade = offeree_trade;
        this.offerer_trade = offerer_trade;
    }

    public String getOfferee() {
        return offeree;
    }

    public String getOfferer() {
        return offerer;
    }

    public int getOfferee_image() {
        return offeree_image;
    }

    public int getOfferer_image() {
        return offerer_image;
    }

    public int getOfferee_trade() {
        return offeree_trade;
    }

    public int getOfferer_trade() {
        return offerer_trade;
    }
}
