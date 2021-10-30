package com.boba.bobabuddy.core.entity.builder;

import com.boba.bobabuddy.core.entity.User;

public class UserBuilder {
    private String name;
    private String email;
    private String password;

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
        return new User(name, email, password);
    }
}