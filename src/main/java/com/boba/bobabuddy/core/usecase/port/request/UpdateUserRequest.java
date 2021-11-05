package com.boba.bobabuddy.core.usecase.port.request;

import com.boba.bobabuddy.core.entity.RatingPoint;
import com.boba.bobabuddy.core.entity.User;

import java.util.List;

public class UpdateUserRequest {
    String email;
    List<RatingPoint> ratingLst;
    private String name;
    private String password;

    public List<RatingPoint> getRatingLst() {
        return ratingLst;
    }

    public void setRatingLst(List<RatingPoint> ratingLst) {
        this.ratingLst = ratingLst;
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

    public User updateUser() {
        User updatedUser = new User(getName(), getEmail(), getPassword());
        updatedUser.setRatingLst(getRatingLst());
        return updatedUser;
    }
}
