package com.example.barter10.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.MessageActivity;
import com.example.barter10.Model.Message;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public MessageListAdapter(Context mContext, List<User> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.messages, parent, false);

        return new MessageListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.ViewHolder holder, int position) {
        User user = mUsers.get(position);

        holder.username.setText(user.getUsername());
        if(user.getProfilepic().equals("default")){
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Picasso.get()
                    .load(user.getProfilepic())
                    .placeholder(R.drawable.ic_default_picture)
                    .into(holder.profileImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getUserID());
                mContext.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profileImage;
        public RelativeLayout viewF, viewB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.message_username);
            profileImage = itemView.findViewById(R.id.image_message);
            viewF = itemView.findViewById(R.id.message_rl);
            viewB = itemView.findViewById(R.id.view_background);

        }
    }

    public void removeItem(int position){
        mUsers.remove(position);

        //updating recyclerview
        notifyItemRemoved(position);
    }

    public void restoreItem(User user, int position){
        mUsers.add(position,user);
        notifyItemInserted(position);

    }

}
