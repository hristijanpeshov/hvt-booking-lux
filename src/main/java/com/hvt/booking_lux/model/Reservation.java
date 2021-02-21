package com.hvt.booking_lux.model;

import javax.persistence.*;
import java.time.ZonedDateTime;

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

    public int getNumberNights() {
        return numberNights;
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
