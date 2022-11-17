package com.example.barter10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.Model.Search;
import com.example.barter10.R;
import com.example.barter10.Search.RecentSearchInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private List<Search> searchList;
    private RecentSearchInterface recentSearchInterface;

    public SearchAdapter(Context context, ArrayList<Search> searchList, RecentSearchInterface recentSearchInterface) {
        this.context = context;
        this.searchList = searchList;
        this.recentSearchInterface = recentSearchInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Search search = searchList.get(position);

        holder.recentSearch.setText(search.getRecentSearch());


        //Delete Recent Search//
        holder.removeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference removeSearch = FirebaseDatabase.getInstance().getReference("RecentSearch").child(FirebaseAuth.getInstance().getUid());
                removeSearch.child(search.getRecentSearchId()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recentSearch;
        ImageView removeSearch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recentSearch = itemView.findViewById(R.id.recentSearch);
            removeSearch = itemView.findViewById(R.id.recentDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recentSearchInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}