package com.example.charts.activities;

import static com.example.charts.activities.Login.userininlogin;
import static com.example.charts.Start.shahardprefrences_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;
import com.example.charts.user_Builder.User;

public class Mypersonal_deteils extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextChangePassword,
            editTextSSN, editTextPhone, editTextGender;
    private Button updateButton;
    private DatabaseHelper myDb;
    Databasemanger databasemanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypersonal_deteils);

        // Initialize Databasemanger
        databasemanger = Databasemanger.getInstance(this);

        editTextUsername = findViewById(R.id.pUsername);
        editTextPassword = findViewById(R.id.pPassword);
        editTextChangePassword = findViewById(R.id.changePassword);
        editTextSSN = findViewById(R.id.pssn);
        editTextPhone = findViewById(R.id.phone);
        editTextGender = findViewById(R.id.gender);
        updateButton = findViewById(R.id.update);

        SharedPreferences sharedPreferences = getSharedPreferences(shahardprefrences_name, 0);
        String loggedInUserEmail = sharedPreferences.getString(userininlogin, "");
        User user = databasemanger.retrieveUser(loggedInUserEmail);

        if (user != null) {
            editTextUsername.setText(user.getUsername());
            editTextPassword.setText(user.getPassword());
            editTextSSN.setText(user.getSsn());
            editTextPhone.setText(user.getPhone());
            editTextGender.setText(user.getGender());
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newUsername = editTextUsername.getText().toString();
                String newPassword = editTextChangePassword.getText().toString();
                String newSSN = editTextSSN.getText().toString();
                String newPhone = editTextPhone.getText().toString();
                String newbirthday = editTextPhone.getText().toString();
                String newGender = editTextGender.getText().toString();

                // update in the database
                boolean updateSuccessful = databasemanger.updateUser(loggedInUserEmail, newUsername, newPassword, newSSN, newPhone,newbirthday, newGender);

                if (updateSuccessful) {
                    // Update successful
                    Toast.makeText(Mypersonal_deteils.this, "Update successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Update failed
                    Toast.makeText(Mypersonal_deteils.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
