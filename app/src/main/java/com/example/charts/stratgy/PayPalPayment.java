package com.example.charts.stratgy;

import android.content.Context;
import android.widget.Toast;

public class PayPalPayment implements PaymentSystem {

    private String name;
    private String email;


    @Override
    public float Pay(Context context,float price) {
        Toast.makeText( context,"Pay with PayPal",Toast.LENGTH_SHORT).show();
        return  3 + price;
    }


    public PayPalPayment(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
