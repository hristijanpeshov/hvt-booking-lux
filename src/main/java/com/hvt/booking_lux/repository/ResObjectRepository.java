package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ResObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResObjectRepository extends JpaRepository<ResObject, Long> {
}
