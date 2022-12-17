package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Adapter.PendingAdapter;
import com.example.barter10.List.PendingFragment;
import com.example.barter10.Model.Feedback;
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
    private String tradeId,offereeId, offererId, postKey;
    private RatingBar ratingBar;
    private EditText feedback;

    private String username,picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);




        backbtn = findViewById(R.id.rate_back);
        submitComment = findViewById(R.id.submit_comment);
        ratingBar = findViewById(R.id.rating_bar);
        feedback = findViewById(R.id.feedback);

        tradeId = getIntent().getStringExtra("tradeId");
        offereeId =  getIntent().getStringExtra("offereeId");
        offererId =  getIntent().getStringExtra("offererId");
        postKey = getIntent().getStringExtra("postKey");




        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Auctioneer
                DatabaseReference uptradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
                DatabaseReference tradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);

                //Bidder
                DatabaseReference tradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
                DatabaseReference uptradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);


                if (FirebaseAuth.getInstance().getUid().equals(offereeId)){

                    //If Auctioneer click the confirm, the value will change to true instead of null//
                    tradeStatus.child("offeree").setValue("null");

                    uptradeStatus.child("offeree").setValue("null");



                } else if (FirebaseAuth.getInstance().getUid().equals(offererId)){

                    //If Bidder click the confirm, the value will change to true instead of null//
                    tradeStatus2.child("offerer").setValue("null");
                    uptradeStatus2.child("offerer").setValue("null");

                }


                startActivity(new Intent(Rating.this, Home.class));
            }
        });


        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("users");
        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    if (user.getUserID().equals(FirebaseAuth.getInstance().getUid())){
                        username = user.getUsername();
                        picture = user.getProfilepic();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








        RateTrade(tradeId, postKey);

//        disableButton();

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



//        submitComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
//
//                    //If Offeree click the confirm, the value will change to true instead of null//
//                    tradeStatus.child("offeree").setValue("true");
//
//                    uptradeStatus.child("offeree").setValue("true");
//
//
//
//
//                }
//                else if (FirebaseAuth.getInstance().getUid().equals(offererId)){
//                    //If Offerer click the confirm, the value will change to true instead of null//
//                    tradeStatus2.child("offerer").setValue("true");
//                    uptradeStatus2.child("offerer").setValue("true");
//
//
//
//
//                }
//
//
//            }
//        });



    }




//    //offeree
//    DatabaseReference uptradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
//    DatabaseReference tradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
//    //offerer
//    DatabaseReference tradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
//    DatabaseReference uptradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
//
//
//
//                                if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
//
//        //If Offeree click the confirm, the value will change to true instead of null//
//        tradeStatus.child("offeree").setValue("true");
//
//        uptradeStatus.child("offeree").setValue("true");
//
//
//
//    } else if (FirebaseAuth.getInstance().getUid().equals(offererId)){
//        //If Offerer click the confirm, the value will change to true instead of null//
//        tradeStatus2.child("offerer").setValue("true");
//        uptradeStatus2.child("offerer").setValue("true");
//
//    }


    private void RateTrade(String tradeId, String postKey){



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


                                String comment = feedback.getText().toString();

                                DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference("Feedbacks").child(tradeId).child(postKey);
                                commentRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        Feedback feedback = new Feedback(FirebaseAuth.getInstance().getUid(), username, comment, picture, rating);
                                        commentRef.setValue(feedback);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                startActivity(new Intent(Rating.this, Home.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();




        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);




    }


    //    private void disableButton(){
//
//
//        //offeree
//        DatabaseReference uptradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
//        DatabaseReference tradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
//        //offerer
//        DatabaseReference tradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
//        DatabaseReference uptradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
//
//
//
//
//        //                Check if current user is == to the offeree
//        if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
//
//
//            tradeStatus.child("offerer").setValue("true");
//
//            uptradeStatus.child("offerer").setValue("true");
//
//
//
//        }
//        else{
//
//            tradeStatus2.child("offerer").setValue("true");
//
//            uptradeStatus2.child("offerer").setValue("true");
//
//        }
//
//
//    }

}