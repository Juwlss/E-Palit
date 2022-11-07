package com.example.barter10.Search;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;

public class viewSearchHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView location;
    public TextView itemName;
    public TextView condition;
    public ImageView searchImage;
    public ImageView userImage;


    public Button visitProfile;
    Button viewPost;
    ImageView react;

    public viewSearchHolder(@NonNull View itemView) {
        super(itemView);

        userName= itemView.findViewById(R.id.username);
        location = itemView.findViewById(R.id.location);
        searchImage = itemView.findViewById(R.id.postImage);

        userImage = itemView.findViewById(R.id.userProfile);
        visitProfile = itemView.findViewById(R.id.userLayout);

        itemName= itemView.findViewById(R.id.itemName);
        condition = itemView.findViewById(R.id.itemCondition);
        viewPost = itemView.findViewById(R.id.viewPost);
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