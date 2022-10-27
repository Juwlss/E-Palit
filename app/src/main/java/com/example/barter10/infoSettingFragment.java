package com.example.barter10;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class infoSettingFragment extends Fragment {
    ImageView btnBack;
    ImageView changeProfilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_info_setting, container, false);
        btnBack = view.findViewById(R.id.backarr);
        changeProfilePic = view.findViewById(R.id.changeProfile);

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