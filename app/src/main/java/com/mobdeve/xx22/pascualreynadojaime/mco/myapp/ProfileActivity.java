package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Find the userName TextView
        TextView userName = findViewById(R.id.userName);

        // Retrieve the email passed from the Intent
        String email = getIntent().getStringExtra("USER_EMAIL");
        if (email != null) {
            userName.setText(email);
            Log.d("ProfileActivity", "Email received: " + email);
        } else {
            userName.setText("Guest");
            Log.e("ProfileActivity", "No email received");
        }

        // Initialize the DatabaseHandler
        dbHandler = new DatabaseHandler(this);

        // Initialize RecyclerView for reserved bars
        RecyclerView reservedBarsRecyclerView = findViewById(R.id.reservedBarsRecyclerView);
        reservedBarsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load reservations and set up the adapter
        List<Reservation> reservations = loadUserReservations(email);
        ReservedBarAdapter adapter = new ReservedBarAdapter(this, reservations, reservation -> showReservationDetails(reservation));
        reservedBarsRecyclerView.setAdapter(adapter);

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the current tab as selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        // Handle navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_explore) {
                // Pass the email back to ExploreActivity
                Intent intent = new Intent(ProfileActivity.this, ExploreActivity.class);
                intent.putExtra("USER_EMAIL", email); // Pass email back
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Already on Profile; do nothing
                return true;
            }

            return false;
        });
    }

    /**
     * Load reservations from the database for the given user email.
     *
     * @param email User's email address.
     * @return List of reservations.
     */
    private List<Reservation> loadUserReservations(String email) {
        List<Reservation> reservations = new ArrayList<>();

        // Fetch the user ID using the email
        Cursor userCursor = dbHandler.getUserByEmail(email);
        if (userCursor != null && userCursor.moveToFirst()) {
            @SuppressLint("Range") int userId = userCursor.getInt(userCursor.getColumnIndex("user_id"));
            userCursor.close();

            // Fetch reservations for the user
            Cursor reservationCursor = dbHandler.getReservationsByUserId(userId);
            if (reservationCursor != null) {
                while (reservationCursor.moveToNext()) {
                    @SuppressLint("Range") String barName = reservationCursor.getString(reservationCursor.getColumnIndex("bar_name"));
                    @SuppressLint("Range") String date = reservationCursor.getString(reservationCursor.getColumnIndex("date"));
                    @SuppressLint("Range") int pax = reservationCursor.getInt(reservationCursor.getColumnIndex("pax"));

                    // Fetch profile picture from the TABLE_POSTS using barName
                    Cursor postCursor = dbHandler.getPostByBarName(barName);
                    int profilePicture = R.drawable.post_apotheka; // Default image
                    if (postCursor != null && postCursor.moveToFirst()) {
                        @SuppressLint("Range") String profilePicturePath = postCursor.getString(postCursor.getColumnIndex("profile_picture"));
                        profilePicture = getResources().getIdentifier(profilePicturePath, "drawable", getPackageName());
                        postCursor.close();
                    }

                    reservations.add(new Reservation(barName, date, pax, profilePicture));
                }
                reservationCursor.close();
            }
        }

        return reservations;
    }

    /**
     * Show a dialog with reservation details.
     *
     * @param reservation The reservation to display.
     */
    private void showReservationDetails(Reservation reservation) {
        new AlertDialog.Builder(this)
                .setTitle("Reservation Details")
                .setMessage("Bar: " + reservation.getBarName() +
                        "\nDate: " + reservation.getDate() +
                        "\nPax: " + reservation.getPax())
                .setPositiveButton("OK", null)
                .show();
    }
}
