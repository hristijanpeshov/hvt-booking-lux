package com.hvt.booking_lux.model.exceptions;

public class UnitNotFoundException extends RuntimeException{

    public UnitNotFoundException(long id) {
        super("Unit with " + id + " was not found!");
    }
}
