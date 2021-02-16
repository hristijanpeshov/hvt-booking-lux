package com.hvt.booking_lux.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidCreatorException extends RuntimeException {
    public InvalidCreatorException() {
    }
}
