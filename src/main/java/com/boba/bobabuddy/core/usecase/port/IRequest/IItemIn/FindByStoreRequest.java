package com.boba.bobabuddy.core.usecase.port.IRequest.IItemIn;

import java.util.UUID;

public class FindByStoreRequest {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
