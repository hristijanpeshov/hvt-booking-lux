package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Review;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.exceptions.ReviewNotFoundException;
import com.hvt.booking_lux.model.exceptions.UserNotCreatorOfReservationException;
import com.hvt.booking_lux.repository.ReviewRepository;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.ReviewService;
import com.hvt.booking_lux.service.UnitService;
import com.hvt.booking_lux.service.UserService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final UnitService unitService;
    private final ReservationService reservationService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserService userService, UnitService unitService, ReservationService reservationService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.unitService = unitService;
        this.reservationService = reservationService;
    }

    @Override
    public Review deleteReview(long reviewId) {
        Review review = findById(reviewId);
        reviewRepository.delete(review);
        return review;
    }

    @Override
    public Review saveReview(String username, String comment, long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        User user = (User) userService.loadUserByUsername(username);
        Review review = new Review(reservation, comment, user);
        return reviewRepository.save(review);
    }

    @Override
    public Review editReview(String comment, long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException(reviewId));
        review.setComment(comment);
        return reviewRepository.save(review);
    }

    @Override
    public boolean canUserWriteReview(long reservationId, String username) {
        User user = (User) userService.loadUserByUsername(username);
        Reservation reservation = reservationService.findReservationById(reservationId);
        if(!reservation.getUser().equals(user))
        {
            throw new UserNotCreatorOfReservationException(username,reservationId);
        }
        return reservation.getToDate().isBefore(ZonedDateTime.now()) && ZonedDateTime.now().isBefore(reservation.getToDate().plusDays(15));
    }

    @Override
    public boolean alreadyWrite(long reservationId, String username) {
        return reviewRepository.findByUser_UsernameAndReservationId(username, reservationId).isPresent();
    }


    @Override
    public Review findById(long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
        return review;
    }

    @Override
    public List<Review> listAll() {
        return reviewRepository.findAll();
    }
}
