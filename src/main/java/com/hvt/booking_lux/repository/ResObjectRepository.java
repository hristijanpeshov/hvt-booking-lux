package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResObjectRepository extends JpaRepository<ResObject, Long> {

    List<ResObject> findAllByCity(City city);
}
