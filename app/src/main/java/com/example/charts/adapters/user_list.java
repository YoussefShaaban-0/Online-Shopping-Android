package com.example.charts.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.example.charts.dataBase.DatabaseHelper;
import com.example.charts.dataBase.Databasemanger;
import com.example.charts.R;

import java.util.List;

public class user_list extends AppCompatActivity {
    private ListView userListView;
    private ArrayAdapter<String> adapter;

    Databasemanger databasemanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userListView = findViewById(R.id.userListView);

        databasemanger=Databasemanger.getInstance(this);

        // Set up the user list
        displayUsernames();

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click (modification or deletion) logic
                showOptionsDialog(position);
            }
        });
    }

    private void displayUsernames() {
        // Assuming you have a method in DatabaseHelper to retrieve all usernames
        List<String> usernames = databasemanger.getAllUsernames();
        // Populate the ListView with usernames
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        userListView.setAdapter(adapter);
    }

    private void showOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");

        builder.setItems(new CharSequence[]{"Modify", "Delete"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Modify
                    String selectedUsername = adapter.getItem(position);
                    showModifyDialog(selectedUsername);
                } else if (which == 1) {
                    // Delete
                    String selectedUsername = adapter.getItem(position);
                    deleteUser(selectedUsername);
                }
            }
        });

        builder.show();
    }

    private void showModifyDialog(final String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Modify Username");

        // Set up the layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.modify_user_dialog, null);
        builder.setView(view);

        // Get references to EditText fields in the dialog layout
        final EditText usernameEditText = view.findViewById(R.id.usernameEditText);

        // Pre-fill the EditText field with the existing username (if available)
        if (username != null) {
            usernameEditText.setText(username);
        }

        builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // Get the new username from the input field
                String newUsername = usernameEditText.getText().toString();

                // Modify the username in the database
                boolean updated = databasemanger.updateUsername(username, newUsername);

                if (updated) {
                    // Refresh the user list after modification
                    displayUsernames();
                    Toast.makeText(user_list.this, "Username modified successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(user_list.this, "Failed to modify username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteUser(final String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this user?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the user from the database
                DatabaseHelper dbHelper = new DatabaseHelper(user_list.this);
                boolean deleted = databasemanger.deleteUserByUsername(username);

                if (deleted) {
                    // Refresh the user list after deletion
                    displayUsernames();
                    Toast.makeText(user_list.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(user_list.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", null);
        builder.show();
    }
}