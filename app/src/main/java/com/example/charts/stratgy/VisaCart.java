package com.example.charts.stratgy;

import android.content.Context;
import android.widget.Toast;

public class VisaCart implements PaymentSystem {

    //Enter the 16-digit card number, expiration date, and the three-digit CVV

    private long CartNumber;
    private String cartexpirationdate;
    private int CVV;

    public VisaCart(long cartNumber, String cartexpirationDate, int CVV) {
        CartNumber = cartNumber;
        cartexpirationdate = cartexpirationDate;
        this.CVV = CVV;
    }

    public long getCartNumber() {
        return CartNumber;
    }

    public void setCartNumber(long cartNumber) {
        CartNumber = cartNumber;
    }

    public String getCartexpirationdate() {
        return cartexpirationdate;
    }

    public void setCartxpirationDate(String cartexpirationDate) {
        cartexpirationdate = cartexpirationDate;
    }

    public int getCVV() {
        return CVV;
    }

    public void setCVV(int CVV) {
        this.CVV = CVV;
    }

    @Override
    public float Pay(Context context, float price) {
        Toast.makeText( context,"Pay with Visa  ",Toast.LENGTH_SHORT).show();
        return 4 + price;
    }

}
