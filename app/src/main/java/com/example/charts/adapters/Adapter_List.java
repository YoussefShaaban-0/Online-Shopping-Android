package com.example.charts.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charts.activities.CartShopping;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

public class Adapter_List extends CursorAdapter {

    public Adapter_List(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.cart_item, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView minus, add, remove;
        TextView numberofInstant;
        Databasemanger db = Databasemanger.getInstance(context);

        TextView cartItemName = view.findViewById(R.id.C_name);
        TextView cartItemDescription = view.findViewById(R.id.C_des);
        TextView cartItemPrice = view.findViewById(R.id.C_price);
        ImageView imgs = view.findViewById(R.id.C_imge);
        TextView cart_sale = view.findViewById(R.id.C_Sale);
        minus = view.findViewById(R.id.C_minus);
        numberofInstant = view.findViewById(R.id.C_count);
        add = view.findViewById(R.id.C_plus);
        remove = view.findViewById(R.id.C_delete);
        // Use getColumnIndexOrThrow to get column indices
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_DES));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_PRICE));
        byte[] img = cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_IMAGE));
        int itemNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        float sale = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_SALE));
        cartItemName.setText(title);
        cartItemDescription.setText(description);
        cartItemPrice.setText(("$"+ price));
        cart_sale.setText((" Sale %"+ sale));
        imgs.setImageBitmap(bitmap);
        numberofInstant.setText(String.valueOf(itemNumber));
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int newItemNumber = (itemNumber - 1);
                if (newItemNumber <= 0) {
                    db.deleteItemFromCart(title);
                    Cursor c =db.fetchCart();
                    changeCursor(c);

                }
                else {
                    Databasemanger dp = Databasemanger.getInstance(context);
                    CartShopping.Total_price+=price;
                    numberofInstant.setText(String.valueOf(newItemNumber));
                    db.updateCartItemNumber(title, newItemNumber);
                    Cursor c =dp.fetchCart();
                    changeCursor(c);
                }
                Intent intent = new Intent(context, CartShopping.class);
                context.startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartShopping.Total_price+=price;
                int newItemNumber = itemNumber + 1;
                numberofInstant.setText(String.valueOf(newItemNumber));
                int itemNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
                db.updateCartItemNumber(title, newItemNumber);
                numberofInstant.setText(String.valueOf(itemNumber));
                Cursor c =db.fetchCart();
                changeCursor(c);
                Intent intent = new Intent(context, CartShopping.class);
                context.startActivity(intent);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.deleteItemFromCart(title);
                    CartShopping.Total_price -= price;
                    // Fetch the updated cart data
                    Cursor updatedCursor = db.fetchCart();
                    // Notify the adapter of the change
                    changeCursor(updatedCursor);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, CartShopping.class);
                context.startActivity(intent);
            }
        });
    }
}
