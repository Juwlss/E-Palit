package com.example.barter10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.Model.Pending;
import com.example.barter10.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.MyViewHolder> {

    Context context;
    List<Pending> mTrade;

    public FinishedAdapter(Context context, List<Pending> mTrade){
        this.context = context;
        this.mTrade = mTrade;
    }

    @NonNull
    @Override
    public FinishedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.finished_layout, parent, false);
        return new FinishedAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedAdapter.MyViewHolder holder, int position) {
        //username
        holder.offererName.setText(mTrade.get(position).getOfferer());
        holder.offereeName.setText(mTrade.get(position).getOfferee());
        //userimage
        holder.offererImage.setImageResource(mTrade.get(position).getOfferer_image());
        holder.offereeImage.setImageResource(mTrade.get(position).getOfferee_image());
        //tradeimage
        holder.offererTrade.setImageResource(mTrade.get(position).getOfferer_trade());
        holder.offereeTrade.setImageResource(mTrade.get(position).getOfferee_trade());

    }

    @Override
    public int getItemCount() {
        return mTrade.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView offereeImage, offererImage;
        ImageView offereeTrade, offererTrade;
        TextView offereeName, offererName;

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



        }
    }
}
