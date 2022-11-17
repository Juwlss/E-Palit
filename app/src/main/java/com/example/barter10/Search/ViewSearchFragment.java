package com.example.barter10.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;
import com.example.barter10.Search.SearchFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewSearchFragment extends Fragment implements View.OnClickListener {

    private ImageView viewBtnBack;
    private EditText viewSearchBar;
    private String getSearchValue;

    private RecyclerView rv_ViewSearch;
    private List<Upload> uploadList;
    private PostImageAdapter postImageAdapter;
    private DatabaseReference postReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_search, container, false);

        //Getting ID in XML//
        viewBtnBack = view.findViewById(R.id.btnViewBack);
        viewSearchBar = view.findViewById(R.id.viewSearch_bar);
        rv_ViewSearch = view.findViewById(R.id.viewSearch_rv);

        //Setting Up Arraylist and Adapter//
        uploadList = new ArrayList<>();
        postImageAdapter = new PostImageAdapter(getContext(), uploadList);

        //Recycler View Setup//
        rv_ViewSearch.setHasFixedSize(true);
        rv_ViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ViewSearch.setAdapter(postImageAdapter);


        //Go Back Methods//
        viewBtnBack.setOnClickListener(this);


        //Get Search Data in Search Fragment//
        getSearchValue = SearchFragment.searchHolder;

        //Set search value on viewSearchBar//
        if (getSearchValue!=null){
            viewSearchBar.setText(getSearchValue);
            getSearchPost(getSearchValue);
        }
        else {
            //Getting Value via Shared Preferences//
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            getSearchValue = sharedPreferences.getString("recentSearch", "none");
            viewSearchBar.setText(getSearchValue);
            getSearchPost(getSearchValue);
        }


        //Methods//
        viewSearchBar.setOnClickListener(this);


        return view;
    }

    private void getSearchPost(String getSearchValue) {

        //Displaying Data through Search using Firebase//

        postReference = FirebaseDatabase.getInstance().getReference("ApprovedPost");

        Query query = postReference.orderByChild("ApprovedPost").startAt(getSearchValue).endAt(getSearchValue+"\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uploadList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    uploadList.add(upload);
                }
                postImageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View view) {
        Fragment mFragment = new SearchFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).commit();

    }
}