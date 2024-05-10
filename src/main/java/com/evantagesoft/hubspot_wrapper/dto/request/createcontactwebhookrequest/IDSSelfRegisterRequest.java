package com.evantagesoft.hubspot_wrapper.dto.request.createcontactwebhookrequest;

import java.util.ArrayList;

public class IDSSelfRegisterRequest {
    private User user;
    private ArrayList<Object> properties;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Object> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<Object> properties) {
        this.properties = properties;
    }

}
