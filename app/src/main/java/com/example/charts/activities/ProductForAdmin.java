package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.charts.adapters.RecyclerViewAdapter;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

public class ProductForAdmin extends AppCompatActivity {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView rec_products;
    Databasemanger databasemanger;
    ImageView backto_admin_options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_for_admin);

        rec_products=findViewById(R.id.rec1);
        databasemanger=Databasemanger.getInstance(this);
        backto_admin_options=findViewById(R.id.arrow_back);


        backto_admin_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MangeAllProducts.class));

            }
        });



        Cursor cursor=databasemanger.fetch();

        recyclerViewAdapter=new RecyclerViewAdapter(this,cursor);


        if (cursor != null) {

            StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
            rec_products.setAdapter(recyclerViewAdapter);
            rec_products.setLayoutManager(staggeredGridLayoutManager);


            // Check if the data list is empty
            if (cursor != null && cursor.getCount() > 0) {
                // If the cursor is not null and has items, show the RecyclerView
                rec_products.setVisibility(View.VISIBLE);
            } else {
                // If the cursor is either null or empty, show the empty view
                rec_products.setVisibility(View.GONE);
            }


        }
    }
}