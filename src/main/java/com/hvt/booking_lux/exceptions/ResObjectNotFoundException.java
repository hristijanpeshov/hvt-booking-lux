package com.hvt.booking_lux.exceptions;

public class ResObjectNotFoundException extends RuntimeException {

    public ResObjectNotFoundException(long id) {
        super("Object with " + id + " was not found!");
    }

}
