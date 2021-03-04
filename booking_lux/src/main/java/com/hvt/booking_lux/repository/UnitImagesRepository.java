package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ObjectImage;
import com.hvt.booking_lux.model.UnitImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitImagesRepository extends JpaRepository<UnitImages, Long> {
}
