package com.boba.bobabuddy.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents a Store in the domain layer
 */

@Entity
@DiscriminatorValue("Store")
public class Store extends RatableObject {

    private String location;
    /***
     * JPA annotation to indicate one-to-many relationship between Store and Item.
     * cascade parameter tells JPA that if a Store's menu field is mutated, those changes to the Item
     * entities should also be persisted
     */
    private @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "store_id")
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
        return menu.add(item);
    }

    public boolean removeItem(Item item) {
        return menu.remove(item);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Store store = (Store) o;
        return getId() != null && Objects.equals(getId(), store.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ", " +
                "location = " + getLocation() + ", " +
                "links = " + getLinks() + ")";
    }
}
