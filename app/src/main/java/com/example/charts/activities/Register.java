package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.user_Builder.User;
import com.example.charts.user_Builder.User_factory;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    DatabaseHelper myDb;
    Databasemanger databasemanger;
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword,ssn,phone,gendre;
    Button signUpButton;
    TextView birthdayTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databasemanger= Databasemanger.getInstance(this);
        myDb =new DatabaseHelper(this);
        DatePickerDialog.OnDateSetListener setListener;
        editTextUsername =(EditText) findViewById(R.id.editTextUsername);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText) findViewById(R.id.editTextconfirmPassword);
        ssn =(EditText) findViewById(R.id.ssn);
        phone =(EditText)findViewById(R.id.phone) ;
        gendre =(EditText)findViewById(R.id.gndre) ;
        signUpButton =(Button) findViewById(R.id.loginButton);
        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        birthdayTextView=(TextView) findViewById(R.id.birthdayTextView);

        setListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                birthdayTextView.setText(date);
            }
        };

        birthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog DateDialog = new DatePickerDialog(Register.this,
                        android.R.style.Theme_Holo_Dialog,
                        setListener,year,month,day);
                DateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                DateDialog.show();
            }

        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                String ssnValue = ssn.getText().toString().trim();
                String phoneValue = phone.getText().toString().trim();
                String birthDayValue =birthdayTextView.getText().toString().trim();
                String genderValue = gendre.getText().toString().trim();

                User_factory factory = new User_factory();
                User Customer = factory.create_user(username, email, password, "Customer", ssnValue, phoneValue,birthDayValue, genderValue);
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || ssnValue.isEmpty() || phoneValue.isEmpty() || birthDayValue.isEmpty() || genderValue.isEmpty()) {
                    // Display an error message if any field is empty
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();

                }
                else
                {  if (password.equals(confirmPassword)) {
                    if (!databasemanger.checkUser(email, password)) {
                        boolean isInserted = databasemanger.addUser(Customer);
                        if (isInserted) {
                            // Registration successful
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Registration failed
                            Toast.makeText(Register.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        // User already exists with the provided email
                        Toast.makeText(Register.this, "User with this email already exists. Please use a different email.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    // Passwords do not match
                    Toast.makeText(Register.this, "Passwords do not match. Please check again.", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }


}