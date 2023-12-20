package com.example.charts.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charts.R;
import com.example.charts.activities.EditCatagory;
import com.example.charts.activities.MangeAllProducts;
import com.example.charts.adapters.Adapterlist;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;

public class InsertNewCatagory extends AppCompatActivity {

   EditText newcatagory;
   ListView catagorys;
   TextView emptylist;

   ImageView goto_manage;

   Button addnewcatagory;


   public static final String key_of_catagory_name="this catagory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_catagory);

        newcatagory=findViewById(R.id.catagoryonadd);
        catagorys=findViewById(R.id.listofcatagory);
        addnewcatagory=findViewById(R.id.addcatagory);
        emptylist=findViewById(R.id.emptylist);
        goto_manage=findViewById(R.id.arrow_back);


        Databasemanger databasemanger=Databasemanger.getInstance(this);

        Cursor cursor= databasemanger.fetchcatagory();

        Adapterlist adapterlist=new Adapterlist(this,cursor);
        catagorys.setAdapter(adapterlist);
        catagorys.setEmptyView(emptylist);
        catagorys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catagory_selected=cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.catagoryname));
                Intent to_edit=new Intent(getApplicationContext(), EditCatagory.class);
                to_edit.putExtra(key_of_catagory_name,catagory_selected);
                startActivity(to_edit);
            }
        });


        addnewcatagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newcatagoryy=newcatagory.getText().toString().trim();

                newcatagory.setError(null);
                if (newcatagoryy.isEmpty()) {
                    newcatagory.setError("Category is required");
                    return;
                }else {

                    if(!databasemanger.isCategoryExists(newcatagoryy)){

                        databasemanger.inserttdataoncatagory(newcatagoryy);
                        Toast.makeText(InsertNewCatagory.this, "New catagory has been add", Toast.LENGTH_SHORT).show();
                        newcatagory.setText("");

                        Cursor update_cursor= databasemanger.fetchcatagory();
                        adapterlist.changeCursor(update_cursor);
                        catagorys.setAdapter(adapterlist);
                    }
                    else {
                        Toast.makeText(InsertNewCatagory.this, "This catagory is already found in database", Toast.LENGTH_SHORT).show();

                    }
                    adapterlist.notifyDataSetChanged();
                    catagorys.setAdapter(adapterlist);

                }

            }
        });
        goto_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MangeAllProducts.class));
                finish();
            }
        });

    }
}