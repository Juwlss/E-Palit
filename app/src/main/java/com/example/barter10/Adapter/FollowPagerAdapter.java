package com.example.barter10.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.barter10.followerFragment;
import com.example.barter10.followingFragment;

public class FollowPagerAdapter extends FragmentStateAdapter {

    private String[] tabfollow = new String[]{"Followers", "Following"};

    int noTab;
    public FollowPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int Tabno) {
        super(fragmentManager, lifecycle);

        this.noTab = Tabno;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new followingFragment();
            case 1:
                return new followerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return noTab;
    }
}
