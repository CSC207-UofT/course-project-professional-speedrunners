package com.boba.bobabuddy.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/***
 * Class that represents a singleton rating
 * JPA annotation comments will be omitted. refer to other entity class for info
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingPoint {
    private int rating;
    // Indicates ManyToOne relationship between entities.
    private @ManyToOne
    User user;
    private @ManyToOne
    RatableObject ratableObject;
    private @Id
    @GeneratedValue
    UUID id;

    /***
     * Constructor for a Rating point object
     * @param rating the rating, either 0 or 1
     * @param user the user that made this rating
     * @param ratableObject the ratableObject that is being rated, either Item or Store
     */
    public RatingPoint(int rating, User user, RatableObject ratableObject) {
        this.rating = rating;
        this.user = user;
        this.ratableObject = ratableObject;
    }

    // For JPA
    protected RatingPoint() {

    }


    // Getters and Setters
    public RatableObject getRatableObject() {
        return ratableObject;
    }

    public void setRatableObject(RatableObject ratableObject) {
        this.ratableObject = ratableObject;
    }

    public int getRating() {
        return rating;
    }

    //TODO: might need a exception for input that are not 0 or 1, should exist in usecase layer
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
