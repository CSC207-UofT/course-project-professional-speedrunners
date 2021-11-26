package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.infrastructure.JpaEntityResolver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/***
 * Class that represents a User
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email",
        resolver = JpaEntityResolver.class, scope = User.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private @Id
    String email;
    private String name;
    private String password;
    private String image;
    private @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_email")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Rating> ratings;

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
        this.image = "";
    }

    // For JPA
    public User() {

    }

    //Getters and setter for image
    public String getImage(){return image;}
    public void setImage(String imageUrl){this.image = imageUrl;}

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratingLst) {
        this.ratings = ratingLst;
    }

    public boolean addRating(Rating point) {
        return this.ratings.add(point);
    }

    public boolean removeRating(Rating point) {
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
