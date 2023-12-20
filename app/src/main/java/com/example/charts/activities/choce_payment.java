package com.example.charts.activities;

import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.charts.adapters.Adapter_List;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.productBuilder.Product;
import com.example.charts.R;

import java.util.ArrayList;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

public class choce_payment extends AppCompatActivity {

    ListView lv;
    int userid = 1;
    Button pay ;
    ArrayList<Product> ProductArrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choce_payment);


        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");

        // button back
        View back = findViewById(R.id.backtocart);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartShopping.class);
                startActivity(intent);
            }
        });

        // Initialize the ListView
        lv = findViewById(R.id.histtory);
        Databasemanger db=Databasemanger.getInstance(this);
        Cursor c = db.fetchCart();
        Adapter_List list = new Adapter_List(this,c);
        lv.setAdapter(list);


        double total = db.getTotalPriceForUser(loggedInUserEmail);

        CardView creditCardCardView = findViewById(R.id.creditCardCardView);
        CardView paypalCardView = findViewById(R.id.paypalCardView);
        CardView visaCardView = findViewById(R.id.visaCardView);
        RadioButton creditCardRadioButton = findViewById(R.id.creditCardRadioButton);
        RadioButton paypalRadioButton = findViewById(R.id.paypalRadioButton);
        RadioButton visaRadioButton = findViewById(R.id.visa);

        // Set onClickListener for the Credit Card CardView
        creditCardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background color to black
                creditCardCardView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                // Set RadioButton as selected
                creditCardRadioButton.setChecked(true);
                // Reset other CardViews and RadioButtons
                paypalCardView.setBackgroundColor(getResources().getColor(android.R.color.white));
                visaCardView.setBackgroundColor(getResources().getColor(android.R.color.white));
                paypalRadioButton.setChecked(false);
                visaRadioButton.setChecked(false);

                // Start the new activity here
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                intent.putExtra("pay", "CreditCart");
                startActivity(intent);
            }
        });

        paypalCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background color to black
                paypalCardView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                // Set RadioButton as selected
                paypalRadioButton.setChecked(true);
                // Reset other CardViews and RadioButtons
                creditCardCardView.setBackgroundColor(getResources().getColor(android.R.color.black));
                visaCardView.setBackgroundColor(getResources().getColor(android.R.color.white));
                creditCardRadioButton.setChecked(false);
                visaRadioButton.setChecked(false);

                // Start the new activity here
                Intent intent = new Intent(getApplicationContext(),Paypal_page.class);
                intent.putExtra("pay", "PayPal");
                startActivity(intent);
            }
        });

        visaCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change background color to black
                visaCardView.setBackgroundColor(getResources().getColor(android.R.color.black));
                // Set RadioButton as selected
                visaRadioButton.setChecked(true);

                // Reset other CardViews and RadioButtons
                creditCardCardView.setBackgroundColor(getResources().getColor(android.R.color.black));
                paypalCardView.setBackgroundColor(getResources().getColor(android.R.color.white));
                creditCardRadioButton.setChecked(false);
                paypalRadioButton.setChecked(false);

                // Start the new activity here
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                intent.putExtra("pay", "visa");
                startActivity(intent);
            }
        });
    }
}