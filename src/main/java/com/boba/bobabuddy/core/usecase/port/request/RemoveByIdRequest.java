package com.boba.bobabuddy.core.usecase.port.request;

import java.util.UUID;

/**
 * Class that stores in information needed to remove a RatingPoint object.
 */

public class RemoveByIdRequest {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
