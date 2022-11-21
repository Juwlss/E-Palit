package com.example.barter10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barter10.Model.Offer;
import com.example.barter10.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FinishOfferAdapter extends RecyclerView.Adapter<FinishOfferAdapter.MyOfferHolder> {
    Context context;
    List<Offer> list;

    public FinishOfferAdapter(Context context, List<Offer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer_layout, parent, false);
        return new MyOfferHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOfferHolder holder, int position) {

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


        holder.subMenu.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyOfferHolder extends RecyclerView.ViewHolder{

        private TextView userName, rating, itemName, itemDetails, itemCondition, itemValue,location;
        private ImageView btnOfferMsg, subMenu, userProfile;
        private ImageSlider offerImg;

        public MyOfferHolder(@NonNull View itemView) {
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
