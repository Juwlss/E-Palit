package com.example.barter10.Post;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Adapter.OfferListAdapter;
import com.example.barter10.Home;
import com.example.barter10.Model.Offer;
import com.example.barter10.Model.Trade;
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
import java.util.HashMap;
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
    private Button takeOffer;

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

        //Get ID in XML//
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
        takeOffer = view.findViewById(R.id.btnConfirmPin);

        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(itemKey);
//        storage
        firebaseStorage = FirebaseStorage.getInstance();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showFullPost(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Reclyer View for Pin Post//
        rv_Pin = view.findViewById(R.id.rv_pin);
        pinReference = FirebaseDatabase.getInstance().getReference("Offer").child("PinnedPost").child(itemKey);
        rv_Pin.setHasFixedSize(true);
        rv_Pin.setLayoutManager(new LinearLayoutManager(getContext()));

        //Reclyer View for Offers//
        rv_FullPost = view.findViewById(R.id.rv_Offer);
        offerReference = FirebaseDatabase.getInstance().getReference("Offer").child(itemKey);
        rv_FullPost.setHasFixedSize(true);
        rv_FullPost.setLayoutManager(new LinearLayoutManager(getContext()));

        list =  new ArrayList<>();
        offerListAdapter =  new OfferListAdapter(getContext(), list);
        rv_FullPost.setAdapter(offerListAdapter);
        rv_Pin.setAdapter(offerListAdapter);






        //Show pinned post//
        pinReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showPinnedPost(list,uid,takeOffer,snapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Show Offers//
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









        //Check if the post is already taken//
        DatabaseReference getTakeOffer = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(itemKey);
        getTakeOffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String takeOfferValue = snapshot.child("takeOffer").getValue().toString();

                //If False they can offer on post//
                if(takeOfferValue.equals("false")){
                    editOfferAction(itemKey,uid);
                }
                //If true then the bidding is done//
                else {
                    btnOffer.setClickable(false);
                    takeOffer.setClickable(false);

                    //set color//
                    btnOffer.setBackgroundColor(Color.LTGRAY);
                    takeOffer.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Take offer methods//
        takeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        takeOfferMethod(snapshot,uid,itemKey);

                        Toast.makeText(activity, ""+itemKey, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

    private void takeOfferMethod(DataSnapshot snapshot, String uid, String itemKey) {
        if(snapshot.exists()){
            Offer offer = snapshot.getValue(Offer.class);

            //Retrieve Oferrer Information//
            String postKey = offer.getPostKey();
            String offererId = offer.getUid();
            String offererName = offer.getUserName();
            String offererProfile = offer.getProfileUrl();
            String offerImg = offer.getImageUrl();
            String posterId = offer.getPosterId();

            //Get the offeree information//
            DatabaseReference offereeReference = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(postKey);
            offereeReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String uName  = snapshot.child("userName").getValue().toString();
                    String profileUrl = snapshot.child("profileUrl").getValue().toString();
                    String postImg  = snapshot.child("imageUrl").getValue().toString();

                    //For Offeree//
                    String status = "null";
                    DatabaseReference setTrade = FirebaseDatabase.getInstance().getReference("Trade").child(posterId);
                    Trade trade = new Trade(posterId,uName,profileUrl,postImg,offererId,offererName,offererProfile,offerImg,postKey,status);
                    setTrade.child(postKey).setValue(trade);

                    //For Offerer//
                    DatabaseReference setTrade2 = FirebaseDatabase.getInstance().getReference("Trade").child(offererId);
                    Trade trade2 = new Trade(posterId,uName,profileUrl,postImg,offererId,offererName,offererProfile,offerImg,postKey,status);
                    setTrade2.child(postKey).setValue(trade2);


                    //Creating reference to determine if offerer and offeree confirm or cancel on post//
                    DatabaseReference tradeStatus1 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(posterId);
                    tradeStatus1.child(postKey).child("offeree").setValue("null");
                    tradeStatus1.child(postKey).child("offerer").setValue("null");

                    DatabaseReference tradeStatus2 = FirebaseDatabase.getInstance().getReference("TradeStatus").child(offererId);
                    tradeStatus2.child(postKey).child("offeree").setValue("null");
                    tradeStatus2.child(postKey).child("offerer").setValue("null");



                    Toast.makeText(getContext(), "Offer Taked", Toast.LENGTH_SHORT).show();


                    //Updating the take offer value to disable offering//
                    HashMap hashMap = new HashMap();
                    hashMap.put("takeOffer", true);

                    offereeReference.updateChildren(hashMap);


                    //Go home//
                    Intent intent = new Intent(getActivity(), Home.class);
                    startActivity(intent);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    //Edit or Offer in Post//
    private void editOfferAction(String itemKey, String uid) {

        //Edit Post//
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

        //Offer in post//
        else {
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

    }

    private void showPinnedPost(ArrayList<Offer> list, String uid, Button takeOffer, DataSnapshot snapshot) {
        list.clear();
        if(snapshot.exists()){
            Offer viewOffers = snapshot.getValue(Offer.class);
            list.add(viewOffers);

            Boolean pinValue = viewOffers.getPinValue();


            //Check if you are the offeree//
            //If you are the offeree the take offer button will visible//
            if (FirebaseAuth.getInstance().getUid().equals(uid)){

                //If you are the offeree the take offer button will visible//
                if(pinValue.equals(true)){
                    takeOffer.setVisibility(View.VISIBLE);
                }
                else {
                    takeOffer.setVisibility(View.GONE);
                }
            }
            //If you are the offerer the take offer button will gone//
            else {
                takeOffer.setVisibility(View.GONE);
            }
        }
        offerListAdapter.notifyDataSetChanged();
    }

    private void showFullPost(DataSnapshot snapshot) {

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

    private void EditPost(String itemKey, String uid) {
        Fragment mFragment = new UpdatePostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("passKey", itemKey);
        bundle.putString("passUid", uid);
        mFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();

    }

}