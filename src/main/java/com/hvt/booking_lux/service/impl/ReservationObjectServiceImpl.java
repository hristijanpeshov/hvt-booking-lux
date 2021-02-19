package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.exceptions.CityNotFoundException;
import com.hvt.booking_lux.model.exceptions.CountryNotFoundException;
import com.hvt.booking_lux.model.exceptions.ResObjectNotFoundException;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.Country;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.repository.*;
import com.hvt.booking_lux.service.ReservationObjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationObjectServiceImpl implements ReservationObjectService {

    private final ResObjectRepository resObjectRepository;
    private final UnitRepository unitRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public ReservationObjectServiceImpl(ResObjectRepository resObjectRepository, UnitRepository unitRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.resObjectRepository = resObjectRepository;
        this.unitRepository = unitRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<ResObject> listAll() {
        return resObjectRepository.findAll();
    }

    @Override
    public List<ResObject> listByCountry(long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(() -> new CountryNotFoundException(countryId));
        List<ResObject> resObjects = new ArrayList<>();
        country.getCityList().forEach(c-> resObjects.addAll(listByCity(c.getId())));
        return resObjects;
    }

    @Override
    public List<ResObject> listByCity(long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
        return resObjectRepository.findAllByCity(city);
    }

    @Override
    public ResObject save(String name, String address, String description, Category category, User creator, City city) {
        ResObject resObject = new ResObject(name, address, description, category, creator, city);
        return resObjectRepository.save(resObject);
    }

    @Override
    public ResObject edit(long resObjectId, String name, String address, String description, Category category, User creator, City city) {
        ResObject resObject = findResObjectById(resObjectId);
        resObject.setName(name);
        resObject.setAddress(address);
        resObject.setDescription(description);
        resObject.setCategory(category);
        resObject.setCreator(creator);
        resObject.setCity(city);
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
