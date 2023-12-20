package com.example.charts.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;


public class Password_Reset extends AppCompatActivity {
    DatabaseHelper myDb ;
    Databasemanger databasemanger;
    EditText newPasswordEditText, confirmPasswordEditText;
    Button resetPasswordButton;
    TextView emailValueTextView;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        myDb = new DatabaseHelper(this);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        emailValueTextView = findViewById(R.id.emailValueTextView);
        databasemanger=Databasemanger.getInstance(this);
        // Retrieve the user email from the intent
        userEmail = getIntent().getStringExtra("user_email");

        // Set the email in the TextView
        emailValueTextView.setText(userEmail);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the new password and confirmation from the EditTexts
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                // Check if passwords match
                if (newPassword.equals(confirmPassword)) {
                    // Update the password in the database
                    if (databasemanger.updatePassword(userEmail, newPassword)) {
                        // Password updated successfully
                        Toast.makeText(Password_Reset.this, "Password reset successfully.", Toast.LENGTH_SHORT).show();

                        // Move to the login activity
                        Intent intent = new Intent(Password_Reset.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Failed to update password
                        Toast.makeText(Password_Reset.this, "Failed to reset password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Passwords don't match
                    Toast.makeText(Password_Reset.this, "Passwords do not match. Please enter them again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

