package com.hvt.booking_lux.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(long id){
        super("Reservation with id:" + id + " was not found");
    }

}
