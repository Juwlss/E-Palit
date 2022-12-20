package com.example.barter10.Model;

public class Trade {

    String offereeId;
    String offereeName;
    String offereeProfile;
    String offereeImg;




    String offererId;
    String offererName;
    String offererProfile;
    String offererImg;

    String postKey;
    String status;
    String offerKey;

    String aucItemName;
    String bidItemName;
    String aucRating;
    String bidRating;
    public Trade() {
    }

    public Trade(String offereeId, String offereeName, String offereeProfile, String offereeImg, String offererId, String offererName, String offererProfile, String offererImg, String postKey, String status, String offerKey, String aucItemName, String bidItemName, String aucRating, String bidRating) {
        this.offereeId = offereeId;
        this.offereeName = offereeName;
        this.offereeProfile = offereeProfile;
        this.offereeImg = offereeImg;
        this.offererId = offererId;
        this.offererName = offererName;
        this.offererProfile = offererProfile;
        this.offererImg = offererImg;
        this.postKey = postKey;
        this.status = status;
        this.offerKey = offerKey;
        this.aucItemName = aucItemName;
        this.bidItemName = bidItemName;
        this.aucRating = aucRating;
        this.bidRating = bidRating;
    }

    public String getAucItemName() {
        return aucItemName;
    }

    public void setAucItemName(String aucItemName) {
        this.aucItemName = aucItemName;
    }

    public String getBidItemName() {
        return bidItemName;
    }

    public void setBidItemName(String bidItemName) {
        this.bidItemName = bidItemName;
    }

    public String getAucRating() {
        return aucRating;
    }

    public void setAucRating(String aucRating) {
        this.aucRating = aucRating;
    }

    public String getBidRating() {
        return bidRating;
    }

    public void setBidRating(String bidRating) {
        this.bidRating = bidRating;
    }

    public String getOfferKey() {
        return offerKey;
    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getOffereeId() {
        return offereeId;
    }

    public void setOffereeId(String offereeId) {
        this.offereeId = offereeId;
    }

    public String getOffereeName() {
        return offereeName;
    }

    public void setOffereeName(String offereeName) {
        this.offereeName = offereeName;
    }

    public String getOffereeProfile() {
        return offereeProfile;
    }

    public void setOffereeProfile(String offereeProfile) {
        this.offereeProfile = offereeProfile;
    }

    public String getOffereeImg() {
        return offereeImg;
    }

    public void setOffereeImg(String offereeImg) {
        this.offereeImg = offereeImg;
    }

    public String getOffererId() {
        return offererId;
    }

    public void setOffererId(String offererId) {
        this.offererId = offererId;
    }

    public String getOffererName() {
        return offererName;
    }

    public void setOffererName(String offererName) {
        this.offererName = offererName;
    }

    public String getOffererProfile() {
        return offererProfile;
    }

    public void setOffererProfile(String offererProfile) {
        this.offererProfile = offererProfile;
    }

    public String getOffererImg() {
        return offererImg;
    }

    public void setOffererImg(String offererImg) {
        this.offererImg = offererImg;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
