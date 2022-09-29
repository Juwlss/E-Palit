package com.example.barter10.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.barter10.FinishedFragment;
import com.example.barter10.PendingFragment;

public class ListpageAdapter extends FragmentStateAdapter {
    public ListpageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new FinishedFragment();
            default:
                return new PendingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
