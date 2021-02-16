package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ObjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectImageRepository extends JpaRepository<ObjectImage, Long> {
}
