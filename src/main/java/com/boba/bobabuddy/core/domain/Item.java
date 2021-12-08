package com.boba.bobabuddy.core.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
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

    @ManyToMany
    @JoinTable(
            name = "item_categories",
            joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public boolean addCategory(Category category){
        category.addItem(this);
        return categories.add(category);
    }

    public boolean removeCategory(Category category){
        category.removeItem(this);
        return categories.remove(category);
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
