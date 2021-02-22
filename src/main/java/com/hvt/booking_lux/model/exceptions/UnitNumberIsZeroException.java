package com.hvt.booking_lux.model.exceptions;

public class UnitNumberIsZeroException extends RuntimeException {

    public UnitNumberIsZeroException() {
        super("There are no units!");
    }
}
