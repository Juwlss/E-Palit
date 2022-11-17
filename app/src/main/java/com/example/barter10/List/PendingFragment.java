package com.example.barter10.List;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.barter10.Adapter.PendingAdapter;
import com.example.barter10.Model.Pending;
import com.example.barter10.R;

import java.util.ArrayList;

public class PendingFragment extends Fragment {
    private Dialog dialog;
    private RatingBar ratingBar;
    private TextView confirmRate;
    private Button confirmTrade;


    ArrayList<Pending> pendingArrayList = new ArrayList<>();

    int[] offereeImages = {R.drawable.vincentuser,R.drawable.kyouser,R.drawable.daleuser};
    int[] offererImages = {R.drawable.paulouser,R.drawable.juls,R.drawable.kyouser};

    int[] offereeTrade = {R.drawable.shoe, R.drawable.watch, R.drawable.shoe};
    int[] offererTrade = {R.drawable.watch, R.drawable.shoe, R.drawable.shoe};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.trade_rv);
        //confirm
        confirmTrade = view.findViewById(R.id.trade_confirm);
//        ratingBar = view.findViewById(R.id.rating_bar);
//        confirmRate = view.findViewById(R.id.confirm_rate);



        setupPendingModel();
        PendingAdapter adapter = new PendingAdapter(getContext(), pendingArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return view;
    }

    private void setupPendingModel(){
        String[] offereeNames = getResources().getStringArray(R.array.offeree);
        String[] offererNames = getResources().getStringArray(R.array.offerer);


        for(int i =0;  i<offereeNames.length; i++){
            pendingArrayList.add(new Pending(offereeNames[i], offererNames[i],
                    offereeImages[i], offererImages[i], offereeTrade[i], offererTrade[i]));
        }
    }


    private void setConfirmTrade(){
        confirmTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.popup_rating);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

    }
}