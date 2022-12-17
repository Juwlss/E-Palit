package com.example.barter10.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.FinishPostFragment;
import com.example.barter10.List.FinishedFragment;
import com.example.barter10.Model.Feedback;
import com.example.barter10.Model.User;
import com.example.barter10.Post.FullPostFragment;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.example.barter10.Profile.visitprofile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Feedback> mFeedbacks;

    public FeedbackAdapter(Context mContext, List<Feedback> mFeedbacks) {
        this.mContext = mContext;
        this.mFeedbacks = mFeedbacks;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.feedback_layout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {


        Feedback feedback = mFeedbacks.get(position);

        holder.commentorName.setText(feedback.getUsername());
        holder.commentorFeedback.setText(feedback.getFeedback());
        holder.commentorRating.setRating(feedback.getRating());

        Picasso.get()
                .load(feedback.getUserImage())
                .placeholder(R.drawable.ic_default_picture)
                .fit()
                .into(holder.commentorImage);


    }

    @Override
    public int getItemCount() {
        return mFeedbacks.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {


        private TextView commentorName, commentorFeedback;
        private ImageView commentorImage;
        private RatingBar commentorRating;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            commentorName = itemView.findViewById(R.id.feedback_username);
            commentorFeedback = itemView.findViewById(R.id.tv_feedback);
            commentorRating = itemView.findViewById(R.id.feedback_rate);
            commentorImage = itemView.findViewById(R.id.image_feedback);


        }




    }






}

