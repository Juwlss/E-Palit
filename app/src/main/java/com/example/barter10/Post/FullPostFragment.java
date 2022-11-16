package com.example.barter10.Post;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Adapter.OfferListAdapter;
import com.example.barter10.Home;
import com.example.barter10.Model.Offer;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.viewOffers;
import com.example.barter10.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullPostFragment extends Fragment {

    private ImageView btnBack;
    private TextView itemName;
    private TextView userName;
    private TextView location;
    private TextView itemCondition;
    private  TextView itemDetails;
    private TextView estimatedValue;
    private  TextView preference;

    private ImageSlider postImage;

    private ImageView profileImg;
    private FirebaseAuth firebaseAuth;
    private ExtendedFloatingActionButton btnOffer;
    private FirebaseStorage firebaseStorage;


    DatabaseReference databaseReference;
    DatabaseReference offerReference;
    DatabaseReference pinReference;
    RecyclerView rv_FullPost;
    OfferListAdapter offerListAdapter;
    RecyclerView rv_Pin;
    ArrayList<Offer> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_post, container, false);
        //Hide button navigation
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
        meowBottomNavigation.setVisibility(View.GONE);



        btnBack = view.findViewById(R.id.btnPostBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });

        String itemKey = getArguments().getString("ItemKey");
        String uid = getArguments().getString("uId");
        itemName = view.findViewById(R.id.itemName);
        userName = view.findViewById(R.id.username);
        location = view.findViewById(R.id.location);
        itemCondition = view.findViewById(R.id.itemCondition);
        itemDetails = view.findViewById(R.id.itemDetails);
        estimatedValue = view.findViewById(R.id.itemValue);
        preference = view.findViewById(R.id.itemPref);

        postImage = view.findViewById(R.id.image_slider);

        profileImg = view.findViewById(R.id.userProfile);
        btnOffer = view.findViewById(R.id.btnOffer);

        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(itemKey);
//        storage
        firebaseStorage = FirebaseStorage.getInstance();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String iName  = snapshot.child("itemName").getValue().toString();
                String uName  = snapshot.child("userName").getValue().toString();
                String profileUrl = snapshot.child("profileUrl").getValue().toString();
                String loc  = snapshot.child("location").getValue().toString();

                String img  = snapshot.child("imageUrl").getValue().toString();

                String iCondition  = snapshot.child("itemCondition").getValue().toString();
                String iDetails  = snapshot.child("itemDetails").getValue().toString();
                String value  = snapshot.child("itemValue").getValue().toString();
                String pref  = snapshot.child("itemPreference").getValue().toString();

                itemName.setText(iName);
                userName.setText(uName);
                location.setText("Location : "+loc);
                itemCondition.setText("Condition : "+iCondition);
                itemDetails.setText("Details : "+iDetails);
                estimatedValue.setText("Estimated Value  : "+value);
                preference.setText("Preferences : "+pref);


                List<SlideModel> slideModels = new ArrayList<>();

                //multiple images
                String rep = img.replace("]","");
                String rep1 = rep.replace("[","");
                String rep2 = rep1.replace(" ","");
                String[] pictures = rep2.split(",");




                for (int i =0 ; i < pictures.length ; i++){


                    slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

                }


                postImage.setImageList(slideModels, ScaleTypes.FIT);

                Picasso.get()
                        .load(profileUrl)
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .fit()
                        .into(profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rv_Pin = view.findViewById(R.id.rv_pin);
        pinReference = FirebaseDatabase.getInstance().getReference("Offer").child("PinnedPost").child(itemKey);
        rv_Pin.setHasFixedSize(true);
        rv_Pin.setLayoutManager(new LinearLayoutManager(getContext()));


        rv_FullPost = view.findViewById(R.id.rv_Offer);
        offerReference = FirebaseDatabase.getInstance().getReference("Offer").child(itemKey);
        rv_FullPost.setHasFixedSize(true);
        rv_FullPost.setLayoutManager(new LinearLayoutManager(getContext()));

        list =  new ArrayList<>();
        offerListAdapter =  new OfferListAdapter(getContext(), list);
        rv_FullPost.setAdapter(offerListAdapter);
        rv_Pin.setAdapter(offerListAdapter);


        pinReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    Offer viewOffers = snapshot.getValue(Offer.class);
                    list.add(viewOffers);
                }
                offerListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Offer viewOffers = dataSnapshot.getValue(Offer.class);
                    list.add(viewOffers);
                }
                offerListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Edit Post
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser().getUid().equals(uid)){
            btnOffer.setText("Edit");
            btnOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditPost(itemKey,uid);
                }
            });
        }
        else {
            //Offer post
            btnOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment mFragment = new OfferItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("passKey", itemKey);
                    bundle.putString("passUid", uid);
                    mFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();
                }
            });
        }



        return view;
    }

    private void EditPost(String itemKey, String uid) {
        Fragment mFragment = new UpdatePostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("passKey", itemKey);
        bundle.putString("passUid", uid);
        mFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();

    }

}