package com.boba.bobabuddy.core.entity;

import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Entity
public class Store extends RatableObject {
    private String name;
    private String location;
    private @OneToMany(cascade = CascadeType.ALL)
    List<Item> menu;


    protected Store() {
    }

    public Store(String name, String location) {
        super(new HashSet<>());
        this.name = name;
        this.location = location;
        this.menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
                "name = " + name + ", " +
                "location = " + location + ")";
    }
}
