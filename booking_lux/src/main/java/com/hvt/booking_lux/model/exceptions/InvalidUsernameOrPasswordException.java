package com.hvt.booking_lux.model.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException {

    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password!");
    }
}
