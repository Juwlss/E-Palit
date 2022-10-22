package com.example.barter10.Model;

import android.widget.ImageView;

public class Upload {

    String imageUrl;

    public Upload(){

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Upload(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
