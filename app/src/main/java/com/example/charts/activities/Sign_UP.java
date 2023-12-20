package com.example.charts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.user_Builder.User;
import com.example.charts.user_Builder.User_factory;

public class Sign_UP extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword,ssn,phone,gendre;
    Button signUpButton;
    Databasemanger databasemanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb =new DatabaseHelper(this);

        editTextUsername =(EditText) findViewById(R.id.editTextUsername);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText) findViewById(R.id.editTextconfirmPassword);
        ssn =(EditText) findViewById(R.id.ssn);
        phone =(EditText)findViewById(R.id.phone) ;
        gendre =(EditText)findViewById(R.id.gndre) ;
        signUpButton =(Button) findViewById(R.id.loginButton);
        databasemanger=Databasemanger.getInstance(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                String ssnValue = ssn.getText().toString().trim();
                String phoneValue = phone.getText().toString().trim();
                String birthdayValue = phone.getText().toString().trim();
                String genderValue = gendre.getText().toString().trim();
                User_factory factory = new User_factory();
                User Customer = factory.create_user(username, email, password, "Customer", ssnValue, phoneValue,birthdayValue, genderValue);
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || ssnValue.isEmpty() || phoneValue.isEmpty() || genderValue.isEmpty()) {
                    // Display an error message if any field is empty
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()&&ssnValue.length()==14&&phoneValue.length()==11&&password.length()>=8)
                    {

                        if (password.equals(confirmPassword)) {
                            if (!databasemanger.checkUser(email, password)) {
                                boolean isInserted = databasemanger.addUser(Customer);
                                if (isInserted) {
                                    // Registration successful
                                    Intent intent = new Intent(Sign_UP.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Registration failed
                                    Toast.makeText(Sign_UP.this, "Invalid username or Email.", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                // User already exists with the provided email
                                Toast.makeText(Sign_UP.this, "User with this email already exists. Please use a different email.", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // Passwords do not match
                            Toast.makeText(Sign_UP.this, "Passwords do not match. Please check again.", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        Toast.makeText(Sign_UP.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    }
                    else if(ssnValue.length()!=14)
                    {
                        Toast.makeText(Sign_UP.this, "SSN consist of 14 digits", Toast.LENGTH_SHORT).show();
                    }
                    else if(phoneValue.length()!=11&&phoneValue.startsWith("01"))
                    {
                        Toast.makeText(Sign_UP.this, "phone number consist of 11 digits and like 01*********", Toast.LENGTH_SHORT).show();
                    }


                    else if(password.length()<8)
                    {
                        Toast.makeText(Sign_UP.this, "password at least 8 letters", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}