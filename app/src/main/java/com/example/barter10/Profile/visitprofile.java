package com.example.barter10.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Adapter.SelfPostAdapter;
import com.example.barter10.BottomNavigation.HomeFragment;
import com.example.barter10.Home;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.Profile.profileSettings;
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
    private TextView countPost;

    private Button btn_message;

    private DatabaseReference databaseReference;

    private List<Upload> mUploads;
    private RecyclerView recyclerView;
    private SelfPostAdapter selfPostAdapter;
    String profieid,search_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visitprofile, container, false);

        backbtn = view.findViewById(R.id.v_backarr);

        imageprofile = view.findViewById(R.id.v_image_profile);
        username = view.findViewById(R.id.v_profilename);
        countPost = view.findViewById(R.id.v_numpost);
        btn_message = view.findViewById(R.id.btn_message);


        //displaying post of user
        recyclerView = view.findViewById(R.id.prof_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mUploads = new ArrayList<>();
        selfPostAdapter = new SelfPostAdapter(getContext(), mUploads);
        recyclerView.setAdapter(selfPostAdapter);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Home.class));
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profieid = sharedPreferences.getString("uid", "none");
        search_username = sharedPreferences.getString("username","none");

        //Displaying the username into profile fragment
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");


        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){





                    //for visiting in search fragment
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){


                        //Visiting the users in display post

                        //if current user visits his own profile
                        if (profieid.equals(FirebaseAuth.getInstance().getUid())){
                            btn_message.setVisibility(View.GONE);
                        }

                        //if current user visits other user
                        if (dataSnapshot.getKey().equals(profieid)){

//                        Toast.makeText(getContext(), profieid+"!@#", Toast.LENGTH_SHORT).show();
                            String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                            Picasso.get()
                                    .load(Profilepic)
                                    .placeholder(R.drawable.ic_default_picture)
                                    .into(imageprofile);
                            username.setText(dataSnapshot.child("username").getValue().toString());
                            break;
                        }



//                        Toast.makeText(getContext(), search_username+"POTA", Toast.LENGTH_SHORT).show();

                        if (postSnapshot.getValue().equals(search_username)){

                            Toast.makeText(getContext(), postSnapshot.getValue()+"tite", Toast.LENGTH_SHORT).show();

                            String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                            Picasso.get()
                                    .load(Profilepic)
                                    .placeholder(R.drawable.ic_default_picture)
                                    .into(imageprofile);
                            username.setText(postSnapshot.getValue().toString());
                            break;

                        }

                        if(profieid.equals(postSnapshot.getValue())){
                            btn_message.setVisibility(View.GONE);
                        }


                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Displaying userpost
        databaseReference = FirebaseDatabase.getInstance().getReference("PostItem");
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


                    countPost.setText(""+snapshot.getChildrenCount());
                }


                selfPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}