package com.hvt.booking_lux.model.exceptions;

public class ResObjectIsDeletedException extends RuntimeException {

    public ResObjectIsDeletedException(long id) {
        super("Res object with id " + id + " is deleted!");
    }
}
