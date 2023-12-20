package com.example.charts.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.charts.activities.Home;
import com.example.charts.activities.ShowProductDetails;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.activities.EditProduct;
import com.example.charts.activities.ProductForAdmin;
import com.example.charts.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    private final Context context;
    private Cursor cursor;
    private Cursor cursorFiltered;

    Databasemanger databasemanger;

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.cursorFiltered = cursor; // Initialize with the original cursor

        databasemanger=Databasemanger.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardofproduct, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!cursorFiltered.moveToPosition(position)) {
            return;
        }

        String title = cursorFiltered.getString(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.titel));
        byte[] imageData = cursorFiltered.getBlob(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.image));
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        float s = cursorFiltered.getFloat(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.sale));
        long id = cursorFiltered.getLong(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.id));
        String description = cursorFiltered.getString(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.category));
        int salary = cursorFiltered.getInt(cursorFiltered.getColumnIndexOrThrow(DatabaseHelper.salary));

        holder.setdat(title, bitmap, description, salary ,s) ;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Home) {
                    Intent intent = new Intent(context, ShowProductDetails.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                } else if (context instanceof ProductForAdmin) {
                    Intent intent = new Intent(context, EditProduct.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursorFiltered.getCount();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();
                FilterResults filterResults = new FilterResults();

                List<Integer> filteredIds = new ArrayList<>();

                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.titel));
                        String qr_code=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.qr_code));
                        if (title.toLowerCase().startsWith(filterPattern)||qr_code.toLowerCase().startsWith(filterPattern)) {
                            filteredIds.add(cursor.getPosition());
                        }
                    } while (cursor.moveToNext());
                }

                filterResults.values = filteredIds;
                filterResults.count = filteredIds.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<Integer> filteredIds = (List<Integer>) results.values;

                cursorFiltered = createFilteredCursor(filteredIds);
                notifyDataSetChanged();
            }
        };
    }

    private Cursor createFilteredCursor(List<Integer> filteredIds) {
        // Create a matrix cursor with the same columns
        String[] columnNames = cursor.getColumnNames();
        android.database.MatrixCursor filteredCursor = new android.database.MatrixCursor(columnNames);

        // Iterate through the filtered IDs and add corresponding rows to the new cursor
        for (int position : filteredIds) {
            cursor.moveToPosition(position);
            Object[] rowData = new Object[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (cursor.getType(i) == Cursor.FIELD_TYPE_BLOB) {
                    // Handle BLOB data separately
                    rowData[i] = cursor.getBlob(i);
                } else {
                    rowData[i] = cursor.getString(i);
                }
            }
            filteredCursor.addRow(rowData);
        }

        return filteredCursor;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titell, description, salary ,sale;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgoncard);
            titell = itemView.findViewById(R.id.titeloncard);
            description = itemView.findViewById(R.id.descreptiononcard);
            salary = itemView.findViewById(R.id.salaryoncard);
            sale = itemView.findViewById(R.id.saleoncard);
        }

        public void setdat(String titel, Bitmap bitmap, String des, int salaryy , float sales) {
            imageView.setImageBitmap(bitmap);
            titell.setText(titel);
            description.setText(des);
            salary.setText("$"+salaryy);
            sale.setText("Sale $"+sales);
        }
    }
}
