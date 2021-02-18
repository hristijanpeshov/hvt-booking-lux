package com.hvt.booking_lux.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidCreatorException extends RuntimeException {
    public InvalidCreatorException() {
    }
}
