package com.boba.bobabuddy.core.usecase.port.request;

import com.boba.bobabuddy.core.entity.RatingPoint;

import java.util.List;

public class UpdateUserRequest {
    String email;
    private String name;
    private String password;
    List<RatingPoint> ratingList;
}
