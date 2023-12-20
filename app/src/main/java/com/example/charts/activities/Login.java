package com.example.charts.activities;

import static com.example.charts.Start.key_ofvalue;
import static com.example.charts.Start.shahardprefrences_name;

import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.charts.Start;
import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

public class Login extends AppCompatActivity {
    DatabaseHelper myDb ;
    EditText editTextEmail, editTextPassword ;
    TextView Forget;
    Button loginButton, registerbtn;
    public static  String userininlogin="userlogin";
    SharedPreferences sharedPreferences;
    Databasemanger databasemanger;
    CheckBox rememberBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        databasemanger=Databasemanger.getInstance(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        Forget = findViewById(R.id.forget_txt);
        registerbtn = findViewById(R.id.registerBtn);
        loginButton = findViewById(R.id.loginButton);
        rememberBox = findViewById(R.id.RemmeberBox);

        // Initialize sharedPreferences
        sharedPreferences = getSharedPreferences(Start.shahardprefrences_name, 0);
        // Retrieve saved preferences
        boolean rememberMe = sharedPreferences.getBoolean(Start.keyRememberMe, false);
        String savedEmail = sharedPreferences.getString(Start.keyUserEmail, "");
        String savedPassword = sharedPreferences.getString(Start.keyUserPassword, "");

        if (rememberMe) {
            // If "Remember Me" is checked, populate the fields with saved data
            editTextEmail.setText(savedEmail);
            editTextPassword.setText(savedPassword);
            rememberBox.setChecked(true);
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if (databasemanger.checkUser(email, password)) {
                    // User exists, login successful
                    Rating rating =Rating.build_rate();
                    rating.setCustomer_email(email);
                    sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean(key_ofvalue,true);
                    editor.commit();

                    SharedPreferences sharedPreferences1=getSharedPreferences(shahardprefrences_name,0);
                    SharedPreferences.Editor edit=sharedPreferences1.edit();
                    edit.putString(userininlogin,email);
                    edit.commit();


                    SharedPreferences sharedPreferences=getSharedPreferences(shahardprefrences_name,0);
                    String loggedInUserEmail = sharedPreferences.getString(userininlogin,"");

                    Toast.makeText(getApplicationContext(), loggedInUserEmail, Toast.LENGTH_SHORT).show();


                    // Intent intent = new Intent(Login.this, Mypersonal_deteils.class);

                    // Intent intent = new Intent(Login.this, Home.class);
                    // Intent intent = new Intent(Login.this, edit_user.class);
                    // intent.putExtra(userinlogin,email);
                    if (rememberBox.isChecked()) {
                        // Save the "Remember Me" checkbox state
                        editor.putBoolean(Start.keyRememberMe, true);
                        editor.putString(Start.keyUserEmail, email);
                        editor.putString(Start.keyUserPassword, password);
                        editor.apply();
                    } else {
                        // Clear any previously saved information
                        edit.remove(Start.keyRememberMe);
                        edit.remove(Start.keyUserPassword);
                        edit.apply();
                    }
                    Intent intent = new Intent(Login.this, Rigster_Succfully.class);
                    startActivity(intent);
                    finish();

                } else {
                    // User does not exist or incorrect credentials
                    Toast.makeText(Login.this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();

                // Check if the entered email is valid (you may want to add further validation)
                if (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Check if the email exists in the database
                    if (databasemanger.checkUser(email, null)) {
                        // Email exists, move to ResetPassword activity
                        Intent intent = new Intent(Login.this, Password_Reset.class);
                        intent.putExtra("user_email", email);
                        startActivity(intent);
                    } else {
                        // Email doesn't exist in the database
                        Toast.makeText(Login.this, "Email not found. Please enter a valid email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Invalid email format
                    Toast.makeText(Login.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

    }
}