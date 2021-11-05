package com.boba.bobabuddy.core.usecase.port.request;

import com.boba.bobabuddy.core.entity.Item;

/***
 * Class that stores information required to create a new Item entity
 * A API call that invoke the CreateItem usecase will have the HTTP request body in
 * JSON converted to this class via HTTPMessageConverter.
 * Subsequently, this class will handle the construction of the Item entity.
 */
public class CreateItemRequest {
    private float price;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    /***
     * Construct an Item entity base on the fields provided.
     * @return a new Item object to be stored in the database
     */
    public Item toItem() {
        return new Item(getPrice(), null, getName());
    }
}
