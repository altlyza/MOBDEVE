package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class  MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // User is already logged in, navigate to ExploreActivity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            // User is not logged in, navigate to LoginActivity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish();
    }
}
