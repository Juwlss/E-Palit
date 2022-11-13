package com.example.barter10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barter10.Model.viewOffers;
import com.example.barter10.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.MyViewHolder> {

    Context context;
    ArrayList<viewOffers> list;

    public OfferListAdapter(Context context, ArrayList<viewOffers> list) {
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
        viewOffers viewOffers = list.get(position);

        holder.userName.setText(viewOffers.getUserName());
        holder.location.setText(viewOffers.getLocation());
        holder.itemName.setText("Item Name : "+viewOffers.getItemName());
        holder.itemDetails.setText("Item Details : "+viewOffers.getItemDetails());
        holder.itemCondition.setText("Item Condition : "+viewOffers.getItemCondition());
        holder.itemValue.setText("Item Value : "+viewOffers.getItemValue());

        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = viewOffers.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");




        for (int i =0 ; i < pictures.length ; i++){

            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        holder.offerImg.setImageList(slideModels, ScaleTypes.FIT);


        Picasso.get()
                .load(viewOffers.getProfileUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.userProfile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView userName, location, itemName, itemDetails, itemCondition, itemValue;
        ImageView  btnOfferMsg, subMenu, userProfile;
        ImageSlider offerImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.o_username);
            location = itemView.findViewById(R.id.o_location);
            itemName = itemView.findViewById(R.id.o_itemName);
            itemDetails = itemView.findViewById(R.id.o_itemDetails);
            itemCondition = itemView.findViewById(R.id.o_itemCondition);
            itemValue = itemView.findViewById(R.id.o_itemValue);
            offerImg = itemView.findViewById(R.id.o_image_slider);
            btnOfferMsg = itemView.findViewById(R.id.btnOfferMsg);
            userProfile = itemView.findViewById(R.id.o_userProfile);
            subMenu = itemView.findViewById(R.id.o_subMenu);
            subMenu.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view);

        }

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.pinPost:
                    Toast.makeText(itemView.getContext(), "Pin", Toast.LENGTH_SHORT).show();

                    return true;

                default:
                    return false;
            }

        }
    }
}