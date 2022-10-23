package com.example.barter10;

import android.content.Context;
import android.content.Intent;
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

import com.example.barter10.Model.RecentSearch;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SearchFragment extends Fragment {
    ImageView btnBack;
    RecyclerView recyclerView;
    EditText search_bar;
    FirebaseRecyclerOptions<RecentSearch> options;
    FirebaseRecyclerAdapter<RecentSearch, viewHolder> adapter;
    DatabaseReference reference;// for setting the recent searches on database
    DatabaseReference dataRef;// for getting database of recent search
    FirebaseAuth firebaseAuth;
    public static String searchedText;
    String recentSearchKey;
    String currentUserId;
    DatabaseReference dataDeleteRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        dataRef = FirebaseDatabase.getInstance().getReference().child("RecentSearch").child(currentUserId);
        btnBack = view.findViewById(R.id.btnBack);
        search_bar = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.search_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();

        //button back

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_bar.onEditorAction(EditorInfo.IME_ACTION_DONE);
                Intent intent = new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });


        //this is for recent searches
        reference = FirebaseDatabase.getInstance().getReference("RecentSearch").child(currentUserId);
        String key = reference.push().getKey();
        search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if ((i == KeyEvent.KEYCODE_DPAD_CENTER) || (i == KeyEvent.KEYCODE_ENTER)){
                        String getSearchText = search_bar.getText().toString();
                        searchedText = getSearchText;
                        if(TextUtils.isEmpty(getSearchText)){
                            search_bar.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
                        else {
                            String recent = search_bar.getText().toString().trim();
                            String recentkey = key;
                            RecentSearch recents = new RecentSearch(recent,recentkey);
                            reference.child(key).setValue(recents);
                            search_bar.onEditorAction(EditorInfo.IME_ACTION_DONE);
                            // going to searched post
                            Fragment mFragment = new viewSearchFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).commit();

                        }


                        return true;
                    }
                return false;
            }
        });


        search_bar.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);



        return view;
    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<RecentSearch>().setQuery(dataRef, RecentSearch.class).build();
        adapter = new FirebaseRecyclerAdapter<RecentSearch, viewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull RecentSearch recentSearch) {
                viewHolder.recent_search.setText(recentSearch.getRecent());
                viewHolder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_bar.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        searchedText = recentSearch.getRecent();
                        // going to searched post
                        Fragment mFragment = new viewSearchFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).commit();
                    }
                });
                viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recentSearchKey = recentSearch.getRecentkey();
                        dataDeleteRef = FirebaseDatabase.getInstance().getReference("RecentSearch").child(currentUserId).child(recentSearchKey);
                        dataDeleteRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //do nothing
                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
                return new viewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}