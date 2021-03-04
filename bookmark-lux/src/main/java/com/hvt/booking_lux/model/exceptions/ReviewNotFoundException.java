package com.hvt.booking_lux.model.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(long id) {
        super("Review with id: " + id + " was not found!");
    }
}
