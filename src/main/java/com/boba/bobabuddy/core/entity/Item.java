package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.infrastructure.JpaEntityResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Class that represents an Item in the domain layer
 */
@Entity
@DiscriminatorValue("Item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Item.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends RatableObject {

    private float price;
    @JsonIdentityReference
    private @ManyToOne
    Store store;

    /**
     * Creates an Item with relevant parameters
     *
     * @param price the price of the item
     * @param store store that sell this item.
     * @param name  name of the item
     */
    public Item(float price, Store store, String name) {
        super(name);
        this.price = price;
        this.store = store;
    }

    // For JPA
    protected Item() {
    }

    // Getters and Setters for price
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    // Getters and Setters for Store
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "price = " + getPrice() + ", " +
                "store = " + getStore() + ", " +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ", " +
                "links = " + getLinks() + ")";
    }
}
