package com.example.barter10.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.barter10.List.FinishedFragment;
import com.example.barter10.List.PendingFragment;
import com.example.barter10.Profile.FeedbackFragment;
import com.example.barter10.Profile.VisitpostFragment;

public class VisitPageAdapter extends FragmentStateAdapter {



    int noTab;
    public VisitPageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int Tabno) {
        super(fragmentManager, lifecycle);

        this.noTab = Tabno;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new VisitpostFragment();
            case 1:
                return new FeedbackFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return noTab;
    }
}
