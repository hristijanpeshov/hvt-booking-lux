package com.hvt.booking_lux.model.exceptions;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(long id){
        super("Reservation with id:" + id + " was not found");
    }

}
