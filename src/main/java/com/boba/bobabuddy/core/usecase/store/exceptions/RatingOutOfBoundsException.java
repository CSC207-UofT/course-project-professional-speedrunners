package com.boba.bobabuddy.core.usecase.store.exceptions;

public class RatingOutOfBoundsException extends Exception{
    public RatingOutOfBoundsException(String message){
        super(message);
    }
}
