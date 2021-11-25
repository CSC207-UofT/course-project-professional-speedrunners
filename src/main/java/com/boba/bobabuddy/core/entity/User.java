package com.boba.bobabuddy.core.entity;

import com.boba.bobabuddy.core.entity.builder.UserBuilder;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

/***
 * Class that represents a User
 */
@Entity

public class User {
    private @Id
    @GeneratedValue
    UUID id;
    private String email;
    private String name;
    private String password;
    private @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    Set<Rating> ratings;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    /***
     * Constructs a user.
     * @param name name of the user. can be duplicate
     * @param email email of user, should not have duplicate in the database
     * @param password password of the user. TODO: add encryption (low priority)
     */
    public User(final String name, final String email, final String password, Collection<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratings = new HashSet<>();
        this.roles=roles;
    }

    // For JPA
    public User() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

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

    public UserBuilder builder(){return new UserBuilder();}

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
                "email = " + email + ", " +
                "name = " + name + ", " +
                "password = " + password + ")";
    }
}
