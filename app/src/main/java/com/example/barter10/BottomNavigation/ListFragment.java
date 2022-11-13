package com.example.barter10.BottomNavigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.ListpageAdapter;
import com.example.barter10.Adapter.trade_recyclerviewAdapter;
import com.example.barter10.Model.Pending;
import com.example.barter10.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private String[] tabList = new String[]{"Pending", "Finished"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewPager2 lviewPager2;
        TabLayout lTabLayout;
        ListpageAdapter lpagerAdapter;

        View view = inflater.inflate(R.layout.fragment_list,container, false);


        //declaring viewpager and tabs
        lviewPager2 = view.findViewById(R.id.list_vpager);
        lTabLayout = view.findViewById(R.id.listTab);


        FragmentManager fm = getActivity().getSupportFragmentManager();
        lpagerAdapter = new ListpageAdapter(fm, getLifecycle(), lTabLayout.getTabCount());
        lviewPager2.setAdapter(lpagerAdapter);


        new TabLayoutMediator(lTabLayout, lviewPager2,
                (tab, position) -> tab.setText(tabList[position])
        ).attach();



        return view;
    }


}