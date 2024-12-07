package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.barName.setText(post.getBarName());
        holder.caption.setText(post.getCaption());
        holder.profilePicture.setImageResource(post.getProfilePicture());
        holder.postPhoto.setImageResource(post.getPostPhoto());

        // Set up Reserve Button Click Listener
        holder.reserveButton.setOnClickListener(v -> {
            // Show Reservation Dialog
            showReservationDialog(post.getBarName());
        });

        holder.viewMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("LATITUDE", post.getLatitude());
            intent.putExtra("LONGITUDE", post.getLongitude());
            intent.putExtra("BAR_NAME", post.getBarName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private void showReservationDialog(String barName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_reserve, null);
        builder.setView(dialogView);

        EditText dateInput = dialogView.findViewById(R.id.dateInput);
        EditText paxInput = dialogView.findViewById(R.id.paxInput);

        builder.setPositiveButton("Reserve", (dialog, which) -> {
            String date = dateInput.getText().toString();
            String paxStr = paxInput.getText().toString();

            if (date.isEmpty() || paxStr.isEmpty()) {
                Toast.makeText(context, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            int pax = Integer.parseInt(paxStr);
            if (pax > 20) {
                Toast.makeText(context, "Maximum 20 pax allowed.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save reservation to database
            DatabaseHandler dbHandler = new DatabaseHandler(context);
            int userId = 1; // Replace with actual user ID from logged-in session
            dbHandler.addReservation(userId, barName, date, pax);

            Toast.makeText(context, "Reservation Successful!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView barName, caption;
        ImageView profilePicture, postPhoto;
        Button reserveButton;
        Button viewMapButton; // Add this


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            barName = itemView.findViewById(R.id.barName);
            caption = itemView.findViewById(R.id.caption);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            postPhoto = itemView.findViewById(R.id.postPhoto);
            reserveButton = itemView.findViewById(R.id.reserveButton); // Initialize reserve button
            viewMapButton = itemView.findViewById(R.id.viewMapButton); // Initialize
        }
    }
}

