package com.hvt.booking_lux.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reservation reservation;

    @Column(length = 2000)
    private String comment;

    @ManyToOne
    private User user;

    public Review() {
    }

    public Review(Reservation reservation, String comment, User user) {
        this.reservation = reservation;
        this.comment = comment;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

}
