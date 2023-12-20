package com.example.charts.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charts.others.CatagoriesName;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

import java.util.ArrayList;
import java.util.Locale;

public class AllCatagorySelected extends AppCompatActivity {

    RecyclerViewAdapter  recyclerViewAdapter;
    RecyclerView rec_products;
    Databasemanger databasemanger;
    SearchView searchView;

    ImageView arroback,mic;
    TextView catagory_text,number_found;
    private static final int SPEECH_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_catagory_selected);



        rec_products=findViewById(R.id.rec1);
        databasemanger=Databasemanger.getInstance(this);
        catagory_text=findViewById(R.id.catagory_text);
        arroback=findViewById(R.id.arrow_back);
        searchView = findViewById(R.id.search);
        number_found=findViewById(R.id.numb);
        mic=findViewById(R.id.mic);


        arroback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CatagoriesName.class));
                finish();
            }
        });


        Intent from_catagory=getIntent();
        String catagory_name=from_catagory.getStringExtra(CatagoriesName.catagory_name_key);

        Cursor cursor=databasemanger.fetchProductsByCategory(catagory_name);

        recyclerViewAdapter=new RecyclerViewAdapter(this,cursor);
        catagory_text.setText(catagory_name);



        if (cursor != null) {

            StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
            rec_products.setAdapter(recyclerViewAdapter);
            rec_products.setLayoutManager(staggeredGridLayoutManager);


            // Check if the data list is empty
            if (cursor != null && cursor.getCount() > 0) {
                // If the cursor is not null and has items, show the RecyclerView
                rec_products.setVisibility(View.VISIBLE);
            } else {
                // If the cursor is either null or empty, show the empty view
                rec_products.setVisibility(View.GONE);
            }


        }



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.getFilter().filter(newText);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        number_found.setText("Founded " + recyclerViewAdapter.getItemCount());
                    }
                }, 1000);
                return false;
            }
        });


        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });



    }



    // Start voice recognition intent
    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0);
                searchView.setQuery(spokenText, true); // Set the voice result as the query
            }
        }
    }
}