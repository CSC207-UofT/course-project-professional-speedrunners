package com.boba.bobabuddy.core.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a Store in the domain layer
 * Note that entities are now coupled directly with Persistence implementation (JPA)
 * This is done to save time and reduce boilerplate codes.
 */

// Note that Ratable Interface have been modified to become
// RatableObject abstract class due to JPA limitation with persisting interface object.

// JPA annotation indicating that the class is an object to be persisted.
@Entity
public class Store extends RatableObject {

    private String location;
    /***
     * JPA annotation to indicate one-to-many relationship between Store and Item.
     * cascade parameter tells JPA that if a Store's menu field is mutated, those changes to the Item
     * entities should also be persisted
     */
    private @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
            @JoinColumn(name = "item_id")
    List<Item> menu;

    // For JPA
    protected Store() {
    }

    public Store(String name, String location) {
        super(name);
        this.location = location;
        this.menu = new ArrayList<>();
    }

    // Getters and Setters
    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> lst) {
        this.menu = lst;
    }

    public String getLocation() {
        return location;
    }

    public boolean setLocation(String newLocation) {
        this.location = newLocation;
        return true;
    }

    public boolean addItem(Item item) {
        this.menu.add(item);
        return true;
    }

    public boolean removeItem(Item item) {
        return this.menu.remove(item);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ", " +
                "location = " + getLocation() + ")";
    }
}
