package com.example.barter10.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.Model.Pending;
import com.example.barter10.R;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {

    Context context;
    ArrayList<Pending> pendingArrayList;
    Dialog dialog;

    public PendingAdapter(Context context, ArrayList<Pending> pendingArrayList){
        this.context = context;
        this.pendingArrayList = pendingArrayList;
    }

    @NonNull
    @Override
    public PendingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trade_layout, parent, false);
        return new PendingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.MyViewHolder holder, int position) {
        //username
        holder.offererName.setText(pendingArrayList.get(position).getOfferer());
        holder.offereeName.setText(pendingArrayList.get(position).getOfferee());
        //userimage
        holder.offererImage.setImageResource(pendingArrayList.get(position).getOfferer_image());
        holder.offereeImage.setImageResource(pendingArrayList.get(position).getOfferee_image());
        //tradeimage
        holder.offererTrade.setImageResource(pendingArrayList.get(position).getOfferer_trade());
        holder.offereeTrade.setImageResource(pendingArrayList.get(position).getOfferee_trade());



        holder.confirmTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup_rating);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                TextView confirm_rate = dialog.findViewById(R.id.confirm_rate);
                RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);

                confirm_rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(dialog.getContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return pendingArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView offereeImage, offererImage;
        ImageView offereeTrade, offererTrade;
        TextView offereeName, offererName;
        Button confirmTrade;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //userimage
            offereeImage = itemView.findViewById(R.id.offeree_user);
            offererImage = itemView.findViewById(R.id.offerer_user);
            //trade item
            offereeTrade = itemView.findViewById(R.id.image_offeree);
            offererTrade = itemView.findViewById(R.id.image_offerer);
            //username
            offereeName = itemView.findViewById(R.id.offeree_name);
            offererName = itemView.findViewById(R.id.offerer_name);

            confirmTrade = itemView.findViewById(R.id.trade_confirm);


        }
    }
}
