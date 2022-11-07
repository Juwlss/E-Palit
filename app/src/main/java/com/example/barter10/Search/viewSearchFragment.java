package com.example.barter10.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.barter10.Adapter.PostImageAdapter;
import com.example.barter10.Model.Upload;
import com.example.barter10.Model.viewSearch;
import com.example.barter10.Profile.visitprofile;
import com.example.barter10.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class viewSearchFragment extends Fragment {

    ImageView btnViewBack;
    EditText viewSearchBar;
    RecyclerView rv;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseRecyclerOptions<viewSearch> options;
    FirebaseRecyclerAdapter<viewSearch, viewSearchHolder> adapter;
    DatabaseReference dataRef;
    private PostImageAdapter postImageAdapter;
    private List<Upload> mUploads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_search, container, false);

        String searchedText = SearchFragment.searchedText;// getting the input search in parent fragment


        dataRef = FirebaseDatabase.getInstance().getReference("PostItem");



        btnViewBack = view.findViewById(R.id.btnViewBack);
        viewSearchBar = view.findViewById(R.id.viewSearch_bar);

        postImageAdapter = new PostImageAdapter(getContext(), mUploads);

        //displaying search
        rv = view.findViewById(R.id.viewSearch_rv);
        rv.setAdapter(postImageAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        mUploads = new ArrayList<>();

        //getting value in firebase
        LoadPostData("");

        viewSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString()!=null){
                    LoadPostData(editable.toString());
                }
                else {
                    LoadPostData("");
                }

            }
        });





        btnViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });


        viewSearchBar.setText(searchedText);
        viewSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });

        return view;
    }

    private void Back() {
        Fragment mFragment = new SearchFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, mFragment).commit();
    }


    private void LoadPostData (String data){


        Query query = dataRef
                .orderByChild("itemName").startAt(data).endAt(data+"\uf8ff");


        options = new FirebaseRecyclerOptions.Builder<viewSearch>().setQuery(query, viewSearch.class).build();

        adapter = new FirebaseRecyclerAdapter<viewSearch, viewSearchHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewSearchHolder holder, int position, @NonNull viewSearch model) {

                holder.userName.setText(model.getUserName());
                holder.location.setText(model.getLocation());
                holder.itemName.setText(model.getItemName());
                holder.condition.setText(model.getItemCondition());

                //item image
                Picasso.get()
                        .load(model.getImageUrl())
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .fit()
                        .into(holder.searchImage);

                //user image
                Picasso.get()
                        .load(model.getProfileUrl())
                        .placeholder(R.drawable.ic_default_picture)
                        .fit()
                        .into(holder.userImage);


                holder.visitProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                        Toast.makeText(getContext(), upload.getUid()+"", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                        editor.putString("username", model.getUserName());
                        editor.apply();


                        AppCompatActivity activity = (AppCompatActivity) v.getContext();

                        MeowBottomNavigation meowBottomNavigation = activity.findViewById(R.id.bot_nav);
                        meowBottomNavigation.setVisibility(View.GONE);

                        Fragment fragment = new visitprofile();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout,fragment).addToBackStack(null).commit();

                    }
                });



                holder.viewPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "View Post", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public viewSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
                return  new viewSearchHolder(view);
            }
        };
        adapter.startListening();
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}