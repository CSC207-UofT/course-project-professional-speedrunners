package com.boba.bobabuddy.core.usecase.request;

import com.boba.bobabuddy.core.entity.User;

public class CreateUserRequest {
    private String email;
    private String name;
    private String password;

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

    public User createUser() {
        return new User(getName(), getEmail(), getPassword());
    }
}