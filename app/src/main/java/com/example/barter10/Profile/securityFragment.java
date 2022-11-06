package com.example.barter10.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.barter10.R;

public class securityFragment extends Fragment {
    ImageView btnBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_security, container, false);
       btnBack = view.findViewById(R.id.backar2);

       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
               Fragment profileSettingFrag = new profileSettings();
               fragmentTransaction.replace(R.id.frame_layout, profileSettingFrag);
               fragmentTransaction.commit();
           }
       });
        return view;
    }
}