package com.example.charts.stratgy;

import android.content.Context;
import android.widget.Toast;

public class Order {
   private PaymentSystem pay;
   private Context context;

    public Order(PaymentSystem pay, Context context) {
        this.pay = pay;
        this.context = context;
    }


    public float checkout(float amount) {
        // Perform other checkout-related logic
        // Use the payment strategy to make the payment
        if (pay != null) {
            return pay.Pay(context,amount);
        } else {
            Toast.makeText(context, "payed faild", Toast.LENGTH_SHORT).show();

            return 0;
        }
    }
}
