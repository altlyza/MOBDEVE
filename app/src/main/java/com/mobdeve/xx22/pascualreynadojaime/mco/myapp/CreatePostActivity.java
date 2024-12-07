package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreatePostActivity extends AppCompatActivity {

    private String postType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Retrieve post type from the intent
        postType = getIntent().getStringExtra("POST_TYPE");

        // Show a message or update the UI based on the post type
        if (postType != null) {
            Toast.makeText(this, "Creating a " + postType + " post", Toast.LENGTH_SHORT).show();

            TextView titleTextView = findViewById(R.id.textViewPostTypeTitle);
            if (postType.equals("Event")) {
                titleTextView.setText("Create an Event Post");
                // Customize layout or functionality for event posts
            } else if (postType.equals("Normal")) {
                titleTextView.setText("Create a Normal Post");
                // Customize layout or functionality for normal posts
            }
        }
    }
}
