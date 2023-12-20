package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.charts.R;

public class Rigster_Succfully extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigster_succfully);
        Button login = findViewById(R.id.btnSignUp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Rigster_Succfully.this, Home.class);
                startActivity(intent);
                finish();
            }
        });


    }
}