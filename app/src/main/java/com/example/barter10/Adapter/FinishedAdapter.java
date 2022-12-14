package com.example.barter10.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Model.Trade;
import com.example.barter10.Profile.visitprofile;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.MyViewHolder> {

    Context context;
    List<Trade> tradeList;

    public FinishedAdapter(Context context, List<Trade> tradeList){
        this.context = context;
        this.tradeList = tradeList;
    }

    @NonNull
    @Override
    public FinishedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(context).inflate(R.layout.finished_layout, parent, false);
        return new FinishedAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedAdapter.MyViewHolder holder, int position) {
        Trade trade = tradeList.get(position);

        //Offere//
        Picasso.get()
                .load(trade.getOffereeProfile())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.f_offereeProfile);

        holder.f_offereeName.setText(trade.getOffereeName());

        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = trade.getOffereeImg().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");




        for (int i =0 ; i < pictures.length ; i++){

            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        holder.f_offereeImg.setImageList(slideModels, ScaleTypes.FIT);



        //Offerer//
        Picasso.get()
                .load(trade.getOffererProfile())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(holder.f_offererProfile);

        holder.f_offererName.setText(trade.getOffererName());

        List<SlideModel> slideModels2 = new ArrayList<>();
        //multiple images
        String rep3 = trade.getOffererImg().replace("]","");
        String rep4 = rep3.replace("[","");
        String rep5 = rep4.replace(" ","");
        String[] pictures2 = rep5.split(",");




        for (int i =0 ; i < pictures2.length ; i++){

            slideModels2.add(new SlideModel(pictures2[i], "", ScaleTypes.FIT));

        }


        holder.f_offererImg.setImageList(slideModels2, ScaleTypes.FIT);

        String postKey = trade.getPostKey();
        String offereeId = trade.getOffereeId();
        String offererId = trade.getOffererId();

        String tradeStats = trade.getStatus();
        if (tradeStats.equals("true")){

            //Remove in pending//
            DatabaseReference tradeReference1 = FirebaseDatabase.getInstance().getReference("Trade").child(offereeId).child(postKey);
            DatabaseReference tradeReference2 = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);

            tradeReference1.removeValue();
            tradeReference2.removeValue();


        }
        else if (tradeStats.equals("false")) {
            holder.rl_footer.setBackgroundColor(Color.parseColor("#EF4565"));
            holder.tradeStatus.setText("BARTER FAILED");

            //Remove in pending//
            DatabaseReference tradeReference1 = FirebaseDatabase.getInstance().getReference("Trade").child(offereeId).child(postKey);
            DatabaseReference tradeReference2 = FirebaseDatabase.getInstance().getReference("Trade").child(offererId).child(postKey);

            tradeReference1.removeValue();
            tradeReference2.removeValue();
        }


        holder.visitAuctioneer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = v.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("uid", offereeId);
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                meowBottomNavigation.setVisibility(View.GONE);

                Fragment fragment = new visitprofile();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();
            }
        });

        holder.visitBidder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = v.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("uid", offererId);
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                meowBottomNavigation.setVisibility(View.GONE);

                Fragment fragment = new visitprofile();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView f_offereeProfile, f_offererProfile;
        ImageSlider f_offereeImg, f_offererImg;
        TextView f_offereeName, f_offererName,tradeStatus;
        RelativeLayout rl_footer;
        Button visitAuctioneer, visitBidder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //userimage
            f_offereeProfile = itemView.findViewById(R.id.f_offeree_user);
            f_offererProfile = itemView.findViewById(R.id.f_offerer_user);
            //trade item
            f_offereeImg = itemView.findViewById(R.id.f_offeree_image_slider);
            f_offererImg = itemView.findViewById(R.id.f_offerer_image_slider);
            //username
            f_offereeName = itemView.findViewById(R.id.f_offeree_name);
            f_offererName = itemView.findViewById(R.id.f_offerer_name);

            rl_footer = itemView.findViewById(R.id.rl_footer);
            tradeStatus = itemView.findViewById(R.id.lblTradeStatus);

            visitAuctioneer = itemView.findViewById(R.id.visitAuctioneer);
            visitBidder = (Button) itemView.findViewById(R.id.visitBidder);

        }
    }
}
