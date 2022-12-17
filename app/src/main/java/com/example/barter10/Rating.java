package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.barter10.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating extends AppCompatActivity {



    private ImageView backbtn;
    private Button submitComment;
    private int rateCount =0;
    private String tradeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);


        backbtn = findViewById(R.id.rate_back);
        submitComment = findViewById(R.id.submit_comment);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rating.this, Home.class));
            }
        });


//        if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("userid", offererId);
//            context.startActivity(intent);
//        }else if (FirebaseAuth.getInstance().getUid().equals(offererId)){
//            Intent intent = new Intent(context, MessageActivity.class);
//            intent.putExtra("userid", offereeId);
//            context.startActivity(intent);
//        }

//        getIntent().getStringExtra("tradeId");

        SharedPreferences sharedPreferences = getSharedPreferences("Trade", MODE_PRIVATE);
        RatingBar ratingBar = findViewById(R.id.rating_bar);
        tradeId = sharedPreferences.getString("tradeId", "none");

        //getting how many users did rate the user
        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("TradeStatus").child(tradeId);
        countRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rateCount = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("users");
        ratingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (dataSnapshot.child("userID").getValue().toString().equals(tradeId)){
                        submitComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                float rating = ratingBar.getRating()+user.getRating();
                                for (int i = 1; i <= rateCount; i++){
                                    if (i >=2){
                                        dataSnapshot.child("rating").getRef().setValue(rating/2);
                                    }else if (i==1){
                                        dataSnapshot.child("rating").getRef().setValue(rating/1);
                                    }
                                }

                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}