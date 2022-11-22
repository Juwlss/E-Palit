package com.example.barter10.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.barter10.Category.FashionFragment;
import com.example.barter10.Category.GadgetFragment;
import com.example.barter10.Category.SportsFragment;
import com.example.barter10.Category.TechnologyFragment;
import com.example.barter10.Category.ToyFragment;

public class PagerAdapter extends FragmentStateAdapter {

    private String[] tabCategory = new String[]{"Furniture", "Sport", "Fashion", "Appliances","Technology"};

    int noTab;
    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int Tabno) {
        super(fragmentManager, lifecycle);

        this.noTab = Tabno;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new GadgetFragment();
            case 1:
                return new SportsFragment();
            case 2:
                return new FashionFragment();
            case 3:
                return new ToyFragment();
            case 4:
                return new TechnologyFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return noTab;
    }
}
