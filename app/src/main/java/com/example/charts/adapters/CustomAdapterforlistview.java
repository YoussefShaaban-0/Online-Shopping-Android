package com.example.charts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.charts.R;

public class CustomAdapterforlistview extends BaseAdapter {

    private Context context;
    private String[] stringArray;

    public CustomAdapterforlistview(Context context, String[] stringArray) {
        this.context = context;
        this.stringArray = stringArray;
    }

    @Override
    public int getCount() {
        return stringArray.length;
    }

    @Override
    public Object getItem(int position) {
        return stringArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the layout for each list item if it's not recycled
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.catagorescell, parent, false);

            // Create a ViewHolder and store references to the child views
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.catagoryname);

            // Associate the ViewHolder with the convertView
            convertView.setTag(holder);
        } else {
            // If convertView is not null, reuse it and get the ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Bind data to the views
        holder.textView.setText(stringArray[position]);

        return convertView;
    }

    // ViewHolder pattern to improve performance by caching views
    private static class ViewHolder {
        TextView textView;
    }
}