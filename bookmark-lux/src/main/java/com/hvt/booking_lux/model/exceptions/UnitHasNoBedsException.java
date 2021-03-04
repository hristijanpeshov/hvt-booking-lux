package com.hvt.booking_lux.model.exceptions;


public class UnitHasNoBedsException extends RuntimeException {
    public UnitHasNoBedsException() {
        super("Unit has no beds selected!");
    }
}
