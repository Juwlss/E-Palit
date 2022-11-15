package com.example.barter10.Category;

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
import com.example.barter10.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ToyFragment extends Fragment implements PostImageAdapter.OnItemClickListener {
    private List<Upload> mUploads;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    private RecyclerView recyclerView;
    private PostImageAdapter postImageAdapter;
    private FirebaseAuth firebaseAuth;
    private String currentId;

    public ToyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_toy, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.toy_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mUploads = new ArrayList<>();
        postImageAdapter = new PostImageAdapter(getContext(),mUploads);
        recyclerView.setAdapter(postImageAdapter);
        postImageAdapter.setOnItemClickListener(ToyFragment.this);

        //storage
        firebaseStorage = FirebaseStorage.getInstance();
        //displaying items
        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost");


        Query query = databaseReference.orderByChild("category1").equalTo("Toy");
        query.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onWhateverClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

        Upload selectedItem = mUploads.get(position);
        String selectedKey = selectedItem.getKey();

        String otherUid = selectedItem.getUid();

        if(currentId.equals(otherUid)){
            StorageReference imageRef = firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());

            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    databaseReference.child(selectedKey).removeValue();
                    Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            Toast.makeText(getContext(), "This is not your post", Toast.LENGTH_SHORT).show();
        }
    }
}