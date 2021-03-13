package com.hvt.booking_lux.service.impl;

import com.google.gson.Gson;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.enumeration.Status;
import com.hvt.booking_lux.model.exceptions.ReservationNotFoundException;
import com.hvt.booking_lux.model.statistics.ResObjectMonthlyVisitorCount;
import com.hvt.booking_lux.model.exceptions.UnitIsReservedException;
import com.hvt.booking_lux.model.statistics.ResObjectYearStatistics;
import com.hvt.booking_lux.repository.ResObjectRepository;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.UnitService;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;
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

//    @Override
    private boolean checkIfUnitIsReserved(ZonedDateTime fromDate, ZonedDateTime toDate, long unitId) {
        return listAll().stream().filter(s-> fromDate.isBefore(s.getToDate()) && (s.getFromDate().isBefore(toDate)))
                .map(s-> s.getUnit().getId()).distinct().collect(Collectors.toList()).contains(unitId);
    }

    private List<Map<String,String>> createHashMapWithMonths()
    {
        List<Map<String,String>> jsonData = new ArrayList<>();
        for (int i=0;i<12;i++)
        {
            jsonData.add(i,new HashMap<String,String>());
        }
        jsonData.get(0).put("Month","January");
        jsonData.get(1).put("Month","February");
        jsonData.get(2).put("Month","March");
        jsonData.get(3).put("Month","April");
        jsonData.get(4).put("Month","May");
        jsonData.get(5).put("Month","June");
        jsonData.get(6).put("Month","July");
        jsonData.get(7).put("Month","August");
        jsonData.get(8).put("Month","September");
        jsonData.get(9).put("Month","October");
        jsonData.get(10).put("Month","November");
        jsonData.get(11).put("Month","December");
        return jsonData;
    }

    @Override
    public List<Map<String,String>> lastYearIncomeForCreatorsAccommodations(User user,Integer year)
    {
        List<ResObject> resObjectList = resObjectRepository.findAllByCreator(user);
        List<Map<String,String>> jsonData = createHashMapWithMonths();
        for (ResObject accommodation:
             resObjectList) {
            List<ResObjectYearStatistics> data = reservationRepository.findAnnualReservationCountForProperty(user.getUsername(), year,accommodation.getId());
            for (int i=0;i<12;i++)
            {
                Map<String,String> hashMapForMonth = jsonData.get(i);
                hashMapForMonth.put(accommodation.getName() + " " + accommodation.getId().toString(),"0");
            }
            for (ResObjectYearStatistics stats:
                 data) {
                Map<String,String> hashMapForMoth = jsonData.get(stats.getMonth()-1);
                hashMapForMoth.put(accommodation.getName() + " " + accommodation.getId().toString(),String.valueOf(stats.getTotal()));
            }
        }

        return jsonData;
    }

    @Override
    public List<Map<String,String>> yearlyVisitorsStatistic(User user,Integer year)
    {
        List<ResObject> resObjectList = resObjectRepository.findAllByCreator(user);
        List<Map<String,String>> jsonData = createHashMapWithMonths();
        for (ResObject accommodation:
                resObjectList) {
            List<ResObjectMonthlyVisitorCount> data = reservationRepository.findMonthlyVisitors(user.getUsername(), year,accommodation.getId());
            for (int i=0;i<12;i++)
            {
                Map<String,String> hashMapForMonth = jsonData.get(i);
                hashMapForMonth.put(accommodation.getName() + " " + accommodation.getId().toString(),"0");
            }
            for (ResObjectMonthlyVisitorCount stats:
                    data) {
                Map<String,String> hashMapForMoth = jsonData.get(stats.getMonth()-1);
                hashMapForMoth.put(accommodation.getName() + " " + accommodation.getId().toString(),String.valueOf(stats.getNum()));
            }
        }

        return jsonData;
    }

    @Override
    public List<ResObject> findAllResObjectsThatAreNotReservedAtThatTime(ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople) {
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
        if(checkIfUnitIsReserved(fromDate, toDate, unitId)){
            throw new UnitIsReservedException(unitId);
        }
        Reservation reservation = new Reservation(user, unit, unit.getPrice(), nights, fromDate, toDate);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation cancel(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new ReservationNotFoundException(reservationId));
        reservation.setStatus(Status.DELETED);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findAllReservationsForUser(User user){
        return reservationRepository.findAllByUser(user);
    }
}
