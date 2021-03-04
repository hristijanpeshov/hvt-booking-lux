package com.hvt.booking_lux.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotCreatorOfReservationException extends RuntimeException {
    public UserNotCreatorOfReservationException(String username, Long reservationId) {
        super(String.format("User with username: %s is not the creator of reservation with id: %d",username,reservationId));
    }
}