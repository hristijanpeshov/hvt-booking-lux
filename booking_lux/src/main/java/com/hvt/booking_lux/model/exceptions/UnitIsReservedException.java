package com.hvt.booking_lux.model.exceptions;

public class UnitIsReservedException extends RuntimeException {

    public UnitIsReservedException(long id) {
        super("Unit with id: " + id + " is reserved!");
    }
}
