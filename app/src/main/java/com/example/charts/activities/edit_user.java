package com.example.charts.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.example.charts.adapters.user_list;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.user_Builder.User;
import com.example.charts.user_Builder.User_factory;

import java.util.Calendar;


public class edit_user extends AppCompatActivity {

    private EditText editTextEmail ;
    private TextView editTextBirthday;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText ssnd, phoned, gendred;
    private Button btnInsertAsAdmin,to_alluser;
    Databasemanger databasemanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);


        DatePickerDialog.OnDateSetListener setListener;

        // Initialize UI elements
        editTextEmail = findViewById(R.id.admnemail);
        editTextUsername = findViewById(R.id.admnUsername);
        editTextPassword = findViewById(R.id.admnPassword);

        editTextConfirmPassword = findViewById(R.id.admnConfirmPassword);
        btnInsertAsAdmin = findViewById(R.id.btnUpdate);
        ssnd = findViewById(R.id.admnssn);
        editTextBirthday = findViewById(R.id.admnbirthday);
        phoned = findViewById(R.id.admnphone);
        gendred = findViewById(R.id.admngndre2);
        to_alluser=findViewById(R.id.manage_user);

        databasemanger=Databasemanger.getInstance(this);


        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        editTextBirthday=(TextView) findViewById(R.id.admnbirthday);

        setListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                editTextBirthday.setText(date);
            }
        };

        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog DateDialog = new DatePickerDialog(edit_user.this,
                        android.R.style.Theme_Holo_Dialog,
                        setListener,year,month,day);
                DateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                DateDialog.show();
            }

        });

        // Set click listener for the button
        btnInsertAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                String ssn = ssnd.getText().toString().trim();
                String phone = phoned.getText().toString().trim();
                String gender = gendred.getText().toString().trim();

                String birthday = editTextBirthday.getText().toString();

                // Validate the data
                if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                        || ssn.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
                    // Display an error message if any field is empty
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    // Handle password mismatch
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(edit_user.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(ssn.length()!=14)
                {
                    Toast.makeText(edit_user.this, "SSN consist of 14 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(phone.length()!=11&&phone.startsWith("01"))
                {
                    Toast.makeText(edit_user.this, "phone number consist of 11 digits and like 01*********", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(password.length()<8)
                {
                    Toast.makeText(edit_user.this, "password at least 8 letters", Toast.LENGTH_SHORT).show();
                }

                // Check if other necessary validations are required

                // Now, insert the user into the database as an admin
                User_factory factory=new User_factory();
                User admin=factory.create_user(username,email,password,"admin",ssn,phone,birthday,gender);
                boolean inserted = databasemanger.addUser(admin);

                if (inserted) {
                    // User inserted successfully
                    Toast.makeText(getApplicationContext(), "User inserted as admin", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed to insert user
                    Toast.makeText(getApplicationContext(), "Failed to insert user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        to_alluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user_list.class));
            }
        });

    }
}