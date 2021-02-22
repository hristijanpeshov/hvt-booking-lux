package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.*;
import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.exceptions.CityNotFoundException;
import com.hvt.booking_lux.model.exceptions.CountryNotFoundException;
import com.hvt.booking_lux.model.exceptions.ResObjectNotFoundException;
import com.hvt.booking_lux.model.exceptions.UnitNumberIsZeroException;
import com.hvt.booking_lux.repository.*;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationObjectServiceImpl implements ReservationObjectService {

    private final ResObjectRepository resObjectRepository;
    private final UnitRepository unitRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ReservationService reservationService;

    public ReservationObjectServiceImpl(ResObjectRepository resObjectRepository, UnitRepository unitRepository, CityRepository cityRepository, CountryRepository countryRepository, ReservationService reservationService) {
        this.resObjectRepository = resObjectRepository;
        this.unitRepository = unitRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.reservationService = reservationService;
    }

    @Override
    public List<ResObject> listAll() {
        List<ResObject> resObjects = resObjectRepository.findAll().stream().filter(s-> s.getUnits().size() > 0).collect(Collectors.toList());
        resObjects.forEach(s-> s.setLowestPrice(
                s.getUnits().stream().mapToDouble(Unit::getPrice).min().orElseThrow(UnitNumberIsZeroException::new)));
        return resObjects;
    }

    @Override
    public List<ResObject> listByCountry(long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(() -> new CountryNotFoundException(countryId));
        List<ResObject> resObjects = new ArrayList<>();
        country.getCityList().forEach(c-> resObjects.addAll(listByCity(c.getId())));
        return resObjects;
    }

    @Override
    public List<Unit> listAllNotAvailable(long resObjectId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople) {
        List<Unit> units = reservationService.listAll().stream().filter(s-> s.getUnit().getResObject().getId().equals(resObjectId))
                .filter(s-> (fromDate.isBefore(s.getToDate()) && (s.getFromDate().isBefore(toDate)))).map(Reservation::getUnit).distinct()
                .collect(Collectors.toList());
//        List<ResObject> resObjects = listAll().stream().filter(s-> fromDate.isBefore(s.getToDate()) && (s.getFromDate().isBefore(toDate)))
//                .map(s-> s.getUnit().getResObject()).distinct().collect(Collectors.toList());
        return units;
    }

    @Override
    public List<Unit> listAllAvailableUnitsForResObject(long resObjectId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        resObject.getUnits().removeAll(listAllNotAvailable(resObjectId, fromDate, toDate, numberOfPeople));
        return resObject.getUnits().stream().filter(s-> s.getNumberOfPeople() >= numberOfPeople).collect(Collectors.toList());
    }

    @Override
    public double lowestPriceForUnit(long resObjectId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        resObject.getUnits().removeAll(listAllNotAvailable(resObjectId, fromDate, toDate, numberOfPeople));
        return resObject.getUnits().stream().mapToDouble(Unit::getPrice).min().getAsDouble();
    }

    @Override
    public List<ResObject> findAllAvailable(ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople, String city) {
        List<ResObject> resObjects = reservationService.findAllResObjectsThatAreReservedAtThatTime(fromDate, toDate, numberOfPeople);
        resObjects.forEach(s-> s.setLowestPrice(lowestPriceForUnit(s.getId(), fromDate, toDate, numberOfPeople)));
        return resObjects.stream().filter(s-> s.getCity().getName().toLowerCase().contains(city.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<ResObject> listByCity(long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
        return resObjectRepository.findAllByCity(city);
    }

    @Override
    public ResObject save(String name, String address, String description, Category category, User creator, long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(()->new CityNotFoundException(cityId));
        ResObject resObject = new ResObject(name, address, description, category, creator, city);
        return resObjectRepository.save(resObject);
    }

    @Override
    public ResObject edit(long resObjectId, String name, String address, String description, Category category) {
        ResObject resObject = findResObjectById(resObjectId);
        resObject.setName(name);
        resObject.setAddress(address);
        resObject.setDescription(description);
        resObject.setCategory(category);
        return resObjectRepository.save(resObject);
    }

    @Override
    public ResObject findResObjectById(long resObjectId) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        return resObject;
    }

    @Override
    public ResObject delete(long resObjectId) {
        ResObject resObject = findResObjectById(resObjectId);
        resObjectRepository.delete(resObject);
        return resObject;
    }

    @Override
    public List<ResObject> listByCityName(String city) {
        Optional<City> city1 = cityRepository.findByNameContains(city);
        if(city1.isPresent())
        {
            return resObjectRepository.findAllByCity(city1.get());
        }
        else
        {
            return resObjectRepository.findAll();
        }

    }
}
