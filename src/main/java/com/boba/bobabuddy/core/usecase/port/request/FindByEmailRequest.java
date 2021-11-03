package com.boba.bobabuddy.core.usecase.port.request;

/**
 * Class that stores the information required to make a query by email.
 */

public class FindByEmailRequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}