package com.boba.bobabuddy.core.usecase.port.request;

/***
 * Class that stores information required to perform a query by name.
 * any type of query that search by a field called name can use this request.
 */
public class FindByNameRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
