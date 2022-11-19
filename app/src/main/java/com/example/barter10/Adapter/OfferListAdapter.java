package com.example.barter10.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barter10.Model.Offer;
import com.example.barter10.Model.viewOffers;
import com.example.barter10.Post.FullPostFragment;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Offer> list;

    public OfferListAdapter(Context context, ArrayList<Offer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer_layout, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Offer offer = list.get(position);

        //Show Offer Details//
        holder.userName.setText(offer.getUserName());
        holder.rating.setText(offer.getRating());
        holder.itemName.setText("Item Name : "+offer.getItemName());
        holder.itemDetails.setText("Item Details : "+offer.getItemDetails());
        holder.itemCondition.setText("Item Condition : "+offer.getItemCondition());
        holder.itemValue.setText("Item Value : "+offer.getItemValue());
        holder.location.setText("Location : "+offer.getLocation());

        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = offer.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");


        for (int i =0 ; i < pictures.length ; i++){

            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        holder.offerImg.setImageList(slideModels, ScaleTypes.FIT);


        Picasso.get()
                .load(offer.getProfileUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.userProfile);


        //Determine if post is pinned or not//
        Boolean getPinValue = offer.getPinValue();

        if(getPinValue.equals(true)){
            //Offer pinned//
            holder.subMenu.setImageResource(R.drawable.ic_pin);
        }
        else {
            //Offer Unpin//
            holder.subMenu.setImageResource(R.drawable.ic_unpin);
        }


        //Hides the pin button to the offerers//
        String posterId = offer.getPosterId();
        if(FirebaseAuth.getInstance().getUid().equals(posterId) ){
            holder.subMenu.setVisibility(View.VISIBLE);
        }
        else {
            holder.subMenu.setVisibility(View.GONE);
        }


        //Methods for pinning the item//
        holder.subMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Pin Methods Start//

                DatabaseReference pinRef = FirebaseDatabase.getInstance().getReference("PinnedPost");

                String postKey = offer.getPostKey();
                String offerKey = offer.getOfferKey();
                Boolean getPinValue = offer.getPinValue();


                //Pinning the Post//
                if (getPinValue.equals(false)) {
                    Toast.makeText(context, "Pin", Toast.LENGTH_SHORT).show();
                    pinRef.child(postKey).setValue(offer);

                    HashMap changePinValue = new HashMap();
                    changePinValue.put("pinValue", true);
                    pinRef.child(postKey).updateChildren(changePinValue);

                    //Deletion of pin//
                    DatabaseReference pinDelete = FirebaseDatabase.getInstance().getReference("Offer").child(postKey);
                    pinDelete.child(offerKey).removeValue();


                    //Refresh Full Post Page//
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new FullPostFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("ItemKey", postKey);
                    bundle.putString("uId", posterId);
                    fragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up).replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();
                    //Pin Methods End//

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName, rating, itemName, itemDetails, itemCondition, itemValue,location;
        ImageView  btnOfferMsg, subMenu, userProfile;
        ImageSlider offerImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.o_username);
            rating = itemView.findViewById(R.id.o_rating);
            itemName = itemView.findViewById(R.id.o_itemName);
            itemDetails = itemView.findViewById(R.id.o_itemDetails);
            itemCondition = itemView.findViewById(R.id.o_itemCondition);
            itemValue = itemView.findViewById(R.id.o_itemValue);
            location = itemView.findViewById(R.id.o_location);
            offerImg = itemView.findViewById(R.id.o_image_slider);
            btnOfferMsg = itemView.findViewById(R.id.btnOfferMsg);
            userProfile = itemView.findViewById(R.id.o_userProfile);
            subMenu = itemView.findViewById(R.id.o_subMenu);

        }

    }
}