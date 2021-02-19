package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface ReservationService {
    Reservation reserve(User user, long unitId, int nights, ZonedDateTime fromDate,ZonedDateTime toDate);
    Reservation cancel(long reservationId);
}
