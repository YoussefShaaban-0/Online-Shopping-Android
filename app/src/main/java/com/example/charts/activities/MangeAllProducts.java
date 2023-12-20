package com.example.charts.activities;

import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.charts.others.InsertNewCatagory;
import com.example.charts.others.InsertNewItem;
import com.example.charts.R;

public class MangeAllProducts extends AppCompatActivity {

    Button to_showallproductfor_admin,to_best_sales,to_insert_product,to_manage_all_catagory,to_manage_user,to_allorder;
    ImageView arrow_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mange_all_products);

        to_showallproductfor_admin=findViewById(R.id.manageallproduct);
        to_insert_product=findViewById(R.id.insertNewproduct);
        to_manage_all_catagory=findViewById(R.id.mangecatagoty);
        to_manage_user=findViewById(R.id.manageallusers);
        to_allorder=findViewById(R.id.manageallorder);
        to_best_sales=findViewById(R.id.topsalesbtn);
        arrow_back=findViewById(R.id.arrow_back);

        to_showallproductfor_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProductForAdmin.class));
            }
        });

        to_insert_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InsertNewItem.class));
            }
        });
        to_manage_all_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InsertNewCatagory.class));
            }
        });
        to_manage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), edit_user.class));
            }
        });

        to_allorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowAlooOrder.class));
            }
        });
        to_best_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChartActivity.class));
            }
        });
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),End_app.class));
                finish();
            }
        });

    }
}