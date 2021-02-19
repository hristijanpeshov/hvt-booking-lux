package com.hvt.booking_lux.model.exceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super("Passwords not match!");
    }
}
