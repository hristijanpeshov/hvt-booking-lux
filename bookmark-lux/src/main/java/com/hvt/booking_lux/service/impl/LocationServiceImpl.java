package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.Country;
import com.hvt.booking_lux.model.exceptions.CountryNotFoundException;
import com.hvt.booking_lux.repository.CityRepository;
import com.hvt.booking_lux.repository.CountryRepository;
import com.hvt.booking_lux.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public LocationServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<City> listAllCities(long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(()->new CountryNotFoundException(countryId));
        return cityRepository.findAllByCountry(country);
    }

    @Override
    public List<Country> listAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<City> listAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public List<String> listAllCityNames() {
        return cityRepository.findAll().stream().map(City::getName).collect(Collectors.toList());
    }
}
