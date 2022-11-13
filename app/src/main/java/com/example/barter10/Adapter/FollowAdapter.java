package com.example.barter10.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.MessageActivity;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.Post.FullPostFragment;
import com.example.barter10.Profile.visitprofile;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public FollowAdapter(Context mContext, List<User> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public FollowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.follow_layout, parent, false);

        return new FollowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowAdapter.ViewHolder holder, int position) {


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
        holder.follow.setVisibility(View.VISIBLE);
        isFollowing(user.getUserID(), holder.follow);

        if (user.getUserID().equals(FirebaseAuth.getInstance().getUid())){
            holder.follow.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",user.getUserID());
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment fragment = new visitprofile();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit();
//                Intent intent = new Intent(mContext, visitprofile.class);
//                intent.putExtra("userid", user.getUserID());
//                mContext.startActivity(intent);
            }
        });

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.follow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance().getUid())
                    .child("following").child(user.getUserID()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserID())
                            .child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance().getUid())
                            .child("following").child(user.getUserID()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getUserID())
                            .child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue();
                }
            }
        });






    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profileImage;
        public Button follow;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.follow_username);
            profileImage = itemView.findViewById(R.id.image_follow);
            follow = itemView.findViewById(R.id.btn_follow);



        }
    }

    private void isFollowing(String userid, Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(FirebaseAuth.getInstance().getUid()).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(userid).exists()){
                    button.setText("following");
                }else{
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
