package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.Store;

public class StoreBuilder {
    private String name;
    private String location;

    public StoreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StoreBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public Store createStore() {
        return new Store(name, location);
    }
}