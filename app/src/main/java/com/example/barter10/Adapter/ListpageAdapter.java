package com.example.barter10.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.barter10.List.FinishedFragment;
import com.example.barter10.List.PendingFragment;

public class ListpageAdapter extends FragmentStateAdapter {

    private String[] tabList = new String[]{"Pending", "Finished"};

    int noTab;
    public ListpageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int Tabno) {
        super(fragmentManager, lifecycle);

        this.noTab = Tabno;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PendingFragment();
            case 1:
                return new FinishedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return noTab;
    }
}
