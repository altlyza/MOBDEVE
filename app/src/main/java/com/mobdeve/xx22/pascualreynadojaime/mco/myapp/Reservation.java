package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

public class Reservation {
    private String barName;
    private String date;
    private int pax;
    private int barImage;

    public Reservation(String barName, String date, int pax, int barImage) {
        this.barName = barName;
        this.date = date;
        this.pax = pax;
        this.barImage = barImage;
    }

    public String getBarName() {
        return barName;
    }

    public String getDate() {
        return date;
    }

    public int getPax() {
        return pax;
    }

    public int getBarImage() {
        return barImage;
    }
}
