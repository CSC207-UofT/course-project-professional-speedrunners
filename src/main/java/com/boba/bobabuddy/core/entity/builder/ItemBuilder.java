package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Store;


/***
 * Builder class for Item
 * note that these along with other builders in this package is currently unused.
 */
public class ItemBuilder {
    private float price;
    private Store store;
    private String name;

    public ItemBuilder setPrice(float price) {
        this.price = price;
        return this;
    }

    public ItemBuilder setStore(Store store) {
        this.store = store;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public Item createItem() {
        return new Item(price, store, name);
    }
}