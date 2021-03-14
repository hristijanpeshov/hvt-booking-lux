package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResObjectRepository extends JpaRepository<ResObject, Long> {

    @Query("select ro from ResObject ro where ro.city = ?1 and ro.status='ACTIVE'")
    List<ResObject> findAllByCity(City city);

    @Query("select ro from ResObject ro where ro.creator = ?1 and ro.status='ACTIVE'")
    List<ResObject> findAllByCreator(User user);

    @Query("select ro from ResObject ro where ro.status ='ACTIVE'")
    List<ResObject> findAll();

    @Override
    @Query("select ro from ResObject ro where ro.id = ?1 and ro.status = 'ACTIVE'")
    Optional<ResObject> findById(Long aLong);

    @Query("select ro from ResObject ro")
    Optional<ResObject> findAllById(long id);


}
