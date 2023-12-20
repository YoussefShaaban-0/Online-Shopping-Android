package com.example.charts.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.stratgy.Order;
import com.example.charts.stratgy.PayPalPayment;
import com.example.charts.stratgy.PaymentSystem;

import java.util.Date;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

public class Paypal_page extends AppCompatActivity {

    EditText CartEmail, CartName;
    TextView totalPay;
    Button Submit;
    ImageView backtoChooce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chechout_paypal_page);


        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");

        //get data from back activity
        Intent intent = getIntent();
        String PaymentMethod = intent.getStringExtra("pay");

        Databasemanger db = Databasemanger.getInstance(this);
        float tot = (float) db.getTotalPriceForUser(loggedInUserEmail);
        totalPay = findViewById(R.id.totalPricepaypal);
        //put tatal pay
        totalPay.setText(String.format("%s", tot));

        // initialize
        CartEmail = findViewById(R.id.Pay_Emailpaypal);
        CartName = findViewById(R.id.Pay_Namepaypal);
        Submit = findViewById(R.id.Pay_confirmpaypal);
        backtoChooce = findViewById(R.id.Pay_BackPaypal);


        // on click submit to pay
        Submit.setOnClickListener(v -> {

            String name = CartName.getText().toString();
            String email = CartEmail.getText().toString();
            //Reset error
            CartEmail.setError(null);
            CartEmail.setError(null);


            // Check if any of the fields are empty
            if (name.isEmpty()) {
                CartName.setError("Name is required");
                return;
            }

            // Check if any of the fields are empty
            if (email.isEmpty() || !email.contains("@")) {
                CartEmail.setError("Email is required");
                return;
            }

            PaymentSystem p = new PayPalPayment(name,email);
            showShoeDialog(db,loggedInUserEmail,p,tot);

        });
        backtoChooce.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), choce_payment.class);
            startActivity(intent1);
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
