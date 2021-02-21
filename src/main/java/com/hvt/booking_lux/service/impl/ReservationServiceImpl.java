package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.exceptions.ReservationNotFoundException;
import com.hvt.booking_lux.repository.ResObjectRepository;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UnitService unitService;
    private final ResObjectRepository resObjectRepository;

    public ReservationServiceImpl(UserRepository userRepository, ReservationRepository reservationRepository, UnitService unitService, ResObjectRepository resObjectRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.unitService = unitService;
        this.resObjectRepository = resObjectRepository;
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
    public List<ResObject> findAllResObjectsThatAreReservedAtThatTime(ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople) {
//        (start1.isBefore(end2)) && (start2.isBefore(end1));
        List<Unit> units = listAll().stream().filter(s-> fromDate.isBefore(s.getToDate()) && (s.getFromDate().isBefore(toDate)))
                .map(Reservation::getUnit).distinct().collect(Collectors.toList());

        List<ResObject> resObjects = units.stream().map(Unit::getResObject).distinct().collect(Collectors.toList());
        resObjects = resObjects.stream().filter(s-> !(s.getUnits().size() > units.stream()
                .filter(f-> f.getResObject().getId().equals(s.getId()))
                .count())).collect(Collectors.toList());

        List<ResObject> returnObjects = resObjectRepository.findAll();
        returnObjects.removeAll(resObjects);
        return returnObjects.stream().filter(s-> s.getUnits().size() > 0 && s.getUnits().stream().anyMatch(f -> f.getNumberOfPeople() >= numberOfPeople))
                .collect(Collectors.toList());
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
