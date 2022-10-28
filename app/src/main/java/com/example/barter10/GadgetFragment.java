package com.example.barter10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.User;
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

import java.util.ArrayList;
import java.util.List;

public class GadgetFragment extends Fragment implements PostImageAdapter.OnItemClickListener {


    private List<Upload> mUploads;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    private RecyclerView recyclerView;
    private PostImageAdapter postImageAdapter;

    public GadgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gadget, container, false);


        recyclerView = view.findViewById(R.id.post_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Displaying Post
        mUploads = new ArrayList<>();
        postImageAdapter = new PostImageAdapter(getContext(), mUploads);
        recyclerView.setAdapter(postImageAdapter);
        postImageAdapter.setOnItemClickListener(GadgetFragment.this);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //storage
        firebaseStorage = FirebaseStorage.getInstance();
        //displaying items
        databaseReference = FirebaseDatabase.getInstance().getReference("PostItem");

//        DatabaseReference postReference;
//        postReference = FirebaseDatabase.getInstance().getReference("users");
//
//        postReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUploads.clear();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    User user = new User();
//                    String username = dataSnapshot.child("username").getValue().toString();
//                    Toast.makeText(getContext(), ""+username, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fetching from firebase to display
                mUploads.clear();

                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);

                }

                postImageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






        return view;
    }


    //Post Menu

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Normal Click: "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(getContext(), "Whatever Click: "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUploads.get(position);
        String selectedkey = selectedItem.getKey();
        String username = selectedItem.getUserName();
        Toast.makeText(getContext(), ""+username, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), ""+selectedkey, Toast.LENGTH_SHORT).show();


//        StorageReference imageRef = firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
//
//        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//                databaseReference.child(selectedkey).removeValue();
//                Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Failed to Delete", Toast.LENGTH_SHORT).show();
//            }
//        });



    }
}