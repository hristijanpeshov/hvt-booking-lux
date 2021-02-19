package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.exceptions.ReservationNotFoundException;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UnitService unitService;
    private final ReservationObjectService reservationObjectService;

    public ReservationServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository, UnitService unitService, ReservationObjectService reservationObjectService) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.unitService = unitService;
        this.reservationObjectService = reservationObjectService;
    }

    @Override
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        return reservation;
    }

    @Override
    public List<ResObject> findAllResObjectsThatAreReservedAtThatTime(ZonedDateTime fromDate, ZonedDateTime toDate) {
//        (start1.isBefore(end2)) && (start2.isBefore(end1));
        List<ResObject> resObjects = listAll().stream().filter(s-> fromDate.isBefore(s.getToDate()) && (toDate.isBefore(s.getFromDate())))
                .map(s-> s.getUnit().getResObject()).distinct().collect(Collectors.toList());
        List<ResObject> returnObjects = reservationObjectService.listAll();
        returnObjects.removeAll(resObjects);
        return resObjects;
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

    @Override
    public List<Reservation> findAllReservationsForUser(User user){
        return findAllReservationsForUser(user);
    }
}
