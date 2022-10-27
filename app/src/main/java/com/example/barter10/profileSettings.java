package com.example.barter10;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class profileSettings extends Fragment {

    FirebaseAuth firebaseAuth;
    Button btnInfo;
    Button btnSec;
    TextView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        logout = view.findViewById(R.id.btn_logout);
        firebaseAuth = FirebaseAuth.getInstance();
        btnInfo = view.findViewById(R.id.btnMyInfo);
        btnSec = view.findViewById(R.id.btnSec);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logging out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment infoFrag = new infoSettingFragment();
                fragmentTransaction.replace(R.id.frame_layout, infoFrag);
                fragmentTransaction.commit();
            }
        });

        btnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment securityFrag = new securityFragment();
                fragmentTransaction.replace(R.id.frame_layout, securityFrag);
                fragmentTransaction.commit();
            }
        });



        return view;
    }
}