package com.hvt.booking_lux.web;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.Review;
import com.hvt.booking_lux.service.ReservationService;
import com.hvt.booking_lux.service.ReviewService;
import com.hvt.booking_lux.web.requestHelper.RequestHelper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReservationService reservationService;

    @Autowired
    private RestTemplate restTemplate;

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
        HttpEntity<MultiValueMap<String, Object>> request = RequestHelper.createRequestMap("comment", comment);
        boolean s = Boolean.valueOf(restTemplate.postForEntity(RequestHelper.relativeUrl+"/review", request, String.class).getBody());
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

//    @GetMapping(value = "/exportCSV", produces = "text/csv")
//    public ResponseEntity<Resource> exportCSV() {
//        // replace this with your header (if required)
//        String[] csvHeader = {
//                "Country", "Value"
//        };
//
//        // replace this with your data retrieving logic
//        List<List<String>> csvBody = new ArrayList<>();
//        csvBody.add(Arrays.asList("January", "12394"));
//        csvBody.add(Arrays.asList("February", "6148"));
//        csvBody.add(Arrays.asList("March", "1653"));
//        csvBody.add(Arrays.asList("April", "2162"));
//        csvBody.add(Arrays.asList("May", "1214"));
//        csvBody.add(Arrays.asList("June", "1131"));
//        csvBody.add(Arrays.asList("July", "814"));
//        csvBody.add(Arrays.asList("August", "1167"));
//        csvBody.add(Arrays.asList("September", "660"));
//        csvBody.add(Arrays.asList("October", "1263"));
//        csvBody.add(Arrays.asList("November", "0"));
//        csvBody.add(Arrays.asList("December", "0"));
//
//        ByteArrayInputStream byteArrayOutputStream;
//
//        // closing resources by using a try with resources
//        // https://www.baeldung.com/java-try-with-resources
//        try (
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                // defining the CSV printer
//                CSVPrinter csvPrinter = new CSVPrinter(
//                        new PrintWriter(out),
//                        // withHeader is optional
//                        CSVFormat.DEFAULT.withHeader(csvHeader)
//                );
//        ) {
//            // populating the CSV content
//            for (List<String> record : csvBody)
//                csvPrinter.printRecord(record);
//
//            // writing the underlying stream
//            csvPrinter.flush();
//
//            byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//        InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);
//
//        String csvFileName = "people.csv";
//
//        // setting HTTP headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
//        // defining the custom Content-Type
//        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
//
//        return new ResponseEntity<>(
//                fileInputStream,
//                headers,
//                HttpStatus.OK
//        );
//    }

    @GetMapping
    public String showPercentage(Model model){
        model.addAttribute("positive", 100);
        model.addAttribute("negative", 50);
        model.addAttribute("bodyContent","statistics");
        return "master-template";
    }
}
