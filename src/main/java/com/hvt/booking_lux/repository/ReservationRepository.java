package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.ObjectImage;
import com.hvt.booking_lux.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
