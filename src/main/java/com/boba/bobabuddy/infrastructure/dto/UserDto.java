package com.boba.bobabuddy.infrastructure.dto;

import com.boba.bobabuddy.infrastructure.validator.ValidEmail;
import com.boba.bobabuddy.infrastructure.validator.ValidPassword;
import com.fasterxml.jackson.annotation.*;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Objects matching the corresponding entities in the domain layer
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email", scope = UserDto.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDto {
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String name;
    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<SimpleRatingDto> ratings;
    private Collection<RoleDto> roles;

    public UserDto(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ratings = new HashSet<>();
    }

    public UserDto() {
    }

    public Collection<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleDto> roles) {
        this.roles = roles;
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

    public Set<SimpleRatingDto> getRatings() {
        return ratings;
    }

    public void setRatings(Set<SimpleRatingDto> ratings) {
        this.ratings = ratings;
    }
}
