package com.boba.bobabuddy.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/***
 * Class that represents a User
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email", scope = UserDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String email;
    private String name;
    private String password;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<UUID> ratings;

    /***
     * Constructs a user.
     * @param name name of the user. can be duplicate
     * @param email email of user, should not have duplicate in the database
     * @param password password of the user. TODO: add encryption (low priority)
     */
    public UserDto(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratings = new HashSet<>();
    }

    public UserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UUID> getRatings() {
        return ratings;
    }

    public void setRatings(Set<UUID> ratings) {
        this.ratings = ratings;
    }
}
