package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findAllByResObject(ResObject resObject);

}
