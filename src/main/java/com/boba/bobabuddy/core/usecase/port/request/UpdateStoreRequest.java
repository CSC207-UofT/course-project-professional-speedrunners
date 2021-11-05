package com.boba.bobabuddy.core.usecase.port.request;

public class UpdateStoreRequest {
    // private float avgRrating; //TODO: do we need to update this?
    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
