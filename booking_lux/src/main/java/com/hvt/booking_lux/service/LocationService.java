package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    List<City> listAllCities(long countryId);
    List<Country> listAllCountries();
    List<City> listAllCities();
    List<String> listAllCityNames();
}
