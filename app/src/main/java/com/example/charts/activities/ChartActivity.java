package com.example.charts.activities;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;

    Databasemanger database;
    ArrayList<String> productsName;
    ArrayList<Integer> productsSales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        database= Databasemanger.getInstance(this);
        productsName=new ArrayList<>();
        productsSales=new ArrayList<>();
        Cursor cursor=database.productsSales();
        productsName.clear();  // Clear existing data
        productsSales.clear();  // Clear existing data

        try {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ORDER_NAME));
                int orderDate = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_ITEMS));
                productsName.add(productName);
                productsSales.add(orderDate);
            }
        } finally {
            cursor.close();  // Close the cursor to avoid memory leaks
        }
        barChart=findViewById(R.id.bar_char);
        pieChart=findViewById(R.id.pie_char);
        //array list
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        ArrayList<PieEntry> pieEntries=new ArrayList<>();
        for (int i=0;i<cursor.getCount();i++){
            int orderCount = productsSales.get(i);
            BarEntry barEntry=new BarEntry(i,orderCount);
            barEntries.add(barEntry);
            PieEntry pieEntry=new PieEntry(productsSales.get(i),productsName.get(i));
            pieEntries.add(pieEntry);
        }
        //bar dataset
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"best selle products");
        BarDataSet barDataSet=new BarDataSet(barEntries,"best seller products");
        barDataSet.setValues(barEntries);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        // draw values
        barDataSet.setDrawValues(true);
        pieDataSet.setDrawValues(true);
        //set bar

        barChart.setData(new BarData(barDataSet));
        pieChart.setData(new PieData(pieDataSet));
        //animation
       // barChart.animateY(5000);
        //disc text
        barChart.getDescription().setText("Best seller products");
        barChart.getDescription().setTextColor(Color.BLUE);
        barChart.getDescription().setTextSize(12);
        barChart.getDescription().setPosition((int) 1,(int) 2);
        pieChart.getDescription().setText("Best seller products");
        pieChart.getDescription().setTextColor(Color.BLUE);
        pieChart.getDescription().setTextSize(12);
        pieChart.getDescription().setPosition((int) 1,(int) 2);
        pieDataSet.setValueTextColor(Color.BLACK);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(productsName));
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(productsName.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        pieChart.animateXY(2000,2000);
        barChart.invalidate();
        pieChart.invalidate();
    }
}