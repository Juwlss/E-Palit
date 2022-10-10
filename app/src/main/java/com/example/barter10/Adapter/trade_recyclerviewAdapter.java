package com.example.barter10.Adapter;

import android.content.Context;
import android.media.Image;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class trade_recyclerviewAdapter extends RecyclerView.Adapter<trade_recyclerviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Pending> pendingArrayList;

    public trade_recyclerviewAdapter (Context context, ArrayList<Pending> pendingArrayList){
        this.context = context;
        this.pendingArrayList = pendingArrayList;
    }

    @NonNull
    @Override
    public trade_recyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trade_layout, parent, false);
        return new trade_recyclerviewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull trade_recyclerviewAdapter.MyViewHolder holder, int position) {
        //username
        holder.offererName.setText(pendingArrayList.get(position).getOfferer());
        holder.offereeName.setText(pendingArrayList.get(position).getOfferee());
        //userimage
        holder.offererImage.setImageResource(pendingArrayList.get(position).getOfferer_image());
        holder.offereeImage.setImageResource(pendingArrayList.get(position).getOfferee_image());
        //tradeimage
        holder.offererTrade.setImageResource(pendingArrayList.get(position).getOfferer_trade());
        holder.offereeTrade.setImageResource(pendingArrayList.get(position).getOfferee_trade());

    }

    @Override
    public int getItemCount() {
        return pendingArrayList.size();
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
