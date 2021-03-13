package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    Review deleteReview(long reviewId);
    Review saveReview(String username, String comment, long reservationId, boolean sentiment);
    Review editReview(String comment, long reviewId);
    boolean canUserWriteReview(long reservationId, String username);
    boolean alreadyWrite(long reservationId, String username);
    Review findById(long id);
    List<Review> listAll();

}
