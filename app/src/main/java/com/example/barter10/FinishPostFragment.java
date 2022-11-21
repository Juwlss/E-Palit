package com.example.barter10;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.barter10.Adapter.FinishOfferAdapter;
import com.example.barter10.Model.Offer;
import com.example.barter10.Model.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FinishPostFragment extends Fragment implements View.OnClickListener {

    private ImageView btnBack;
    private CircleImageView v_profile;
    private ImageSlider v_postImg;
    private TextView v_itemName,v_userName,v_rating,v_location,v_condition,v_details,v_value,v_preference;

    private CircleImageView p_profile;
    private ImageSlider p_postImg;
    private TextView p_itemName,p_userName,p_rating,p_location,p_condition,p_details,p_value,p_preference;

    private RecyclerView rv_offers;
    private FinishOfferAdapter finishedOfferAdapter;
    private List<Offer> offerList;


    DatabaseReference closeBidReference,pinPostReference,offerReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish_post, container, false);

        String postKey = getArguments().getString("ItemKey");

        //Getting ID in XML //
        btnBack = view.findViewById(R.id.v_btnPostBack);
        v_profile = view.findViewById(R.id.v_userProfile);
        v_postImg = view.findViewById(R.id.v_image_slider);
        v_itemName = view.findViewById(R.id.v_itemName);
        v_userName = view.findViewById(R.id.v_username);
        v_rating = view.findViewById(R.id.v_rating);
        v_location = view.findViewById(R.id.v_location);
        v_condition = view.findViewById(R.id.v_itemCondition);
        v_details = view.findViewById(R.id.v_itemDetails);
        v_value = view.findViewById(R.id.v_itemValue);
        v_preference = view.findViewById(R.id.v_itemPref);

        //For Pin Post//
        p_profile = view.findViewById(R.id.p_userProfile);
        p_userName = view.findViewById(R.id.p_username);
        p_rating = view.findViewById(R.id.p_rating);
        p_postImg = view.findViewById(R.id.p_image_slider);
        p_itemName = view.findViewById(R.id.p_itemName);
        p_details = view.findViewById(R.id.p_itemDetails);
        p_condition = view.findViewById(R.id.p_itemCondition);
        p_value = view.findViewById(R.id.p_itemValue);
        p_location = view.findViewById(R.id.p_location);

        //Reclyer View for Offers//
        rv_offers = view.findViewById(R.id.v_rv_Offer);
        offerReference = FirebaseDatabase.getInstance().getReference("Offer").child(postKey);
        rv_offers.setHasFixedSize(true);
        rv_offers.setLayoutManager(new LinearLayoutManager(getContext()));

        offerList =  new ArrayList<>();
        finishedOfferAdapter = new FinishOfferAdapter(getContext(), offerList);
        rv_offers.setAdapter(finishedOfferAdapter);

        //Methods//
        btnBack.setOnClickListener(this);


        //Get Finished Post//
        closeBidReference = FirebaseDatabase.getInstance().getReference("ClosedBid").child(postKey);
        closeBidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showFinishPost(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        pinPostReference = FirebaseDatabase.getInstance().getReference("PinnedPost").child(postKey);
        pinPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showFinishPinPost(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //        Show Offers//
        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                offerList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Offer viewOffers = dataSnapshot.getValue(Offer.class);
                    offerList.add(viewOffers);
                }
                finishedOfferAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



    private void showFinishPost(DataSnapshot snapshot) {
        Upload upload = snapshot.getValue(Upload.class);

        v_userName.setText(upload.getUserName());
        v_itemName.setText(upload.getItemName());

        Picasso.get()
                .load(upload.getProfileUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(v_profile);
        v_rating.setText(upload.getRating());

        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = upload.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");

        for (int i =0 ; i < pictures.length ; i++){
            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }
        v_postImg.setImageList(slideModels, ScaleTypes.FIT);

        v_location.setText("location : "+upload.getLocation());
        v_condition.setText("Item Condition : "+upload.getItemCondition());
        v_details.setText("Item Details : "+upload.getItemDetails());
        v_value.setText("Item Value : "+upload.getItemValue());
        v_preference.setText("Item Preferences : "+upload.getItemPreference());


    }

    private void showFinishPinPost(DataSnapshot snapshot) {
        Offer offer = snapshot.getValue(Offer.class);
        p_userName.setText(offer.getUserName());
        p_rating.setText(offer.getRating());
        Picasso.get()
                .load(offer.getProfileUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .fit()
                .into(p_profile);


        List<SlideModel> slideModels = new ArrayList<>();
        //multiple images
        String rep = offer.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");

        for (int i =0 ; i < pictures.length ; i++){
            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }
        p_postImg.setImageList(slideModels, ScaleTypes.FIT);
        p_itemName.setText("Item Name : "+offer.getItemName());
        p_details.setText("Item Details : "+offer.getItemDetails());
        p_condition.setText("Item Condition : "+offer.getItemCondition());
        p_value.setText("Item Value : "+offer.getItemValue());
        p_location.setText("location : "+offer.getLocation());
    }


    @Override
    public void onClick(View view) {
        //Go home//
        Intent intent = new Intent(getActivity(), Home.class);
        startActivity(intent);
    }
}