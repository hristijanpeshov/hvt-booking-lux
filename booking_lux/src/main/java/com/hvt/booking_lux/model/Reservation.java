package com.hvt.booking_lux.model;

import com.hvt.booking_lux.model.enumeration.Status;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Unit unit;

    double pricePerNight;

    int numberNights;

    private ZonedDateTime reservationDate;

    private ZonedDateTime fromDate;

    private ZonedDateTime toDate;

    @OneToOne(mappedBy = "reservation")
    private Review review;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    public Review getReview() {
        return review;
    }

    public Reservation() {
    }

    public Reservation(User user, Unit unit, double pricePerNight, int numberNights, ZonedDateTime fromDate, ZonedDateTime toDate) {
        this.user = user;
        this.unit = unit;
        this.pricePerNight = pricePerNight;
        this.numberNights = numberNights;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reservationDate = ZonedDateTime.now();

    }

    public int numberOfNights(){
        return Period.between(LocalDate.of(fromDate.getYear(), fromDate.getMonth(), fromDate.getDayOfMonth()), LocalDate.of(toDate.getYear(), toDate.getMonth(), toDate.getDayOfMonth())).getDays();
    }

    public double getTotalAmount(){
        return numberOfNights() * pricePerNight;
    }


    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public ZonedDateTime getReservationDate() {
        return reservationDate;
    }

    public ZonedDateTime getFromDate() {
        return fromDate;
    }

    public ZonedDateTime getToDate() {
        return toDate;
    }
}
