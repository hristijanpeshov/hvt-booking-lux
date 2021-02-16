package com.hvt.booking_lux.exceptions;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(long id) {
        super("Country with " + id + " was not found!");
    }
}
