package com.boba.bobabuddy.core.entity;

import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class RatingPoint {
    private int rating;
    private @OneToOne
    User user;
    private @OneToOne
    RatableObject ratableObject;
    private @Id
    @GeneratedValue
    UUID id;

    public RatingPoint(int rating, User user, RatableObject ratableObject) {
        this.rating = rating;
        this.user = user;
        this.ratableObject = ratableObject;
    }

    protected RatingPoint() {

    }

    //TODO: add exception for input that are not 0 or 1
    public boolean updateRating(int newRating) {
        this.rating = newRating;
        return true;
    }

    public RatableObject getRatableObject() {
        return ratableObject;
    }

    public void setRatableObject(RatableObject ratableObject) {
        this.ratableObject = ratableObject;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RatingPoint that = (RatingPoint) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "rating = " + rating + ", " +
                "user = " + user + ", " +
                "ratableObject = " + ratableObject + ")";
    }
}
