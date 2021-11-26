package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.Store;

public class StoreBuilder {
    private String location;
    private String owner;
    private String name;

    public StoreBuilder setLocation(String location) {
        this.location = location;
        return this;
    }
    public StoreBuilder setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public StoreBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Store createStore() {
        return new Store(this.name, this.location, this.owner);
    }
}
