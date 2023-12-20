package com.example.charts.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.stratgy.*;

import java.util.Date;

import static com.example.charts.activities.CartShopping.chart_cursor;
import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

public class CheckoutActivity extends AppCompatActivity {
    EditText cartNumber, cartName, cartM, cartY, cartCVV;
    TextView totalPay;
    Button submit;
    ImageView backToChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_criditcartpage);

        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");
        // Get data from the previous activity
        Intent intent = getIntent();
        String paymentMethod = intent.getStringExtra("pay");
        Databasemanger db = Databasemanger.getInstance(this);
        float total = (float) db.getTotalPriceForUser(loggedInUserEmail);

        totalPay = findViewById(R.id.totalPrice);
        totalPay.setText(String.format("%s", total));

        cartNumber = findViewById(R.id.Pay_Number);
        cartName = findViewById(R.id.Pay_Name);
        cartM = findViewById(R.id.Pay_expiryM);
        cartY = findViewById(R.id.Pay_expiryY);
        cartCVV = findViewById(R.id.Pay_CVV);
        submit = findViewById(R.id.Pay_confirm);
        backToChoice = findViewById(R.id.Pay_Back);

        backToChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), choce_payment.class);
                startActivity(intent1);
            }
        });

        submit.setOnClickListener(v -> {
            if (total != 0) {
                String dateStringM = cartM.getText().toString();
                String dateStringY = cartY.getText().toString();
                String cNumber = cartNumber.getText().toString();
                String cartNameText = cartName.getText().toString();
                String cvv = cartCVV.getText().toString();

                // Validation checks
                if (cartNameText.isEmpty()) {
                    cartName.setError("Name is required");
                    return;
                }

                if (cNumber.isEmpty()) {
                    cartNumber.setError("Card Number is required");
                    return;
                }

                if (dateStringM.isEmpty()) {
                    cartM.setError("Expiry Month is required");
                    return;
                }
                if (dateStringY.isEmpty()) {
                    cartY.setError("Expiry Year is required");
                    return;
                }

                if (cvv.isEmpty()) {
                    cartCVV.setError("CVV is required");
                    return;
                }

                if (cNumber.length() != 16) {
                    Toast.makeText(getApplicationContext(), "Card Number should be 16 digits", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (paymentMethod.equals("visa")) {
                    PaymentSystem p = new VisaCart(Long.parseLong(cNumber), dateStringY, Integer.parseInt(cvv));
                    showShoeDialog(db,loggedInUserEmail ,p,total);
                    if(chart_cursor!=null){
                        Toast.makeText(this, "full cursor ", Toast.LENGTH_SHORT).show();
                    db.updateProductItemNumber(chart_cursor);}
                    else {
                        Toast.makeText(this, "empty cursor ", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    PaymentSystem p = new CreditCardPayment(cartNameText,Long.parseLong(cNumber), dateStringY, Integer.parseInt(cvv));
                    showShoeDialog(db,loggedInUserEmail ,p,total);
                    if(chart_cursor!=null){
                        Toast.makeText(this, "full cursor ", Toast.LENGTH_SHORT).show();
                        db.updateProductItemNumber(chart_cursor);}
                    else {
                        Toast.makeText(this, "empty cursor ", Toast.LENGTH_SHORT).show();
                    }


                }
            }
            else {
                Intent intent1 = new Intent(getBaseContext(), choce_payment.class);
                startActivity(intent1);
            }
        });
    }
    private void showShoeDialog(Databasemanger db , String Email , PaymentSystem p , float total) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Succssful payed")
                .setMessage("We recive oeder after 2 day ")
                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int items = db.getTotalItemForUser(Email);
                        Date currentDate = new Date();
                        Cursor cursor = db.fetchCart();
                        while (cursor.moveToNext()) {
                            String ti = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_NAME));
                            db.insertOrder(Email, ti, items,currentDate);
                        }
                        Order order = new Order(p, getApplicationContext());
                        float x = order.checkout(total);
                        Toast.makeText( getApplicationContext(),"Payed successully",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteOrder(Email);
                        Toast.makeText( getApplicationContext(),"Canseld successully",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
