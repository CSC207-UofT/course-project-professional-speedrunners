package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.infrastructure.JpaEntityResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Class that represents a Category
 */
@Entity
// specify that the class is an entity and is mapped to a database table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id",
        resolver = JpaEntityResolver.class, scope = Categories.class)
// this annotation is used when an object has parent-child relationship
// needed to serialization and deserialization process (e.g. save it to DB)
@JsonIgnoreProperties(ignoreUnknown = true)
// is used at class level to mark a property or list of properties to be ignored.
public class Categories {
    private @Id
    @GeneratedValue
    UUID id;

    enum Category{
        JELLY_TOPPING, TAPIOCA_TOPPING, MILK_TEA, TEA,
        SLUSH, FRUIT_DRINK
    }
    private Category ctgry;

    @ManyToMany(mappedBy = "categories")
    Set<Item> items;
    //TODO: change item -> jointable for manytomany relationship

    //For JPA
    protected Categories(){
    }

    public Categories(Category ctgry){
        this.ctgry = ctgry;
        this.items = new HashSet<>();
    }

    public void setCtgry(Item item){
        items.add(item);
    }

    public Set<Item> getItems(){
        return items;
    }


}
