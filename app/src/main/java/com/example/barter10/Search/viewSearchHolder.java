package com.example.barter10.Search;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;

public class viewSearchHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView location;
    public TextView itemName;
    public TextView details;
    public ImageView userImage;

    public Button viewPost;
    public Button visitProfile;
    public ImageSlider imageSlider;


    ImageView react;

    public viewSearchHolder(@NonNull View itemView) {
        super(itemView);


        imageSlider = itemView.findViewById(R.id.image_slider);

        userName = itemView.findViewById(R.id.username);
        userImage = itemView.findViewById(R.id.userProfile);
        location = itemView.findViewById(R.id.location);
        itemName = itemView.findViewById(R.id.itemName);
        details = itemView.findViewById(R.id.itemDetails);

        viewPost = itemView.findViewById(R.id.viewPost);
        visitProfile = itemView.findViewById(R.id.userLayout);
        react = itemView.findViewById(R.id.react);

        react.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                React();
            }
        });
    }

    private void React() {
    }

}