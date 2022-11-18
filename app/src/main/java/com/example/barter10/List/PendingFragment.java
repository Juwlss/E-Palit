package com.example.barter10.List;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barter10.Adapter.PendingAdapter;
import com.example.barter10.Model.Trade;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingFragment extends Fragment {
    private ArrayList<Trade> tradeList;
    private DatabaseReference tradeReference;
    private RecyclerView rv_Pending;
    private PendingAdapter pendingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        //Getting ID in XML layout//
        rv_Pending = view.findViewById(R.id.trade_rv);


        //List Setup & Adapter//
        tradeList = new ArrayList<>();
        pendingAdapter = new PendingAdapter(getContext(), tradeList );

        //Recycler View Setup//
        rv_Pending.setHasFixedSize(true);
        rv_Pending.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_Pending.setAdapter(pendingAdapter);

        //Displaying Data in Firebase//
        tradeReference = FirebaseDatabase.getInstance().getReference("Trade").child(FirebaseAuth.getInstance().getUid());

        tradeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tradeList.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    Trade trade = snap.getValue(Trade.class);
                    tradeList.add(trade);
                }
                pendingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }


}