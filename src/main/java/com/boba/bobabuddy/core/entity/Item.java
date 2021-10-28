package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.core.entity.builder.ItemBuilder;

import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Objects;

/**
 * Class that represents an Item in the domain layer
 */
@Entity
public class Item extends RatableObject {

    private float price;
    private @ManyToOne(cascade = CascadeType.ALL)
    Store store;
    private String name;

    /**
     * Creates an Item with relevant parameters
     * storeId, id should be final upon creation
     *
     * @param price the price of an PriceComparable object
     * @param store store in which the item is associated sold.
     * @param name  name of the item
     */
    public Item(float price, Store store, String name) {
        super(new HashSet<>());
        this.price = price;
        this.store = store;
        this.name = name;
    }

    //For JPA
    protected Item() {

    }

    /**
     * A builder class for item objects
     * This class is responsible for making construction of objects more readable in code, among other benefits.
     * It is not a necessary or important part of the class, but something nice to have.
     * Refer to builder design patterns for more detail
     *
     * @return An ItemBuilder object
     */

    public float getPrice() {
        return price;
    }

    /**
     * setter for Price. Mutating method
     *
     * @param price new price of the item
     */
    public void setPrice(float price) {
        this.price = price;
    }



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

    public static ItemBuilder builder(){return new ItemBuilder();}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Item item = (Item) o;
        return getId() != null && Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "price = " + getPrice() + ", " +
                "store = " + store + ", " +
                "name = " + getName() + ")";
    }
}
