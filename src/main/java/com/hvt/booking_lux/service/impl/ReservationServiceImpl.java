package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UnitService unitService;

    public ReservationServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository, UnitService unitService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.unitService = unitService;
    }

    @Override
    public Reservation reserve(User user, long unitId, int nights, ZonedDateTime fromDate, ZonedDateTime toDate) {
        Unit unit = unitService.findById(unitId);
        Reservation reservation = new Reservation(user, unit, unit.getPrice(), nights, fromDate, toDate);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation cancel(long reservationId) {
        return null;
    }
}
