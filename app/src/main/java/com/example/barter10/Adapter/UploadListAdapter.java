package com.example.barter10.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.barter10.Model.Upload;
import com.example.barter10.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ViewHolder> {

    private ArrayList<Uri> imgList;

    public UploadListAdapter(ArrayList<Uri> imgList) {
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public UploadListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.upload_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadListAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageURI(imgList.get(position));

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
