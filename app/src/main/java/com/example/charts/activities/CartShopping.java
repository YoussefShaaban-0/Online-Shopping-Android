package com.example.charts.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charts.adapters.Adapter_List;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

public class CartShopping extends AppCompatActivity {
    public static double Total_price = 0;
    public static Cursor chart_cursor;
    ListView lv;
    ImageView empty_list;

    BottomNavigationView bottomNavigationView;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        View back = findViewById(R.id.Backtohome);

        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");
        Toast.makeText(getApplicationContext(), loggedInUserEmail, Toast.LENGTH_SHORT).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });


        // Initialize the ListView
        lv = findViewById(R.id.CartItemsList);
        Databasemanger db = Databasemanger.getInstance(this);
        Cursor c = db.fetchchartbyEmail(loggedInUserEmail);
        chart_cursor=c;

        Adapter_List list = new Adapter_List(this,c);
        lv.setAdapter(list);

        // catch all elements on the screen
        TextView total = findViewById(R.id.CartTotalitem);
        TextView totalPrice = findViewById(R.id.CartToPayment);
        TextView contTopayment = findViewById(R.id.itemcount);
        TextView pay = findViewById(R.id.CartConfirm);

        Total_price = db.getTotalPriceForUser(loggedInUserEmail);
        // set total item in cart and total price
        totalPrice.setText("$" + Total_price);
        // list.notifyDataSetChanged();
        total.setText("Total ( " +db.getTotalItemForUser(loggedInUserEmail) + " items ) :");
        contTopayment.setText(String.valueOf(db.getTotalItemForUser(loggedInUserEmail) ));

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), choce_payment.class);
                Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.cart) {
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation) {
                    // Do something for the "navigation" item
                    return true;
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), End_app.class));
                    overridePendingTransition(0, 0);
                    return true;
                }


                return false;
            }
        });

    }
}