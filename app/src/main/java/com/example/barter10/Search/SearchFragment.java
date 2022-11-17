package com.example.barter10.Search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.barter10.Adapter.SearchAdapter;
import com.example.barter10.Home;
import com.example.barter10.Model.Search;
import com.example.barter10.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements RecentSearchInterface {

    public static String searchHolder;
    private ImageView s_btnBack;
    private EditText searchBar;
    private DatabaseReference searchReference;
    private ArrayList<Search> searchList;
    private RecyclerView rv_Search;
    private SearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //Revert the search holder to null//
        searchHolder = null;

        //Getting ID in XML layout//
        s_btnBack = view.findViewById(R.id.s_btnBack);
        searchBar = view.findViewById(R.id.searchBar);
        rv_Search = view.findViewById(R.id.search_rv);

        //Firebase Reference//
        searchReference = FirebaseDatabase.getInstance().getReference("RecentSearch").child(FirebaseAuth.getInstance().getUid());

        //List Setup & Adapter//
        searchList = new ArrayList<>();
        searchAdapter = new SearchAdapter(getContext(), searchList, this);

        //Recycler View Setup//
        rv_Search.setHasFixedSize(true);
        rv_Search.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_Search.setAdapter(searchAdapter);

        //Go Back Methods//
        s_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
                searchBar.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        //Adding To Firebase//

        searchReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searchList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Search search = snap.getValue(Search.class);
                    searchList.add(search);
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Searching Methods and Adding in Firebase//


        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if ((i == KeyEvent.KEYCODE_DPAD_CENTER) || (i == KeyEvent.KEYCODE_ENTER)){

                        String getSearchText = searchBar.getText().toString();
                        if(TextUtils.isEmpty(getSearchText)){
                            searchBar.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
                        else {

                            //Store search in this variable//
                            searchHolder = searchBar.getText().toString().trim();

                            String searchId = searchReference.push().getKey();
                            String recent = searchBar.getText().toString().trim();
                            Search search = new Search(recent,searchId);
                            searchReference.child(searchId).setValue(search);
                            searchBar.onEditorAction(EditorInfo.IME_ACTION_DONE);

                            // going to searched post
                            Fragment mFragment = new ViewSearchFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).commit();

                        }


                        return true;
                    }
                return false;
            }
        });


        searchBar.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);



        return view;
    }

    @Override
    public void onItemClick(int position) {
        Search search = searchList.get(position);

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
        editor.putString("recentSearch", search.getRecentSearch());
        editor.apply();

        Fragment fragment = new ViewSearchFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();

        searchBar.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }
}