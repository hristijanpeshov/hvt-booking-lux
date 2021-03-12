package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Review;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.ReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReservationService reservationService;

    public ReviewController(ReviewService reviewService, ReservationService reservationService) {
        this.reviewService = reviewService;
        this.reservationService = reservationService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{reservationId}")
    public String reviewPage(Authentication authentication, @PathVariable Long reservationId, Model model){
        String username = authentication.getName();
        if(reviewService.canUserWriteReview(reservationId,username)){
            model.addAttribute("reservationId",reservationId);
            if(reviewService.alreadyWrite(reservationId,username))
            {
                Reservation reservation = reservationService.findReservationById(reservationId);
                Review review = reservation.getReview();
                model.addAttribute("edit" , true);
                model.addAttribute("review",review);

            }
            else
            {
                model.addAttribute("edit",false);
            }
            model.addAttribute("bodyContent","reviewPage");
            return "master-template";
        }
        return "redirect:/reserve/myReservations";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{reservationId}/add")
    public String addComment(Authentication authentication, @PathVariable Long reservationId, @RequestParam String comment)
    {
        reviewService.saveReview(authentication.getName(),comment,reservationId);
        return "redirect:/reserve/myReservations";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{reviewId}")
    public String editComment(Authentication authentication, @PathVariable Long reviewId, @RequestParam String comment)
    {
        reviewService.editReview(comment,reviewId);
        return "redirect:/reserve/myReservations";
    }
    @GetMapping
    public String showPercentage(Model model){
        model.addAttribute("positive", 100);
        model.addAttribute("negative", 50);
        model.addAttribute("bodyContent","statistics");
        return "master-template";
    }
}
