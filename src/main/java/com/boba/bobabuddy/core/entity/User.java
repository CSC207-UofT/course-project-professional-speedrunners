package com.boba.bobabuddy.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

/***
 * Class that represents a User
 * JPA annotation comments will be omitted. refer to other entity class for info
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private @Id
    String email;
    private String name;
    private String password;
    private @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_email")
    @JsonIdentityReference(alwaysAsId = true)
    Set<RatingPoint> ratings;

    /***
     * Constructs a user.
     * @param name name of the user. can be duplicate
     * @param email email of user, should not have duplicate in the database
     * @param password password of the user. TODO: add encryption (low priority)
     */
    public User(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratings = new HashSet<>();
    }

    // For JPA
    public User() {

    }

    public Set<RatingPoint> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingPoint> ratingLst) {
        this.ratings = ratingLst;
    }

    public boolean addRating(RatingPoint point) {
        return this.ratings.add(point);
    }

    public boolean removeRating(RatingPoint point) {
        return this.ratings.remove(point);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return email != null && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "email = " + email + ", " +
                "name = " + name + ", " +
                "password = " + password + ")";
    }
}
