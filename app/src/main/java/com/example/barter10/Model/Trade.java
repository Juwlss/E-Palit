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

    public Trade() {
    }

    public Trade(String offereeId, String offereeName, String offereeProfile, String offereeImg, String offererId, String offererName, String offererProfile, String offererImg, String postKey, String status) {
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
