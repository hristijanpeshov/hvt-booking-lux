package com.hvt.booking_lux.web.controllers;

import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.service.*;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
@RequestMapping(value = {"/","/home"})
public class HomeController {

//    private final LocationService locationService;
    private final ReservationObjectService reservationObjectService;
    private final ReservationService reservationService;
    private final UnitService unitService;
    private final LocationService locationService;
    private final UserStatisticsService userStatisticsService;
    private final ReviewService reviewService;

    private final ServletContext servletContext;
    private final TemplateEngine templateEngine;

    public HomeController(ReservationObjectService reservationObjectService, ReservationService reservationService, UnitService unitService, UserStatisticsService userStatisticsService, ReviewService reviewService, LocationService locationService, ServletContext servletContext, TemplateEngine templateEngine) {
        this.reservationObjectService = reservationObjectService;
        this.reservationService = reservationService;
        this.unitService = unitService;
        this.userStatisticsService = userStatisticsService;
        this.locationService = locationService;
        this.reviewService = reviewService;
        this.servletContext = servletContext;
        this.templateEngine = templateEngine;
    }

    @GetMapping
    public String homePage(Model model, HttpServletRequest request, HttpSession session, Authentication authentication){
//        List<Country> countryList = locationService.listAllCountries();
//        model.addAttribute("countries",countryList);
        model.addAttribute("cities",locationService.listAllCities());
        model.addAttribute("searchForm","searchForm");
        model.addAttribute("bodyContent", "index");
        model.addAttribute("leastExpensive", unitService.findTheLeastExpensive());
        model.addAttribute("mostExpensive", unitService.findTheMostExpensive());
        model.addAttribute("largest", unitService.findTheLargest());
        model.addAttribute("smallest", unitService.findTheSmallest());
        if(request.getParameter("error") != null){
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Check in date should be before check out date!");
        }
//        reviewService.canUserWriteReview(1, "user@user.com");
        model.addAttribute("bodyContent", "index");
        return "master-template";
    }

    @GetMapping(value = "/{id}/pdf")
    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response, @PathVariable long id) throws IOException {

        Reservation reservation = reservationService.findReservationById(id);

        WebContext context = new WebContext(request, response, servletContext);
        context.setVariable("reservation", reservation);
        String orderHtml = templateEngine.process("pdf", context);

//        ByteArrayOutputStream target = new ByteArrayOutputStream();
//        InputStream inputStream = new FileInputStream("src/main/resources/templates/pdf.html");
//        HtmlConverter.convertToPdf(inputStream, target);

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080/");
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);

        byte[] bytes = target.toByteArray();
        /* Send the response as downloadable PDF */

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);
    }



}
