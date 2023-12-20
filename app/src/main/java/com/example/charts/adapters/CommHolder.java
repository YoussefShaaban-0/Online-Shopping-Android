package com.example.charts.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.charts.R;

public class CommHolder extends RecyclerView.ViewHolder {

    TextView emailview;
    TextView commentview;


    public CommHolder(@NonNull View itemView) {
        super(itemView);
        emailview=itemView.findViewById(R.id.custnam);
        commentview=itemView.findViewById(R.id.coment);




    }
}
