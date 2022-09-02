package com.example.barter10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Home extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bot_nav);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.nhome));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.nlist));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.nmessage));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.nprofile));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch(item.getId()){
                    case 1:
                        fragment = new HomeFragment();
                        break;

                    case 2:
                        fragment = new ListFragment();
                        break;

                    case 3:
                        fragment = new MessageFragment();
                        break;

                    case 4:
                        fragment = new ProfileFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        //count notif
        bottomNavigation.setCount(1, "1");

        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }
}