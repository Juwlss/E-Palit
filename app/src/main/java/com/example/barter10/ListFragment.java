package com.example.barter10;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.ListpageAdapter;
import com.google.android.material.tabs.TabLayout;


public class ListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewPager2 lviewPager2;
        TabLayout lTabLayout;
        ListpageAdapter lpagerAdapter;

        View view = inflater.inflate(R.layout.fragment_list,container, false);

        //declaring viewpager and tabs
        //lviewPager2 = view.findViewById(R.id.list_vpager);

        lTabLayout = view.findViewById(R.id.listTab);


        FragmentManager fm = getActivity().getSupportFragmentManager();
        lpagerAdapter = new ListpageAdapter(fm, getLifecycle());
        //lviewPager2.setAdapter(lpagerAdapter);

        lTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //lviewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });








        return view;
    }
}