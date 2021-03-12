package com.hvt.booking_lux.model.exceptions;

public class ResObjectNotFoundException extends RuntimeException {

    public ResObjectNotFoundException(long id) {
        super("Object with " + id + " was not found!");
    }

}
