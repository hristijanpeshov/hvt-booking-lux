package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;

import java.time.ZonedDateTime;

public interface ReservationService {
    Reservation reserve(User user, long unitId, int nights, ZonedDateTime fromDate,ZonedDateTime toDate);
    Reservation cancel(long reservationId);
}
