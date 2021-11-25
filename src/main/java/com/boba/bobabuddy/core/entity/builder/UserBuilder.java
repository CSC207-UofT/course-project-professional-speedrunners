package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.Role;
import com.boba.bobabuddy.core.entity.User;

import java.util.Collection;

public class UserBuilder {
    private String name;
    private String email;
    private String password;
    private Collection<Role> roles;

    public UserBuilder setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User createUser() {
        return new User(name, email, password, roles);
    }
}