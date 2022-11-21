package com.example.barter10.List;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barter10.Adapter.FinishedAdapter;
import com.example.barter10.Adapter.FollowAdapter;
import com.example.barter10.Model.Trade;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FinishedFragment extends Fragment {

    private ArrayList<Trade> tradeList;
    private RecyclerView rv_finished;
    private FinishedAdapter finishedAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished, container, false);

        //Getting ID in XML layout//
        rv_finished = view.findViewById(R.id.finished_rv);

        //List Setup & Adapter//
        tradeList = new ArrayList<>();
        finishedAdapter = new FinishedAdapter(getContext(), tradeList );

        //Recycler View Setup//
        rv_finished.setHasFixedSize(true);
        rv_finished.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_finished.setAdapter(finishedAdapter);


        DatabaseReference trade = FirebaseDatabase.getInstance().getReference("Finished").child(FirebaseAuth.getInstance().getUid());

        trade.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tradeList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    String statusValue = snap.child("status").getValue().toString();

                    if(statusValue.equals("null")){
//                        Toast.makeText(getContext(), "Wait for both approval", Toast.LENGTH_SHORT).show();
                    }
                    else if (statusValue.equals("true")){
                        Trade trade = snap.getValue(Trade.class);
                        tradeList.add(trade);
                    }
                    else if(statusValue.equals("false")){
                        Trade trade = snap.getValue(Trade.class);
                        tradeList.add(trade);
                    }
                }
                finishedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

}