package com.example.barter10;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewSearchHolder extends RecyclerView.ViewHolder {

    TextView itemName;
    TextView itemCondition;
    Button viewPost;
    ImageView react;
    public viewSearchHolder(@NonNull View itemView) {
        super(itemView);

        itemName= itemView.findViewById(R.id.itemName);
        itemCondition = itemView.findViewById(R.id.itemCondition);
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