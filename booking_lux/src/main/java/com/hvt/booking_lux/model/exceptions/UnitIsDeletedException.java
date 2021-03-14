package com.hvt.booking_lux.model.exceptions;

public class UnitIsDeletedException extends RuntimeException {

    public UnitIsDeletedException(long id) {
        super("Unit with id " + id + " is deleted!");
    }
}
