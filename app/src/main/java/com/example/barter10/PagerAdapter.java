package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {
    private final List <Fragment> fragmentList = new ArrayList<>();
    private final List <String> fragmentTitle = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SportsFragment();
            case 2:
                return new FashionFragment();
            case 3:
                return new ToyFragment();
            default:
                return new GadgetFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
