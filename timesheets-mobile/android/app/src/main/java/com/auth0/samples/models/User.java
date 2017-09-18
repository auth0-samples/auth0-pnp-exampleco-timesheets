package com.auth0.samples.models;

/**
 * Created by ej on 7/11/17.
 */

public class User {
    private String email;
    private String name;
    private String pictureURL;
    private String grantedScope;

    public User(String email, String name, String pictureURL, String grantedScope) {
        this.email = email;
        this.name = name;
        this.pictureURL = pictureURL;
        this.grantedScope = grantedScope;
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

    public String getGrantedScope() { return grantedScope; }

    public Boolean hasScope(String scope) {
        return grantedScope.contains(scope);
    }
}
