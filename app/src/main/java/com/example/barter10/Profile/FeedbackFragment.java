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
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Adapter.FeedbackAdapter;
import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Category.GadgetFragment;
import com.example.barter10.Model.Feedback;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FeedbackFragment extends Fragment {

    private List<Feedback> mFeedbacks;
    private FeedbackAdapter feedbackAdapter;
    private RecyclerView recyclerView;

    String profieid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = view.findViewById(R.id.feedback_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Displaying Feedback
        mFeedbacks = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(getContext(), mFeedbacks);
        recyclerView.setAdapter(feedbackAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profieid = sharedPreferences.getString("uid", "none");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Feedbacks").child(profieid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fetching from firebase to display
                mFeedbacks.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    Feedback feedback = postSnapshot.getValue(Feedback.class);
                    mFeedbacks.add(feedback);

                }
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}