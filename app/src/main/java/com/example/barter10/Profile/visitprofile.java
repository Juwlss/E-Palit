package com.example.barter10.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Adapter.FollowAdapter;
import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Adapter.VisitPostAdapter;
import com.example.barter10.Home;
import com.example.barter10.MessageActivity;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class visitprofile extends Fragment {

    public visitprofile() {
        // Required empty public constructor
    }


    private ImageView backbtn;
    private ImageView imageprofile;
    private TextView username,followers,following,rating;

    private ImageButton btn_message,btn_report;
    private Button btn_follow;

    private DatabaseReference databaseReference;
    private List<User> mUsers;

    private List<Upload> mUploads;
    private RecyclerView recyclerView;
    private VisitPostAdapter selfPostAdapter;
    String profieid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitprofile, container, false);

        backbtn = view.findViewById(R.id.v_backarr);

        imageprofile = view.findViewById(R.id.v_image_profile);
        username = view.findViewById(R.id.v_profilename);
        followers = view.findViewById(R.id.followers_count);
        following = view.findViewById(R.id.following_count);
        rating = view.findViewById(R.id.rating);
        btn_message = view.findViewById(R.id.btn_message);
        btn_follow = view.findViewById(R.id.v_btn_follow);


        //displaying post of user
        recyclerView = view.findViewById(R.id.prof_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUploads = new ArrayList<>();
        mUsers = new ArrayList<>();
        selfPostAdapter = new VisitPostAdapter(getContext(), mUploads);
        recyclerView.setAdapter(selfPostAdapter);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Home.class));
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profieid = sharedPreferences.getString("uid", "none");

        //Displaying the username into profile fragment
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");
        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    //Visiting the users in display post

                    //if current user visits his own profile
                    if (profieid.equals(FirebaseAuth.getInstance().getUid())){
                        btn_message.setVisibility(View.GONE);
                        btn_follow.setVisibility(View.GONE);
                        btn_report.setVisibility(View.GONE);
                    }

                    //if current user visits other user
                    if (dataSnapshot.getKey().equals(profieid) ){

                        String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                        Picasso.get()
                                .load(Profilepic)
                                .placeholder(R.drawable.ic_default_picture)
                                .into(imageprofile);


                        rating.setText("Rating: "+dataSnapshot.child("rating").getValue().toString()+"/5");
                        username.setText(dataSnapshot.child("username").getValue().toString());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Displaying userpost
        databaseReference = FirebaseDatabase.getInstance().getReference("ClosedBid");
        Query qPost = databaseReference.orderByChild("uid").equalTo(profieid);

        qPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fetching from firebase to display
                mUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);


                }


                selfPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //display the close bid of user
//        DatabaseReference closeRef = FirebaseDatabase.getInstance().getReference("ClosedBid");
//
//        Query queryClose = closeRef.orderByChild("uid").equalTo(profieid);
//        queryClose.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //fetching from firebase to display
//                mUploads.clear();
//                for(DataSnapshot postSnapshot : snapshot.getChildren()){
//                    Upload upload = postSnapshot.getValue(Upload.class);
//                    upload.setKey(postSnapshot.getKey());
//                        mUploads.add(upload);
//                }
//                selfPostAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



        //send message
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("userid", profieid);
                getContext().startActivity(intent);


            }
        });



        //follow
        isFollowing(profieid, btn_follow);
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_follow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance().getUid())
                            .child("following").child(profieid).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profieid)
                            .child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(FirebaseAuth.getInstance().getUid())
                            .child("following").child(profieid).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profieid)
                            .child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue();
                }
            }
        });

        getFollowers();




        return view;
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


    private void getFollowers(){
        //Displaying followers
        DatabaseReference followerRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(profieid).child("followers");
        followerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followers.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Displaying following
        DatabaseReference followingRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(profieid).child("following");
        followingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                following.setText(""+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}