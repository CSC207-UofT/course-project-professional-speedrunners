package com.boba.bobabuddy.core.usecase.port.request;

/***
 * Class that stores information required to perform a query by field price.
 */
public class findByPriceRequest {
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
