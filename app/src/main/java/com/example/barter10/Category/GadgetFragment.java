package com.example.barter10.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class GadgetFragment extends Fragment implements PostImageAdapter.OnItemClickListener {


    private List<Upload> mUploads;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    private RecyclerView recyclerView;
    private PostImageAdapter postImageAdapter;
    private FirebaseAuth firebaseAuth;
    private String currentId;
    private String profiePic;
    private String rating;

    public GadgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gadget, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();


        recyclerView = view.findViewById(R.id.gadget_rv);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        //Displaying Post
        mUploads = new ArrayList<>();
        postImageAdapter = new PostImageAdapter(getContext(), mUploads);
        recyclerView.setAdapter(postImageAdapter);
        postImageAdapter.setOnItemClickListener(GadgetFragment.this);


        //storage
        firebaseStorage = FirebaseStorage.getInstance();
        //displaying items
        databaseReference = FirebaseDatabase.getInstance().getReference("ApprovedPost");

        Query query = databaseReference.orderByChild("category1").equalTo("Furniture");
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

        //getting profile picture from user
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("profilepic");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profiePic = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ratingReference = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("rating");

        ratingReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rating = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //updating profile picture
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ApprovedPost");
        Query update = reference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());
        update.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataSnapshot.child("profileUrl").getRef().setValue(profiePic);
                    dataSnapshot.child("rating").getRef().setValue("Rating:"+rating+"/5");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        String selectedKey = selectedItem.getKey();

        String otherUid = selectedItem.getUid();


        if(currentId.equals(otherUid)){
            String rep = selectedItem.getImageUrl().replace("]","");
            String rep1 = rep.replace("[","");
            String rep2 = rep1.replace(" ","");

            StorageReference imageRef = firebaseStorage.getReferenceFromUrl(rep2);

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







        Toast.makeText(getContext(), selectedKey+"XXX"+otherUid, Toast.LENGTH_SHORT).show();



    }
}