package com.example.charts.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

public class ShowAlooOrder extends AppCompatActivity {

    ListView lv;
    TextView empty_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_aloo_order);
        empty_list=findViewById(R.id.emptylist);
        View back = findViewById(R.id.Backtohome);

        lv = findViewById(R.id.ordertItemsList);
        SharedPreferences sharedPreferences = getSharedPreferences(shahardprefrences_name, 0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin, "no");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MangeAllProducts.class));
            }
        });

        Databasemanger db = Databasemanger.getInstance(this);
        Cursor c = db.getAllOrders();
        OrderList list = new OrderList(this, c);
        lv.setAdapter(list);
        lv.setEmptyView(empty_list);

    }
}