package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameContains(String cityName);
    List<City> findAllByCountry(Country country);
}
