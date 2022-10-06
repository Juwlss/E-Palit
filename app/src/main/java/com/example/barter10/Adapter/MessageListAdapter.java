package com.example.barter10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.Model.Message;
import com.example.barter10.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    Context context;
    ArrayList<Message> messageArrayList;


    public MessageListAdapter(Context context, ArrayList<Message> messageArrayList){
        this.context = context;
        this.messageArrayList = messageArrayList;
    }


    @NonNull
    @Override
    public MessageListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.messages, parent, false);

        return new MessageListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.MyViewHolder holder, int position) {
        //assigning values to the views
        holder.receiverName.setText(messageArrayList.get(position).getUsername());
        holder.messageContent.setText(messageArrayList.get(position).getMessage());
        holder.circleImageView.setImageResource(messageArrayList.get(position).getReceiverImage());
    }

    @Override
    public int getItemCount() {
        // no of items you want to display
        return messageArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView receiverName, messageContent;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.image_message);
            receiverName = itemView.findViewById(R.id.username);
            messageContent = itemView.findViewById(R.id.message);
        }
    }
}
