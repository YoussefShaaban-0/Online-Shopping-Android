package com.example.charts.others;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.charts.R;
import com.example.charts.activities.CartShopping;
import com.example.charts.activities.End_app;
import com.example.charts.activities.Home;
import com.example.charts.adapters.Adapterlist;
import com.example.charts.adapters.AllCatagorySelected;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CatagoriesName extends AppCompatActivity {

    ListView catagories;
    TextView  emptylist;
    Databasemanger databasemanger;
    ImageView arroback;
    String catagory_selected;
    BottomNavigationView bottomNavigationView;
    public static final  String catagory_name_key="catagory_name_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories_name);


        catagories=findViewById(R.id.listofcatagory);
        emptylist=findViewById(R.id.emptylist);
        arroback=findViewById(R.id.arrow_back);

        databasemanger=Databasemanger.getInstance(this);
        Cursor cursor=databasemanger.fetchcatagory();

        Adapterlist adapterlist=new Adapterlist(this,cursor);
        catagories.setAdapter(adapterlist);
        catagories.setEmptyView(emptylist);
        catagories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                catagory_selected=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.catagoryname));

                Intent to_show_all_item_of_this_catagory =new Intent(getApplicationContext(), AllCatagorySelected.class);
                to_show_all_item_of_this_catagory.putExtra(catagory_name_key,catagory_selected);
                startActivity(to_show_all_item_of_this_catagory);
            }
        });



        arroback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });



        bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), CartShopping.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    finish();

                    return true;
                } else if (itemId == R.id.navigation) {
                    // Do something for the "navigation" item
                    return true;
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), End_app.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });














    }
}