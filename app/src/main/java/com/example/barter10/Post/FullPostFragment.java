package com.example.barter10.Post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.example.barter10.Profile.visitprofile;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FullPostFragment extends Fragment {

    private ImageView btnBack;
    private TextView itemName;
    private TextView userName;
    private TextView location;
    private TextView itemCondition;
    private  TextView itemDetails;
    private TextView estimatedValue;
    private  TextView preference;
    private TextView rating,timer;
    private Button takeOffer,visitAuctioneer,visitBidder;

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
    ArrayList<Offer> list;

    private ImageView unpin;
    private String takeOfferValue;

    //For retrieving information of Pinned Post//
    private CardView pinPost;
    private TextView p_username,p_rating,p_itemName,p_itemDetails,p_condition,p_value,p_loc;
    private CircleImageView p_profile;
    private ImageSlider p_img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_post, container, false);
        //Hide button navigation
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
        meowBottomNavigation.setVisibility(View.GONE);


        visitAuctioneer = view.findViewById(R.id.visitAuctioneer);


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
        rating = view.findViewById(R.id.rating);
        postImage = view.findViewById(R.id.image_slider);

        profileImg = view.findViewById(R.id.userProfile);
        btnOffer = view.findViewById(R.id.btnOffer);
        takeOffer = view.findViewById(R.id.btnConfirmPin);
        timer = view.findViewById(R.id.timer);

        //For retrieving information of Pinned Post//
        pinPost = view.findViewById(R.id.pinPost);

        unpin = view.findViewById(R.id.o_subMenuUnpin);
        p_username = view.findViewById(R.id.p_username);
        p_profile = view.findViewById(R.id.p_userProfile);
        p_rating = view.findViewById(R.id.p_rating);
        p_img = view.findViewById(R.id.p_image_slider);
        p_itemName = view.findViewById(R.id.p_itemName);
        p_itemDetails = view.findViewById(R.id.p_itemDetails);
        p_condition = view.findViewById(R.id.p_itemCondition);
        p_value = view.findViewById(R.id.p_itemValue);
        p_loc = view.findViewById(R.id.p_location);
        visitBidder = view.findViewById(R.id.p_visitOfferer);


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
        pinReference = FirebaseDatabase.getInstance().getReference("PinnedPost").child(itemKey);


        //Reclyer View for Offers//
        rv_FullPost = view.findViewById(R.id.rv_Offer);
        offerReference = FirebaseDatabase.getInstance().getReference("Offer").child(itemKey);
        rv_FullPost.setHasFixedSize(true);
        rv_FullPost.setLayoutManager(new LinearLayoutManager(getContext()));

        list =  new ArrayList<>();
        offerListAdapter =  new OfferListAdapter(getContext(), list);
        rv_FullPost.setAdapter(offerListAdapter);







        //Show pinned post//
        pinReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                showPinnedPost(list,uid,takeOffer,snapshot,pinPost,unpin,itemKey);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        //Show Offers//
        offerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
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



        visitAuctioneer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = view.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("uid", uid);
                editor.apply();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                meowBottomNavigation.setVisibility(View.GONE);

                Fragment fragment = new visitprofile();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();
            }
        });








        //Check if the post is already taken//
        DatabaseReference getTakeOffer = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(itemKey);
        getTakeOffer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                takeOfferValue = snapshot.child("takeOffer").getValue().toString();

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
            String offerKey = offer.getOfferKey();
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

                    Upload upload = snapshot.getValue(Upload.class);

                    String uName  = snapshot.child("userName").getValue().toString();
                    String profileUrl = snapshot.child("profileUrl").getValue().toString();
                    String postImg  = snapshot.child("imageUrl").getValue().toString();

                    //For Offeree//
                    String status = "null";
                    DatabaseReference setTrade = FirebaseDatabase.getInstance().getReference("Trade").child(posterId);
                    Trade trade = new Trade(posterId,uName,profileUrl,postImg,offererId,offererName,offererProfile,offerImg,postKey,status,offerKey);
                    setTrade.child(postKey).setValue(trade);

                    //For Offerer//
                    DatabaseReference setTrade2 = FirebaseDatabase.getInstance().getReference("Trade").child(offererId);
                    Trade trade2 = new Trade(posterId,uName,profileUrl,postImg,offererId,offererName,offererProfile,offerImg,postKey,status,offerKey);
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


                    DatabaseReference createClosedBid = FirebaseDatabase.getInstance().getReference("ClosedBid").child(itemKey);
                    createClosedBid.setValue(upload);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



            //Go home//
            Intent intent = new Intent(getActivity(), Home.class);
            startActivity(intent);

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

    private void showPinnedPost(ArrayList<Offer> list, String uid, Button takeOffer, DataSnapshot snapshot, CardView pinPost, ImageView unpin, String itemKey) {
        list.clear();
        if(snapshot.exists()){
            Offer offer = snapshot.getValue(Offer.class);
            String offerKey = offer.getOfferKey();
            String postKey = offer.getPostKey();
            String posterId = offer.getPosterId();
            String offerId = offer.getUid();
            Boolean pinValue = offer.getPinValue();




            //Check if you are the offeree//
            //If you are the offeree the take offer button will visible//
            if (FirebaseAuth.getInstance().getUid().equals(uid)){

                //If you are the offeree the take offer button will visible//
                if(pinValue.equals(true)){
                    takeOffer.setVisibility(View.VISIBLE);
                    pinPost.setVisibility(View.VISIBLE);
                    p_username.setText(offer.getUserName());

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

                    visitBidder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences.Editor editor = v.getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                            editor.putString("uid", offerId);
                            editor.apply();

                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                            MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                            meowBottomNavigation.setVisibility(View.GONE);

                            Fragment fragment = new visitprofile();
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();
                        }
                    });

                    p_img.setImageList(slideModels, ScaleTypes.FIT);
                    p_rating.setText(offer.getRating());
                    p_itemName.setText("Item Name : "+offer.getItemName());
                    p_itemDetails.setText("Item Details : "+offer.getItemDetails());
                    p_condition.setText("Item Condition : "+offer.getItemCondition());
                    p_value.setText("Item Value : "+offer.getItemValue());
                    p_loc.setText("Location : "+offer.getLocation());

                    unpin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            DatabaseReference OfferReference = FirebaseDatabase.getInstance().getReference("Offer").child(itemKey);
                            OfferReference.child(offerKey).setValue(offer);

                            //Updating pin Value//
                            HashMap changePinValue = new HashMap();
                            changePinValue.put("pinValue", false);
                            OfferReference.child(offerKey).updateChildren(changePinValue);

                            pinReference.removeValue();

                            pinPost.setVisibility(View.GONE);

                            //Setting value of itemPin to false//
                            DatabaseReference approvedPost = FirebaseDatabase.getInstance().getReference("ApprovedPost").child(postKey);
                            HashMap updatePin = new HashMap();
                            updatePin.put("itemPin", false);
                            approvedPost.updateChildren(updatePin);


                            //Refresh Full Post Page//
                            AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            Fragment fragment = new FullPostFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("ItemKey", postKey);
                            bundle.putString("uId", posterId);
                            fragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up).replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();


                        }
                    });
                }
                else {
                    takeOffer.setVisibility(View.GONE);
                    pinPost.setVisibility(View.GONE);
                }
            }
            //If you are the offerer the take offer button will gone//
            else {
                takeOffer.setVisibility(View.GONE);
                pinPost.setVisibility(View.VISIBLE);
                unpin.setClickable(false);
                showPin(offer);
            }
        }
        offerListAdapter.notifyDataSetChanged();
    }

    private void showPin(Offer offer) {
        p_username.setText(offer.getUserName());

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

        p_img.setImageList(slideModels, ScaleTypes.FIT);
        p_rating.setText(offer.getRating());
        p_itemName.setText("Item Name : "+offer.getItemName());
        p_itemDetails.setText("Item Details : "+offer.getItemDetails());
        p_condition.setText("Item Condition : "+offer.getItemCondition());
        p_value.setText("Item Value : "+offer.getItemValue());
        p_loc.setText("Location : "+offer.getLocation());


    }

    private void showFullPost(DataSnapshot snapshot) {


        Upload upload = snapshot.getValue(Upload.class);


        timer.setText(upload.getTimer());
        itemName.setText(upload.getItemName());
        rating.setText(upload.getRating());
        userName.setText(upload.getUserName());
        location.setText("Location : "+upload.getLocation());
        itemCondition.setText("Condition : "+upload.getItemCondition());
        itemDetails.setText("Details : "+upload.getItemDetails());
        estimatedValue.setText("Estimated Value  : "+upload.getItemValue());
        preference.setText("Preferences : "+upload.getItemPreference());


        List<SlideModel> slideModels = new ArrayList<>();

        //multiple images
        String rep = upload.getImageUrl().replace("]","");
        String rep1 = rep.replace("[","");
        String rep2 = rep1.replace(" ","");
        String[] pictures = rep2.split(",");




        for (int i =0 ; i < pictures.length ; i++){


            slideModels.add(new SlideModel(pictures[i], "", ScaleTypes.FIT));

        }


        postImage.setImageList(slideModels, ScaleTypes.FIT);

        Picasso.get()
                .load(upload.getProfileUrl())
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