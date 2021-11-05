package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.core.entity.builder.ItemBuilder;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Class that represents an Item in the domain layer
 * Note that entities are now coupled directly with Persistence implementation (JPA)
 * This is done to save time and reduce boilerplate codes.
 * JPA is Object Relational Mapping API designed for Java
 * Refer to Spring Data JPA doc for more information
 */

// Note that RatableItem and PriceComparable class are removed. Ratable Interface have been modified to become
// RatableObject abstract class due to JPA limitation with persisting interface object.
// Now Item class exhibit PriceComparable behaviour and extends RatableObject Directly.

// JPA annotation indicating that the class is an object to be persisted.
@Entity
@DiscriminatorValue("Item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item extends RatableObject {

    private float price;
    // JPA annotation to indicate many-to-one relationship between item and store
    // cascade parameter tells JPA that if Item's store field is mutated, those changes to the store
    // entity should also be persisted
    private @ManyToOne(cascade = CascadeType.ALL)
    Store store;

    /**
     * Creates an Item with relevant parameters
     * storeId, id should be final upon creation
     *
     * @param price the price of an PriceComparable object
     * @param store store in which the item is associated sold.
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

    // Builder object for Item.
    public static ItemBuilder builder() {
        return new ItemBuilder();
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
                "price = " + getPrice() + ", " +
                "store = " + getStore() + ", " +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ")";
    }
}
