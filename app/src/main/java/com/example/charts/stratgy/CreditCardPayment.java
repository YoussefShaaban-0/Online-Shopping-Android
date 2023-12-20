package com.example.charts.stratgy;

import android.content.Context;
import android.widget.Toast;

public class CreditCardPayment implements PaymentSystem {

    private String Name;
    private long CardNumber;
    private String CartExpiry;
    private int CVV;

    public CreditCardPayment(String name, long cardNumber, String cartExpiry, int CVV) {
        Name = name;
        CardNumber = cardNumber;
        CartExpiry = cartExpiry;
        this.CVV = CVV;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(long cardNumber) {
        CardNumber = cardNumber;
    }


    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    @Override
    public float Pay(Context context , float price) {
        Toast.makeText( context,"Pay with CreditCart",Toast.LENGTH_SHORT).show();
        return  5+price;
    }

    public String getCartExpiry() {
        return CartExpiry;
    }

    public void setCartExpiry(String cartExpiry) {
        CartExpiry = cartExpiry;
    }
}
