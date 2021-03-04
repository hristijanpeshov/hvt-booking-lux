package com.hvt.booking_lux.model.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(long id) {
        super("City with " + id + " was not found!");
    }
}
