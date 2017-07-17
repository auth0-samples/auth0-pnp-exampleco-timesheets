package com.auth0.samples.models;

/**
 * Created by ej on 7/11/17.
 */

public class User {
    private String id;
    private String email;
    private String name;
    private String pictureURL;

    public User(String gId, String gEmail, String gName, String gPictureURL) {
        this.id = gId;
        this.email = gEmail;
        this.name = gName;
        this.pictureURL = gPictureURL;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPictureURL() {
        return pictureURL;
    }
}
