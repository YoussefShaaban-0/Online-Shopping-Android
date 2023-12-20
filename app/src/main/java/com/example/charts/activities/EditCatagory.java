package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.charts.dataBase.Databasemanger;
import com.example.charts.others.InsertNewCatagory;
import com.example.charts.R;

public class EditCatagory extends AppCompatActivity {

    EditText catagore_edittext;
    ImageView to_insert_catagory;
    Button update,delet;
    Databasemanger databasemanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catagory);
        catagore_edittext=findViewById(R.id.catagoryonedit);
        update=findViewById(R.id.updatecatagory);
        delet=findViewById(R.id.deletcatagory);
        to_insert_catagory=findViewById(R.id.arrow_back);

        databasemanger=Databasemanger.getInstance(this);

        Intent from_insert_catagory=getIntent();
        String old_catagory=from_insert_catagory.getStringExtra(InsertNewCatagory.key_of_catagory_name);
        catagore_edittext.setText(old_catagory);
        catagore_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                update.setEnabled(!catagore_edittext.getText().toString().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(update.isEnabled()){
                    String new_catagory=catagore_edittext.getText().toString().trim();
                   if( !databasemanger.isCategoryExists(new_catagory)){
                       if((databasemanger.updateDataonCatagory(old_catagory,new_catagory)==1)&&databasemanger.updateDataonAlldata(old_catagory,new_catagory)==1){
                           Toast.makeText(EditCatagory.this, "Data has been update", Toast.LENGTH_SHORT).show();

                       }
                       else{
                           Toast.makeText(EditCatagory.this, "Data has not been update", Toast.LENGTH_SHORT).show();

                       }
                   }
                   else {
                       Toast.makeText(EditCatagory.this, "Is catagory is already found ", Toast.LENGTH_SHORT).show();
                   }


                }}
        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowsDeletedCatagores = databasemanger.deleteDataByCategoryFromCatagores(old_catagory);
                int rowsDeletedAll = databasemanger.deleteDataByCategoryFromall(old_catagory);

                Log.d("DeleteOperation", "Rows deleted from Catagores: " + rowsDeletedCatagores);
                Log.d("DeleteOperation", "Rows deleted from All: " + rowsDeletedAll);

                if (rowsDeletedCatagores > 0 || rowsDeletedAll > 0) {
                    startActivity(new Intent(getApplicationContext(), InsertNewCatagory.class));
                    finish();
                    Toast.makeText(EditCatagory.this, "Category has been deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditCatagory.this, "Deletion failed or no data to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });


        to_insert_catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InsertNewCatagory.class));
                finish();
            }
        });















    }
}