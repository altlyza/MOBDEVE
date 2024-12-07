package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservedBarAdapter extends RecyclerView.Adapter<ReservedBarAdapter.ReservedBarViewHolder> {

    private Context context;
    private List<Reservation> reservationList;
    private OnReservationClickListener listener;

    public ReservedBarAdapter(Context context, List<Reservation> reservationList, OnReservationClickListener listener) {
        this.context = context;
        this.reservationList = reservationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservedBarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserved_bar, parent, false);
        return new ReservedBarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservedBarViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.barName.setText(reservation.getBarName());
        holder.barImage.setImageResource(reservation.getBarImage()); // Replace with actual image loading logic if needed

        holder.itemView.setOnClickListener(v -> listener.onReservationClick(reservation));
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ReservedBarViewHolder extends RecyclerView.ViewHolder {
        TextView barName;
        ImageView barImage;

        public ReservedBarViewHolder(@NonNull View itemView) {
            super(itemView);
            barName = itemView.findViewById(R.id.barName);
            barImage = itemView.findViewById(R.id.barImage);
        }
    }

    public interface OnReservationClickListener {
        void onReservationClick(Reservation reservation);
    }
}
