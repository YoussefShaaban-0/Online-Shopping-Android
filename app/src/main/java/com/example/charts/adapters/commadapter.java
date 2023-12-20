package com.example.charts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charts.R;
import com.example.charts.activities.Rating;

import java.util.List;

public class commadapter extends RecyclerView.Adapter<CommHolder> {


    Context context;
    List<Rating> rating;

    public commadapter(Context context, List<Rating> rating) {
        this.context = context;
        this.rating = rating;
    }

    @NonNull
    @Override
    public CommHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CommHolder(LayoutInflater.from(context).inflate(R.layout.comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommHolder holder, int position) {
        holder.commentview.setText(rating.get(position).getFeedback());
        holder.emailview.setText((rating.get(position).getCustomer_email()));


    }

    @Override
    public int getItemCount() {
        return rating.size();
    }
}

