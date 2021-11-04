package com.boba.bobabuddy.infrastructure.controller.port;

import com.boba.bobabuddy.core.entity.Store;

public class UpdateItemRequest {
    private float avgRating;
    private float price;
    private Store store;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
