package com.boba.bobabuddy.core.usecase.request;

import com.boba.bobabuddy.core.entity.Store;

/***
 * Class that stores information required to create a new Store entity
 * A API call that invoke the CreateStore usecase will have the HTTP request body in
 * JSON converted to this class via HTTPMessageConverter.
 * Subsequently, this class will handle the construction of the Store entity.
 */

public class CreateStoreRequest {
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

    /***
     * Construct a Store entity base on the fields provided.
     * @return a new Store object to be stored in the database
     */

    public Store toStore() {
        return new Store(getName(), getLocation());
    }
}
