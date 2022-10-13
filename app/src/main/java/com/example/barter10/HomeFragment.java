package com.example.barter10;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.PagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment{

    private String[] tabCategory = new String[]{"Gadget", "Sport", "Fashion", "Toy"};

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
        pagerAdapter = new PagerAdapter(fm, getLifecycle(), tabLayout.getTabCount());
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabCategory[position])
        ).attach();



        return view;
    }


}