package com.example.barter10.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public PostImageAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_layout, parent, false);


        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload upload = mUploads.get(position);

        holder.userName.setText(upload.getUserName());
        holder.location.setText(upload.getLocation());
        holder.itemName.setText(upload.getItemName());
        holder.condition.setText(upload.getItemCondition());

        Picasso.get()
                .load(upload.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.postImage);

        Picasso.get()
                .load(upload.getProfileUrl())
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .fit()
                .into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView userName;
        public TextView location;
        public TextView itemName;
        public TextView condition;
        public ImageView postImage;
        public ImageView userImage;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.username);
            userImage = itemView.findViewById(R.id.userProfile);
            location = itemView.findViewById(R.id.location);
            itemName = itemView.findViewById(R.id.itemName);
            condition = itemView.findViewById(R.id.itemCondition);
            postImage = itemView.findViewById(R.id.postImage);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem dowhatEver = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

            dowhatEver.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhateverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhateverClick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
