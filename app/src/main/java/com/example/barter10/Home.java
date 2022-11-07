package com.example.barter10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.BottomNavigation.HomeFragment;
import com.example.barter10.BottomNavigation.ListFragment;
import com.example.barter10.BottomNavigation.MessageFragment;
import com.example.barter10.BottomNavigation.ProfileFragment;
import com.example.barter10.Search.SearchFragment;
import com.example.barter10.Upload.activityUpload;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    int goBack;
    ImageView btnSearch;
    AppBarLayout appBarLayout;
    FloatingActionButton upload;
    FirebaseAuth firebaseAuth;
    private TextView welcome;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = findViewById(R.id.bot_nav);
        btnSearch = findViewById(R.id.btnSearch);

        upload = findViewById(R.id.upload_item);

        appBarLayout = findViewById(R.id.appbar);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();


        //text welcome
        welcome = findViewById(R.id.welcome);

        DatabaseReference postReference;
        postReference = FirebaseDatabase.getInstance().getReference("users");

        postReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userid="";

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                   if(dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){

                       userid = dataSnapshot.child("username").getValue().toString();
                       String name[] = userid.split(" ");
                       String upperString = name[0].substring(0, 1).toUpperCase() + name[0].substring(1).toLowerCase();
                       welcome.setText("Welcome "+upperString+"!");
                       break;

                   }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        //declaring bottom navigation
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_list_alt_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_chat_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_person_24));


        //choosing in bottom navigation
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;

                switch(item.getId()){
                    case 1:
                        goBack = 1;
                        appbarVisibility(goBack);
                        fragment = new HomeFragment();

                        break;

                    case 2:
                        goBack = 2;
                        appbarVisibility(goBack);
                        fragment = new ListFragment();

                        break;

                    case 3:
                        goBack = 3;
                        appbarVisibility(goBack);
                        fragment = new MessageFragment();

                        break;

                    case 4:
                        goBack = 4;
                        appbarVisibility(goBack);
                        fragment = new ProfileFragment();
                        break;
                }
                loadFragment(fragment);
            }

        });

        //count notif
//        bottomNavigation.setCount(1, "1");

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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, activityUpload.class));
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SearchFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.homeFrameLayout,fragment).commit();
                bottomNavigation.setVisibility(View.GONE);
            }
        });


    }

    /**private void replaceFragment(MessageFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }**/

    public void appbarVisibility(int goBack){
        if (goBack != 1){
            appBarLayout.setVisibility(View.GONE);
        }else{
            appBarLayout.setVisibility(View.VISIBLE);
        }
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(goBack == 1){
            Log.d("CDA", "onBackPressed Called");
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }

    }


    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);


        MenuItem menuItem = menu.findItem(R.id.searchIcon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }**/


}