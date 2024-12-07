package com.mobdeve.xx22.pascualreynadojaime.mco.myapp;

public class Post {
    private String barName;
    private String caption;
    private int profilePicture;
    private int postPhoto;
    private double latitude;
    private double longitude;

    public Post(String barName, String caption, int profilePicture, int postPhoto) {
        this.barName = barName;
        this.caption = caption;
        this.profilePicture = profilePicture;
        this.postPhoto = postPhoto;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBarName() {
        return barName;
    }

    public String getCaption() {
        return caption;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public int getPostPhoto() {
        return postPhoto;
    }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
