package com.example.barter10.List;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.FinishedAdapter;
import com.example.barter10.Adapter.FollowAdapter;
import com.example.barter10.Model.Pending;
import com.example.barter10.R;

import java.util.ArrayList;
import java.util.List;

public class FinishedFragment extends Fragment {

    private FinishedAdapter finishedAdapter;
    private RecyclerView recyclerView;

    private List<Pending> mTrade;


    int[] offereeImages = {R.drawable.vincentuser,R.drawable.kyouser,R.drawable.daleuser};
    int[] offererImages = {R.drawable.paulouser,R.drawable.juls,R.drawable.kyouser};

    int[] offereeTrade = {R.drawable.shoe, R.drawable.watch, R.drawable.shoe};
    int[] offererTrade = {R.drawable.watch, R.drawable.shoe, R.drawable.shoe};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished, container, false);



        recyclerView = view. findViewById(R.id.finished_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTrade = new ArrayList<>();

        finishedAdapter = new FinishedAdapter(getContext(), mTrade);
        recyclerView.setAdapter(finishedAdapter);




        setupPendingModel();





        return view;
    }


    private void setupPendingModel(){
        String[] offereeNames = getResources().getStringArray(R.array.offeree);
        String[] offererNames = getResources().getStringArray(R.array.offerer);


        for(int i =0;  i<offereeNames.length; i++){
            mTrade.add(new Pending(offereeNames[i], offererNames[i],
                    offereeImages[i], offererImages[i], offereeTrade[i], offererTrade[i]));
        }
    }
}