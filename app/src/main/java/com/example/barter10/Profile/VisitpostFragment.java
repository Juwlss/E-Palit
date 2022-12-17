package com.example.barter10.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barter10.Adapter.VisitPostAdapter;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
import com.example.barter10.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class VisitpostFragment extends Fragment {

    private DatabaseReference databaseReference;
    private List<User> mUsers;

    private List<Upload> mUploads;
    private RecyclerView recyclerView;
    private VisitPostAdapter selfPostAdapter;
    String profieid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visitpost, container, false);



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


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profieid = sharedPreferences.getString("uid", "none");

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









        return view;

    }
}