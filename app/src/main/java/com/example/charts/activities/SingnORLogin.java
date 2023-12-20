package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.charts.R;
import com.example.charts.others.SliderOpenning;


public class SingnORLogin extends AppCompatActivity {
    Button login, sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn_orlogin);
        login = findViewById(R.id.btnLogin);
        sign_up = findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingnORLogin.this, Login.class);

                startActivity(intent);
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingnORLogin.this, SliderOpenning.class);
                startActivity(intent);
            }
        });
    }
}