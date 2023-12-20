package com.example.charts.activities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

public class OrderList extends CursorAdapter {

    public OrderList(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.all_ordes, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Databasemanger db = Databasemanger.getInstance(context);

        TextView useremail = view.findViewById(R.id.O_email);
        TextView orderitemname = view.findViewById(R.id.O_name);
        TextView orderitem = view.findViewById(R.id.O_item);
        TextView orderdate = view.findViewById(R.id.O_date);
        ImageView deletorde = view.findViewById(R.id.btnDelete);

        // Use getColumnIndexOrThrow to get column indices
        String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_USER_EMAIL));
        String titel = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ORDER_NAME));
        int item = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_ITEMS));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_DATE));
        useremail.setText("User Email : "+email);
        orderitemname.setText("Product Name: "+titel);
        orderitem.setText(("Item Number :  "+ item));
        orderdate.setText(("Date   :  "+ date));

        deletorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOrder(email);
                Cursor c =db.getAllOrders();
                changeCursor(c);
                notifyDataSetChanged();
            }
        });
    }
}
