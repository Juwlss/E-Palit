package com.example.barter10.BottomNavigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Adapter.VisitPostAdapter;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.example.barter10.Profile.profileSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    private List<Upload> mUploads;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private VisitPostAdapter selfPostAdapter;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private TextView rating,followers,following;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton settings = view.findViewById(R.id.btn_settings);

        //profile
        ImageView imageprofile = view.findViewById(R.id.image_profile);
        TextView profilename = view.findViewById(R.id.profilename);
        rating = view.findViewById(R.id.rating);
        followers = view.findViewById(R.id.followers_count);
        following = view.findViewById(R.id.following_count);


        //displaying post of user
        recyclerView = view.findViewById(R.id.prof_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mUploads = new ArrayList<>();
        selfPostAdapter = new VisitPostAdapter(getContext(), mUploads);
        recyclerView.setAdapter(selfPostAdapter);

        //referencing to the firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //displaying the post of user
        String userId = firebaseAuth.getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference("ClosedBid");
//
//        Query qPost = databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
//
//        qPost.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //fetching from firebase to display
//                mUploads.clear();
//                for(DataSnapshot postSnapshot : snapshot.getChildren()){
//
//                    Upload upload = postSnapshot.getValue(Upload.class);
//                    upload.setKey(postSnapshot.getKey());
//                    mUploads.add(upload);
//
//
//                }
//
//                selfPostAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        //display the close bid of user
        DatabaseReference closeRef = FirebaseDatabase.getInstance().getReference("ClosedBid");
        closeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fetching from firebase to display
                mUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());

                    for (DataSnapshot dataSnapshot : postSnapshot.getChildren()){
                        if (upload.getUid().equals(FirebaseAuth.getInstance().getUid())){
                            mUploads.add(upload);
                            break;
                        }
                    }

                }
                selfPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //go to profile settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment profileSettingFrag = new profileSettings();
                fragmentTransaction.replace(R.id.frame_layout, profileSettingFrag);
                fragmentTransaction.commit();
            }
        });


        //Displaying the username into profile fragment
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");

        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                        Picasso.get()
                                .load(Profilepic)
                                .placeholder(R.drawable.ic_default_picture)
                                .into(imageprofile);


                        rating.setText("Rating: "+dataSnapshot.child("rating").getValue().toString()+"/5");
                        profilename.setText(dataSnapshot.child("username").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        getFollowers();



        return view;
    }

    private void getFollowers(){
        //Displaying followers
        DatabaseReference followerRef = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getUid()).child("followers");
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
                .child(FirebaseAuth.getInstance().getUid()).child("following");
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