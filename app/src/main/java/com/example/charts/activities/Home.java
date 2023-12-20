package com.example.charts.activities;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;


import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.charts.others.CatagoriesName;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.adapters.RecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;

public class Home extends AppCompatActivity {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView rec_products;
    Databasemanger databasemanger;
    SearchView searchView;
    TextView number_found;
    ImageView editcatagory, allcatagories,mic,qr_image,man_profile;
    BottomNavigationView bottomNavigationView;

    private static final int SPEECH_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rec_products = findViewById(R.id.re1);
        databasemanger = Databasemanger.getInstance(this);
        editcatagory = findViewById(R.id.editcatagory);
        allcatagories = findViewById(R.id.categories);
        number_found = findViewById(R.id.numb);
        searchView = findViewById(R.id.search);
        mic=findViewById(R.id.mic);
        qr_image=findViewById(R.id.qr_image);
        man_profile=findViewById(R.id.man);

        man_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),End_app.class));
                finish();
            }
        });


        allcatagories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CatagoriesName.class));
                finish();
            }
        });

        Cursor cursor = databasemanger.fetch();
        recyclerViewAdapter = new RecyclerViewAdapter(this, cursor);

        if (cursor != null) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
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

        // Set up voice search
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });

        checkCameraPermission();
        qr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQRCodeScanner();
            }
        });


        bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), CartShopping.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                } else if (itemId == R.id.home) {
                    // Do something for the "home" item
                    return true;
                } else if (itemId == R.id.navigation) {
                    // Do something for the "navigation" item
                    return true;
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), End_app.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"no");
        Rating rating=Rating.build_rate();
        rating.setCustomer_email(loggedInUserEmail);
        Toast.makeText(this, loggedInUserEmail, Toast.LENGTH_SHORT).show();

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

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                searchView.setQuery(result.getContents(),true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }

    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Scan a QR code or barcode");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.initiateScan();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // initQRCodeScanner();
            } else {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
