package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("select ro.units from ResObject ro where ro=?1 and ro.status = 'ACTIVE'")
    List<Unit> findAllByResObject(ResObject resObject);

    @Query("select u from Unit u where u.status = 'ACTIVE'")
    List<Unit> findAll();

    @Override
    @Query("select u from Unit u where u.id = ?1 and u.status = 'ACTIVE'")
    Optional<Unit> findById(Long aLong);

    @Query("select u from Unit u where u.id = ?1")
    Optional<Unit> findAllById(Long id);

}
