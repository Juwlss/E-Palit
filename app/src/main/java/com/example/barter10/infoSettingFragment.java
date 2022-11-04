package com.example.barter10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class infoSettingFragment extends Fragment {

    private ImageView btnBack;
    private ImageView changeProfilePic;
    private ImageView editUsername;
    private int userEdit = 0;
    private TextView userName;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_info_setting, container, false);


        btnBack = view.findViewById(R.id.backarr);
        changeProfilePic = view.findViewById(R.id.btn_img);
        editUsername = view.findViewById(R.id.btn_username);
        userName = view.findViewById(R.id.editName);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

//        Query qInfo = databaseReference.orderByChild("").equalTo(FirebaseAuth.getInstance().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        userName.setText(dataSnapshot.child("username").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        editUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (userEdit){
                    case 0:
                        userName.setTextIsSelectable(true);
                        editUsername.setColorFilter(getContext().getResources().getColor(R.color.epalitblue));
                        userEdit = 1;
                        break;

                    case 1:
                        userName.setTextIsSelectable(false);
                        editUsername.setColorFilter(getContext().getResources().getColor(R.color.epalitred));
                        userEdit=0;
                        break;

                }
            }
        });






        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment profileSettingFrag = new profileSettings();
                fragmentTransaction.replace(R.id.frame_layout, profileSettingFrag);
                fragmentTransaction.commit();
            }
        });
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Change profile pic", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}