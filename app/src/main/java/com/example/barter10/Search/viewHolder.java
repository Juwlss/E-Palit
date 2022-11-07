package com.example.barter10.Search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barter10.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewHolder extends RecyclerView.ViewHolder {

    TextView recent_search;
    ImageView btnDelete;
    View v;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        recent_search = itemView.findViewById(R.id.recentsearch);
        btnDelete = itemView.findViewById(R.id.recentDelete);
        v = itemView;

    }
}
