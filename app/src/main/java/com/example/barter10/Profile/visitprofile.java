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

import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Adapter.VisitPostAdapter;
import com.example.barter10.Home;
import com.example.barter10.MessageActivity;
import com.example.barter10.Model.Upload;
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
    private TextView username;

    private ImageButton btn_message,btn_report;
    private Button btn_follow;

    private DatabaseReference databaseReference;

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
        btn_message = view.findViewById(R.id.btn_message);
        btn_follow = view.findViewById(R.id.btn_follow);
        btn_report = view.findViewById(R.id.btn_report);


        //displaying post of user
        recyclerView = view.findViewById(R.id.prof_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploads = new ArrayList<>();
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

//                        Toast.makeText(getContext(), profieid+"!@#", Toast.LENGTH_SHORT).show();
                        String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                        Picasso.get()
                                .load(Profilepic)
                                .placeholder(R.drawable.ic_default_picture)
                                .into(imageprofile);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost");
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

        //send message
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("userid", profieid);
                getContext().startActivity(intent);


            }
        });



        return view;
    }
}