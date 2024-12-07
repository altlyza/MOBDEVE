package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Initialize DatabaseHandler
        dbHandler = new DatabaseHandler(this);

        // Bind views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        // Handle login button click
        findViewById(R.id.loginButton).setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate the user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Save the logged-in user's email to the database
                            saveUserEmailToDatabase(email);

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Pass the email to the next activity
                            Intent intent = new Intent(LoginActivity.this, ExploreActivity.class);
                            intent.putExtra("USER_EMAIL", email);
                            startActivity(intent);
                            finish();
                        } else {
                            String errorMessage = task.getException() != null
                                    ? task.getException().getMessage()
                                    : "Login failed.";
                            Toast.makeText(LoginActivity.this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Handle register link click
        findViewById(R.id.registerLink).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    /**
     * Save the logged-in user's email to the SQLite database if not already present.
     */
    private void saveUserEmailToDatabase(String email) {
        try {
            // Check if the email already exists in the database
            Cursor cursor = dbHandler.getUserByEmail(email);
            if (cursor != null && cursor.moveToFirst()) {
                // User already exists, log this information
                Toast.makeText(this, "User already exists in the database.", Toast.LENGTH_SHORT).show();
                android.util.Log.d("LoginActivity", "Email already exists: " + email);
                cursor.close();
            } else {
                // Add the user to the database
                dbHandler.addUser(email, ""); // Empty password for simplicity, adjust as needed
                Toast.makeText(this, "Email added to database.", Toast.LENGTH_SHORT).show();
                android.util.Log.d("LoginActivity", "Email added to database: " + email);
            }
        } catch (Exception e) {
            // Log and display any errors
            android.util.Log.e("LoginActivity", "Error saving email to database", e);
            Toast.makeText(this, "Error saving email to database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
