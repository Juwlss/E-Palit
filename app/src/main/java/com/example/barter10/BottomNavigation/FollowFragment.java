package com.example.barter10.BottomNavigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barter10.Adapter.FollowPagerAdapter;
import com.example.barter10.Adapter.PagerAdapter;
import com.example.barter10.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FollowFragment extends Fragment {


    private String[] tabfollow = new String[]{"Following", "Followers"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewPager2 f_viewPager2;
        TabLayout f_tabLayout;
        FollowPagerAdapter followAdapter;

        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        f_viewPager2 = view.findViewById(R.id.follow_vpager);
        f_tabLayout = view.findViewById(R.id.tab_follow);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        followAdapter = new FollowPagerAdapter(fm, getLifecycle(), f_tabLayout.getTabCount());
        f_viewPager2.setAdapter(followAdapter);




        new TabLayoutMediator(f_tabLayout, f_viewPager2,
                (tab, position) -> tab.setText(tabfollow[position])
        ).attach();

        return view;
    }
}