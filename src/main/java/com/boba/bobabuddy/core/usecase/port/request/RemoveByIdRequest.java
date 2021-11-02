package com.boba.bobabuddy.core.usecase.port.request;

import java.util.UUID;

public class RemoveByIdRequest {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}
