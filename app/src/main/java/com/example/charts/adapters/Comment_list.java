package com.example.charts.adapters;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;

import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.activities.Rating;

import java.util.List;
public class Comment_list extends AppCompatActivity {
    EditText x;
    Databasemanger databasemanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentlist);
        Databasemanger databasemanger=Databasemanger.getInstance(this);
        List<Rating> all_comments=Rating.getRatings();
        Rating r=Rating.build_rate();
        //Rating r=Rating.build_rate();
        all_comments=databasemanger.Display_feedbacks(r.getProduct_title());
        // r=all_comments.get(-1);
        ///////////////////////////////////////////////




        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new commadapter(getApplicationContext(),all_comments));
    }
}