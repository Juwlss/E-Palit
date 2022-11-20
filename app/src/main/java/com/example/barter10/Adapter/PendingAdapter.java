package com.example.barter10.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.List.FinishedFragment;
import com.example.barter10.Model.Trade;
import com.example.barter10.Model.User;
import com.example.barter10.Profile.visitprofile;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {
    private Context context;
    private List<Trade> tradeList;
    private Dialog dialog;
    private int rateCount =0;

    public PendingAdapter(Context context, List<Trade> tradeList) {
        this.context = context;
        this.tradeList = tradeList;
    }

    @NonNull
    @Override
    public PendingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(context).inflate(R.layout.trade_layout, parent, false);
        return new PendingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.MyViewHolder holder, int position) {
        Trade trade = tradeList.get(position);

        //Get Offeree id//
        String offereeId = trade.getOffereeId();
        //Get Offerer id//
        String offererId = trade.getOffererId();
        //Get Post id/key//
        String postKey = trade.getPostKey();

        //Offeree Details//
        Picasso.get()
                .load(trade.getOffereeProfile())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.offereeProfile);

        holder.offereeName.setText(trade.getOffereeName());

        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = trade.getOffereeImg().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");




        for (int i =0 ; i < pictures.length ; i++){

            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        holder.offereeImg.setImageList(slideModels, ScaleTypes.FIT);





        //Offerer Details//
        Picasso.get()
                .load(trade.getOffererProfile())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.offererProfile);

        holder.offererName.setText(trade.getOffererName());

        List<SlideModel> slideModels2 = new ArrayList<>();

        //multiple images
        String rep3 = trade.getOffererImg().replace("]","");
        String rep4 = rep3.replace("[","");
        String rep5 = rep4.replace(" ","");
        String[] pictures2 = rep5.split(",");




        for (int i =0 ; i < pictures2.length ; i++){

            slideModels2.add(new SlideModel(pictures2[i], "", ScaleTypes.FIT));

        }


        holder.offererImg.setImageList(slideModels2, ScaleTypes.FIT);
        //offeree
        DatabaseReference uptradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
        DatabaseReference tradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId).child(postKey);
        //offerer
        DatabaseReference tradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);
        DatabaseReference uptradeStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId).child(postKey);


        if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
            //Disable the button of offerer after clicking the confirm button
            uptradeStatus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String btn_offerer = snapshot.child("offeree").getValue().toString();

                    if (btn_offerer.equals("true")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);



                    }else if (btn_offerer.equals("false")){
                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            //Disable the button of offeree after clicking the confirm button
            uptradeStatus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String btn_offeree = snapshot.child("offerer").getValue().toString();

                    if (btn_offeree.equals("true")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);






                    }else if (btn_offeree.equals("false")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        if (FirebaseAuth.getInstance().getUid().equals(offereeId)){
            //Disable the button of offerer after clicking the confirm button
            uptradeStatus2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String btn_offerer = snapshot.child("offeree").getValue().toString();

                    if (btn_offerer.equals("true")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);



                    }else if (btn_offerer.equals("false")){
                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            //Disable the button of offeree after clicking the confirm button
            uptradeStatus2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String btn_offeree = snapshot.child("offerer").getValue().toString();

                    if (btn_offeree.equals("true")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);

                    }else if (btn_offeree.equals("false")){

                        holder.confirm.setBackgroundColor(Color.LTGRAY);
                        holder.confirm.setClickable(false);

                        holder.cancel.setBackgroundColor(Color.LTGRAY);
                        holder.cancel.setClickable(false);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }







        //Button Confirm
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Check if current user is == to the offeree//
                if (FirebaseAuth.getInstance().getUid().equals(offereeId)){

                    uptradeStatus.child("offeree").setValue("true");

                    //If Offeree click the confirm, the value will change to true instead of null//
                    tradeStatus.child("offeree").setValue("true");


                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_rating);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    TextView confirm_rate = dialog.findViewById(R.id.confirm_rate);
                    RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);


                    //getting how many users did rate the user
                    DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId);
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
                                if (dataSnapshot.child("userID").getValue().toString().equals(offererId)){
                                    confirm_rate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            float rating = ratingBar.getRating()+user.getRating();
                                            for (int i = 1; i <= rateCount; i++){
                                                if (i >=2){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/2);
                                                    dialog.dismiss();
                                                }else if (i==1){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/1);
                                                    dialog.dismiss();
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
                else{

                    //If Offerer click the confirm, the value will change to true instead of null//
                    tradeStatus2.child("offerer").setValue("true");

                    uptradeStatus2.child("offerer").setValue("true");


                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_rating);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    TextView confirm_rate = dialog.findViewById(R.id.confirm_rate);
                    RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);

                    //getting how many users did rate the user
                    DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId);
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
                                if (dataSnapshot.child("userID").getValue().toString().equals(offereeId)){
                                    confirm_rate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            float rating = ratingBar.getRating()+user.getRating();
                                            for (int i = 1; i <= rateCount; i++){
                                                if (i >=2){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/2);
                                                    dialog.dismiss();
                                                }else if (i==1){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/1);
                                                    dialog.dismiss();
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
        });





        //Button Cancel//
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (FirebaseAuth.getInstance().getUid().equals(offereeId)){


                    //If Offeree click the cancel, the value will change to false instead of null//
                    tradeStatus.child("offeree").setValue("false");

                    uptradeStatus.child("offeree").setValue("false");


                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_rating);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    TextView confirm_rate = dialog.findViewById(R.id.confirm_rate);
                    RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);

                    //getting how many users did rate the user
                    DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId);
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
                                if (dataSnapshot.child("userID").getValue().toString().equals(offererId)){
                                    confirm_rate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            float rating = ratingBar.getRating()+user.getRating();
                                            for (int i = 1; i <= rateCount; i++){
                                                if (i >=2){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/2);
                                                    dialog.dismiss();
                                                }else if (i==1){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/1);
                                                    dialog.dismiss();
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
                else{
                    //If Offerer click the cancel, the value will change to false instead of null//
                    tradeStatus2.child("offerer").setValue("false");
                    uptradeStatus2.child("offerer").setValue("false");




                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.popup_rating);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                    TextView confirm_rate = dialog.findViewById(R.id.confirm_rate);
                    RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);

                    //getting how many users did rate the user
                    DatabaseReference countRef = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offereeId);
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
                                if (dataSnapshot.child("userID").getValue().toString().equals(offereeId)){
                                    confirm_rate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            float rating = ratingBar.getRating()+user.getRating();
                                            for (int i = 1; i <= rateCount; i++){
                                                if (i >=2){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/2);
                                                    dialog.dismiss();
                                                    break;
                                                }else if (i==1){
                                                    dataSnapshot.child("rating").getRef().setValue(rating/1);
                                                    dialog.dismiss();
                                                    break;
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
        });








        //Get status in Trade Reference//
        if (FirebaseAuth.getInstance().getUid().equals(offereeId)){

            updateStatusOfferee(offererId,postKey,trade);




        }
        else if (FirebaseAuth.getInstance().getUid().equals(offererId)){
            updateStatusOfferer(offereeId,postKey,trade);
        }



    }



    private void updateStatusOfferee(String offererId, String postKey, Trade trade) {


        DatabaseReference upStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(FirebaseAuth.getInstance().getUid()).child(postKey);
        upStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valOfferee = snapshot.child("offeree").getValue().toString();
                String valOfferer = snapshot.child("offerer").getValue().toString();


                if (valOfferee.equals("null") || valOfferer.equals("null")){
                    Toast.makeText(context, "Status is still false1111", Toast.LENGTH_SHORT).show();
                }
                else if(valOfferee.equals("true") && valOfferer.equals("true")){

                    DatabaseReference offeree = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);
                    DatabaseReference offerer = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);

                    HashMap status  = new HashMap();
                    status.put("status", "true");
                    offeree.updateChildren(status);
                    offerer.updateChildren(status);

                    //Creating Finished reference for storing success/failed transactions//
                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                    finished1.setValue(trade);

                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offererId).child(postKey);
                    finished2.setValue(trade);



                    offeree.removeValue();
                    offerer.removeValue();



                }
                else if (valOfferee.equals("true") && valOfferer.equals("false") ||
                        valOfferee.equals("false") && valOfferer.equals("true")){

                    DatabaseReference offeree = FirebaseDatabase.getInstance().getReference("Trade").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                    DatabaseReference offerer = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);

                    HashMap status  = new HashMap();
                    status.put("status", "false");
                    offeree.updateChildren(status);
                    offerer.updateChildren(status);


                    //Creating Finished reference for storing success/failed transactions//
                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                    finished1.setValue(trade);

                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offererId).child(postKey);
                    finished2.setValue(trade);



                    offeree.removeValue();
                    offerer.removeValue();





                }else if (valOfferee.equals("false") && valOfferer.equals("false")){

                    DatabaseReference offeree = FirebaseDatabase.getInstance().getReference("Trade").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                    DatabaseReference offerer = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);

                    HashMap status  = new HashMap();
                    status.put("status", "false");
                    offeree.updateChildren(status);
                    offerer.updateChildren(status);


                    //Creating Finished reference for storing success/failed transactions//
                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                    finished1.setValue(trade);

                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offererId).child(postKey);
                    finished2.setValue(trade);



                    offeree.removeValue();
                    offerer.removeValue();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void updateStatusOfferer(String offereeId, String postKey, Trade trade) {

        DatabaseReference upStatus = FirebaseDatabase.getInstance().getReference("TradeStatus").child(FirebaseAuth.getInstance().getUid()).child(postKey);

        upStatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valOfferee = snapshot.child("offeree").getValue().toString();
                String valOfferer = snapshot.child("offerer").getValue().toString();

                DatabaseReference offerer = FirebaseDatabase.getInstance().getReference("Trade").child(FirebaseAuth.getInstance().getUid()).child(postKey);
                DatabaseReference offeree = FirebaseDatabase.getInstance().getReference("Trade").child(offereeId).child(postKey);

                if (valOfferee.equals("null") || valOfferer.equals("null")){
                    Toast.makeText(context, "Status is still false2", Toast.LENGTH_SHORT).show();
                }
                else if(valOfferee.equals("true") && valOfferer.equals("true")){

                    HashMap status  = new HashMap();
                    status.put("status", "true");
                    offerer.updateChildren(status);
                    offeree.updateChildren(status);

                    //Creating Finished reference for storing success/failed transactions//
//                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
//                    finished1.setValue(trade);
//
//                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offereeId).child(postKey);
//                    finished2.setValue(trade);


                    offerer.removeValue();
                    offeree.removeValue();

                }
                else if (valOfferee.equals("true") && valOfferer.equals("false") ||
                        valOfferee.equals("false") && valOfferer.equals("true")){



                    HashMap status  = new HashMap();
                    status.put("status", "false");
                    offerer.updateChildren(status);
                    offeree.updateChildren(status);

                    //Creating Finished reference for storing success/failed transactions//
//                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
//                    finished1.setValue(trade);
//
//                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offereeId).child(postKey);
//                    finished2.setValue(trade);


                    offerer.removeValue();
                    offeree.removeValue();



                }else if (valOfferee.equals("false") && valOfferer.equals("false")){

                    HashMap status  = new HashMap();
                    status.put("status", "false");
                    offerer.updateChildren(status);
                    offeree.updateChildren(status);

                    //Creating Finished reference for storing success/failed transactions//
//                    DatabaseReference finished1 = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid()).child(postKey);
//                    finished1.setValue(trade);
//
//                    DatabaseReference finished2 = FirebaseDatabase.getInstance().getReference("Finished").child(offereeId).child(postKey);
//                    finished2.setValue(trade);




                    offerer.removeValue();
                    offeree.removeValue();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView offereeProfile,offererProfile;
        TextView offereeName,offererName;
        ImageSlider offereeImg,offererImg;
        Button confirm,cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            offereeProfile = itemView.findViewById(R.id.offeree_user);
            offereeName = itemView.findViewById(R.id.offeree_name);
            offereeImg = itemView.findViewById(R.id.offeree_image_slider);

            offererProfile = itemView.findViewById(R.id.offerer_user);
            offererName = itemView.findViewById(R.id.offerer_name);
            offererImg = itemView.findViewById(R.id.offerer_image_slider);
            confirm = itemView.findViewById(R.id.trade_confirm);
            cancel = itemView.findViewById(R.id.trade_cancel);











        }
    }
}
