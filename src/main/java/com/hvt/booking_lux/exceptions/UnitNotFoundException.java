package com.hvt.booking_lux.exceptions;

public class UnitNotFoundException extends RuntimeException{

    public UnitNotFoundException(long id) {
        super("Unit with " + id + " was not found!");
    }
}
