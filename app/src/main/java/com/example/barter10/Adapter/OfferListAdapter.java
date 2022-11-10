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

import com.example.barter10.Model.viewOffers;
import com.example.barter10.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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


        Picasso.get()
                .load(viewOffers.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.offerImg);
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
        ImageView offerImg, btnOfferMsg, subMenu, userProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.o_username);
            location = itemView.findViewById(R.id.o_location);
            itemName = itemView.findViewById(R.id.o_itemName);
            itemDetails = itemView.findViewById(R.id.o_itemDetails);
            itemCondition = itemView.findViewById(R.id.o_itemCondition);
            itemValue = itemView.findViewById(R.id.o_itemValue);
            offerImg = itemView.findViewById(R.id.o_postImage);
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