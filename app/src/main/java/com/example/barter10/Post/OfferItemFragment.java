package com.example.barter10.Post;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.barter10.Adapter.UploadListAdapter;
import com.example.barter10.Model.Offer;
import com.example.barter10.Model.User;
import com.example.barter10.Post.FullPostFragment;
import com.example.barter10.R;
import com.example.barter10.Upload.activityUpload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class OfferItemFragment extends Fragment {

    private Button offer;
    private static final int REQUEST_CODE_IMAGE = 101;
    private UploadListAdapter uploadListAdapter;
    RecyclerView recyclerView;
    Uri imageuri;
    ArrayList<String> urlStrings;
    ProgressDialog progressDialog;
    ArrayList<Uri> itemList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private EditText uploadName, uploadLocation, uploadDetails, uploadCondition, uploadValue;
    private int upload_count = 0;
    private ImageView btnGoBack;
    private String getKey,getUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_item, container, false);

        offer = view.findViewById(R.id.btnOfferItem);
        recyclerView = view.findViewById(R.id.upload_list);
        uploadListAdapter = new UploadListAdapter(itemList);


        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_IMAGE);

        }

        Button btnImg = view.findViewById(R.id.btn_img);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        getKey = getArguments().getString("passKey");//For Bundle
        getUid = getArguments().getString("passUid");//For Bundle


        //getting details in edit text
        uploadName = view.findViewById(R.id.txtItemName);
        uploadLocation = view.findViewById(R.id.txtLocation);
        uploadDetails = view.findViewById(R.id.txtDetails);
        uploadCondition = view.findViewById(R.id.txtCondition);
        uploadValue = view.findViewById(R.id.txtEstimatedValue);
        btnGoBack = view.findViewById(R.id.btnOfferBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mFragment = new FullPostFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ItemKey", getKey);
                bundle.putString("uId", getUid);
                mFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();
            }
        });





        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upload();

            }
        });


        return view;
    }



    private void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && null != data){

            if (data.getClipData() != null){
                int x = data.getClipData().getItemCount();
                for (int i=0; i<x; i++){

                    if (itemList.size() != 5){
                        imageuri = data.getClipData().getItemAt(i).getUri();
                        itemList.add(imageuri);

                        if(itemList.size() > 1){
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            recyclerView.setAdapter(uploadListAdapter);
                        }

                    }else if (itemList.size() == 5){

                        Toast.makeText(getContext(), "You can post only 5 images", Toast.LENGTH_SHORT).show();
                    }
                }

            }else{
                if (itemList.size() != 5){
                    imageuri = data.getData();
                    itemList.add(imageuri);

                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

                    if(itemList.size() > 1){
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

                    }
                    recyclerView.setAdapter(uploadListAdapter);
                    uploadListAdapter.notifyDataSetChanged();

                }else if (itemList.size() == 5){

                    Toast.makeText(getContext(), "You can post only 5 images", Toast.LENGTH_SHORT).show();
                }
            }

        }else{
            Toast.makeText(getContext(), "Please pick image", Toast.LENGTH_SHORT).show();
        }
    }


    private void upload() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Post...");
        // generating key
        databaseReference = FirebaseDatabase.getInstance().getReference("Offer");
        urlStrings = new ArrayList<>();
        String itemKey = databaseReference.push().getKey();

        //referring to storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Offer");
        //getting values in edit text
        String postId =itemKey;
        String itemName = uploadName.getText().toString().trim();
        String itemLocation = uploadLocation.getText().toString().trim();
        String itemDetails = uploadDetails.getText().toString().trim();
        String itemCondition = uploadCondition.getText().toString().trim();
        String itemValue = uploadValue.getText().toString().trim();

        if (itemList.isEmpty() || TextUtils.isEmpty(itemName) || TextUtils.isEmpty(itemDetails)
                || TextUtils.isEmpty(itemCondition) || TextUtils.isEmpty(itemValue) ){
            Toast.makeText(getContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
        }
        else {

            for (upload_count=0; upload_count<itemList.size(); upload_count++){
                Uri IndividualImage = itemList.get(upload_count);
                StorageReference ImageName = storageReference.child(IndividualImage.getLastPathSegment());

                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlStrings.add(String.valueOf(uri));
                                if (urlStrings.size() == itemList.size()){
                                    //displaying pictures to upload
                                    storeLink(urlStrings);


                                    DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");

                                    postReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            String name;
//                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                                String copyuser = firebaseAuth.getCurrentUser().getEmail();
//                                                name = dataSnapshot.child("useremail").getValue().toString();
//                                                if(copyuser.equals(name)){
//                                                    //getting the username of uploader
//                                                    name = dataSnapshot.child("username").getValue().toString();
//
//                                                    //uploading to firebase
//                                                    Offer offer = new Offer(FirebaseAuth.getInstance().getUid(),uri.toString(),name,itemLocation, itemName, itemCondition, itemDetails,itemValue);
//                                                    databaseReference.child(getKey).child(postId).setValue(offer);//setting primary key
//                                                    break;
//                                                }
//
//                                            }
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                                                User user = dataSnapshot.getValue(User.class);

                                                if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){

                                                    String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                                                    //getting the username of uploader
                                                    name = dataSnapshot.child("username").getValue().toString();
                                                    //uploading to firebase
                                                    //uploading to firebase
                                                    Boolean pinValue = false;


                                                    float rate = user.getRating();

                                                    String rating = "Rating: "+rate+"/5";

                                                    Offer offer = new Offer(FirebaseAuth.getInstance().getUid(),urlStrings.toString(),Profilepic,name,rating, itemName, itemCondition, itemDetails,itemValue,itemLocation,getKey,getUid,postId, pinValue);
                                                    databaseReference.child(getKey).child(postId).setValue(offer);//setting primary key

                                                    break;
                                                }
                                            }



                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });



                                    Toast.makeText(getContext(), "Successfully Offered", Toast.LENGTH_SHORT).show();
                                    Fragment mFragment = new FullPostFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ItemKey", getKey);
                                    bundle.putString("uId", getUid);
                                    mFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).addToBackStack(null).commit();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Failed to Upload",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }

    }

    private void storeLink(ArrayList<String> urlStrings) {
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i <urlStrings.size() ; i++) {
            hashMap.put("ImgLink"+i, urlStrings.get(i));

        }

        progressDialog.dismiss();
        itemList.clear();
    }
}