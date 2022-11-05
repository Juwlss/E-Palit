package com.example.barter10;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Adapter.SelfPostAdapter;
import com.example.barter10.Model.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    private List<Upload> mUploads;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private SelfPostAdapter selfPostAdapter;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private TextView countPost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageButton settings = view.findViewById(R.id.btn_settings);

        //profile
        ImageView imageprofile = view.findViewById(R.id.image_profile);
        TextView profilename = view.findViewById(R.id.profilename);
        countPost = view.findViewById(R.id.numpost);





        //displaying post of user
        recyclerView = view.findViewById(R.id.prof_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mUploads = new ArrayList<>();
        selfPostAdapter = new SelfPostAdapter(getContext(), mUploads);
        recyclerView.setAdapter(selfPostAdapter);

        //referencing to the firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //displaying the post of user
        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("PostItem");

        Query qPost = databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid());

        qPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //fetching from firebase to display
                mUploads.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);


                    countPost.setText(""+snapshot.getChildrenCount());
                    Toast.makeText(getContext(), "uploadcount : "+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                }


                selfPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });






        //go to profile settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment profileSettingFrag = new profileSettings();
                fragmentTransaction.replace(R.id.frame_layout, profileSettingFrag);
                fragmentTransaction.commit();
            }
        });


        //Displaying the username into profile fragment
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("users");

        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        String Profilepic = dataSnapshot.child("profilepic").getValue().toString();

                        Picasso.get().load(Profilepic).into(imageprofile);
                        profilename.setText(dataSnapshot.child("username").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        return view;
    }
}