package com.example.charts.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.R;

public class Adapterlist extends CursorAdapter {


    public Adapterlist(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.catagorescell,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView catagoryname = view.findViewById(R.id.catagoryname);
        // Use getColumnIndexOrThrow to get column indices
        int titleColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.catagoryname);
        String title = cursor.getString(titleColumnIndex);
        catagoryname.setText(title);
    }
}
