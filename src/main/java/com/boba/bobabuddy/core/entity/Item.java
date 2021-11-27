package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.infrastructure.JpaEntityResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

/**
 * Class that represents an Item in the domain layer
 */
@Entity
@DiscriminatorValue("Item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = JpaEntityResolver.class,
        scope = Item.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends RatableObject {

    private String image;
    private float price;
    @JsonIdentityReference
    private @ManyToOne
    Store store;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "item_categories",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",
            referencedColumnName = "id"))
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

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
        this.image = "";
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

    //Getters and Setters for image
    public String getImage(){
        return image;
    }

    public void setImage(String imageURL){
        this.image = imageURL;
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
                "name = " + getName() + ")";
    }
}
