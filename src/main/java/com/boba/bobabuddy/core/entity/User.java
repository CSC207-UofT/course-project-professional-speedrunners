package com.boba.bobabuddy.core.entity;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class User {
    private @Id
    @GeneratedValue
    UUID id;
    private String name;
    private String email;
    private String password;
    private @OneToMany
    List<RatingPoint> ratingLst;

    public User(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratingLst = new ArrayList<>();
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "email = " + email + ", " +
                "password = " + password + ")";
    }
}
