package com.example.charts.activities;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.key_ofvalue;
import static com.example.charts.Start.shahardprefrences_name;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.charts.Start;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.user_Builder.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class End_app extends AppCompatActivity {
    Button porsnal_details;
    Button settingsButton;
    Button logoutButton;
    private DatabaseHelper myDb;
    Databasemanger databasemanger;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_app);
        porsnal_details =(Button) findViewById(R.id.personal_deteils);
        logoutButton =(Button) findViewById(R.id.logout);
        settingsButton = findViewById(R.id.setting);
        databasemanger=Databasemanger.getInstance(this);
        porsnal_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_app.this, Mypersonal_deteils.class);
                startActivity(intent);
            }
        });

        settingsButton.setVisibility(View.GONE); // Initially, hide the button
        myDb = new DatabaseHelper(this);

        SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin,"");

        Toast.makeText(this, loggedInUserEmail, Toast.LENGTH_SHORT).show();
        // retrieve  usertype from database
        User loggedInUser = databasemanger.retrieveUser(loggedInUserEmail);

        if (loggedInUser != null && "admin".equals(loggedInUser.getUserType())) {
            settingsButton.setVisibility(View.VISIBLE);
        }
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End_app.this, MangeAllProducts.class);
                startActivity(intent);
                finish();
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences  sharedPreferences1;
                sharedPreferences1=getSharedPreferences(shahardprefrences_name,0);
                SharedPreferences.Editor editor=sharedPreferences1.edit();
                editor.putBoolean(key_ofvalue,false);
                editor.remove(userininlogin);
                editor.commit();
                Intent intent = new Intent(End_app.this, Start.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), CartShopping.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation) {
                    // Do something for the "navigation" item
                    return true;
                } else if (itemId == R.id.profile) {

                    return true;
                }
                return false;
            }
        });


    }
}