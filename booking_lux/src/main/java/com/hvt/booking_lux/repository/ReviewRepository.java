package com.hvt.booking_lux.repository;

import com.hvt.booking_lux.model.Review;
import com.hvt.booking_lux.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByReservationUnitId(long unitId);
    Optional<Review> findByUser_UsernameAndReservationId(String username, long reservationId);

}
