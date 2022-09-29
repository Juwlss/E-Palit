package com.example.barter10;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.barter10.Adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {


    private Toolbar mToolbar;

    ArrayAdapter<String> arrayAdapter;

    //Oncreate
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewPager2 viewPager2;
        TabLayout tabLayout;
        PagerAdapter pagerAdapter;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container, false);



        //declaring viewpager and tabs
        viewPager2 = view.findViewById(R.id.home_vpager);
        tabLayout = view.findViewById(R.id.tab_categories);


        FragmentManager fm = getActivity().getSupportFragmentManager();
        pagerAdapter = new PagerAdapter(fm, getLifecycle());
        viewPager2.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
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