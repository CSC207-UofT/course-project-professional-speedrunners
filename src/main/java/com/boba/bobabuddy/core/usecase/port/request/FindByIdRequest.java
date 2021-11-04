package com.boba.bobabuddy.core.usecase.port.request;

import java.util.UUID;

/***
 * Class that stores information required to perform a query by ID.
 * any type of query that search by a field called id can use this request.
 * A JSON containing UUID as String will be converted to this class, which converts
 * that string into a UUID object.
 * This behaviour is subjected to change depending on how hard UUID is to work with
 */
public class FindByIdRequest {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public void setId(UUID id){this.id = id;}
}
