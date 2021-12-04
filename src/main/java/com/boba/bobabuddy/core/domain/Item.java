package com.boba.bobabuddy.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

/**
 * Class that represents an Item in the domain layer
 */
@Entity
@DiscriminatorValue("Item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Item extends RatableObject {
    @Min(value = 0)
    private double price;

    @ManyToOne
    private Store store;

    @ElementCollection
    private Set<String> categories;

    public boolean addCategory(String category){
        return this.categories.add(category);
    }

    public boolean removeCategory(String category){
        return this.categories.remove(category);
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
