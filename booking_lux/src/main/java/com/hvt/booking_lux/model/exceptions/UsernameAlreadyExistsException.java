package com.hvt.booking_lux.model.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Username " + username + " already exists!");
    }
}
