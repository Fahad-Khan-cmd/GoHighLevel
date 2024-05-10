package com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest;

import java.util.ArrayList;

public class User {
    private String username;
    private String realm;
    private String password;
    private ArrayList<Claims> claims;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Claims> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<Claims> claims) {
        this.claims = claims;
    }
}
