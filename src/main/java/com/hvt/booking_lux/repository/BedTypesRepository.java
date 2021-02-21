package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.BedTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypesRepository extends JpaRepository<BedTypes, Long> {
}
