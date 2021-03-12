package com.hvt.booking_lux.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResObjectNotFoundException extends RuntimeException {

    public ResObjectNotFoundException(long id) {
        super("Object with " + id + " was not found!");
    }

}
