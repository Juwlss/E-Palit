package com.example.barter10.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Post.FullPostFragment;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.example.barter10.Profile.visitprofile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        List <SlideModel> slideModels = new ArrayList<>();

        holder.userName.setText(upload.getUserName());
        holder.rating.setText(upload.getRating());
        holder.itemName.setText(upload.getItemName());
        holder.details.setText(upload.getItemCondition());


        Picasso.get()
                .load(upload.getProfileUrl())
                .placeholder(R.drawable.ic_default_picture)
                .fit()
                .into(holder.userImage);

        //multiple images
        String rep = upload.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");




        for (int i =0 ; i < pictures.length ; i++){


            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        holder.imageSlider.setImageList(slideModels, ScaleTypes.FIT);




        holder.visitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("uid", upload.getUid());
                editor.apply();


                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                meowBottomNavigation.setVisibility(View.GONE);

                Fragment fragment = new visitprofile();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();

//                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,new ProfileFragment());
            }
        });

        holder.viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();// GET SPECIFIC KEY
                Upload selectedItem = mUploads.get(pos);// FIND SPECIFIC KEY
                String postKey = selectedItem.getKey();// STORE SPECIFIC KEY
                String uid = selectedItem.getUid();//STORE SPECIFIC USER ID

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new FullPostFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ItemKey", postKey);
                bundle.putString("uId", uid);
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                        .replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView userName;
        public TextView rating;
        public TextView itemName;
        public TextView details;
        public ImageView userImage;

        public Button viewPost;
        public Button visitProfile;
        public ImageSlider imageSlider;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSlider = itemView.findViewById(R.id.image_slider);

            userName = itemView.findViewById(R.id.username);
            userImage = itemView.findViewById(R.id.userProfile);
            rating = itemView.findViewById(R.id.Prating);
            itemName = itemView.findViewById(R.id.itemName);
            details = itemView.findViewById(R.id.itemDetails);

            viewPost = itemView.findViewById(R.id.viewPost);
            visitProfile = itemView.findViewById(R.id.userLayout);



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
