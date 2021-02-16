package com.hvt.booking_lux.model;

import javax.persistence.*;

@Entity
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Country country;

    private String name;

    public City() {
    }

    public City(String name, Country country){
        this.name = name;
        this.country = country;
    }


    public Long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }
}

