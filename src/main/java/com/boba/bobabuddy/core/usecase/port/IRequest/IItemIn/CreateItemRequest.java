package com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;


public class CreateItemRequest {
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

    public Item toItem(){return new Item(getPrice(),getStore(),getName());}
}
