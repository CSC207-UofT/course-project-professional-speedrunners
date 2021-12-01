package com.boba.bobabuddy.core.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a Store in the domain layer
 */

@Entity
@DiscriminatorValue("Store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Store extends RatableObject {

    private String location;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "store_id")
    @Builder.Default
    private List<Item> menu = new ArrayList<>();

    public boolean addItem(Item item) {
        return menu.add(item);
    }

    public boolean removeItem(Item item) {
        return menu.remove(item);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ", " +
                "location = " + getLocation();
    }
}
