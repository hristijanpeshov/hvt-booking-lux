package com.hvt.booking_lux.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String countryCode;

    private String flagImage;

    @OneToMany(mappedBy = "country")
    private List<City> cityList;


    public Country() {
    }

    public Country(String name, String countryCode, String flagImage) {
        this.name = name;
        this.countryCode = countryCode;
        this.flagImage = flagImage;
        cityList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getFlagImage() {
        return flagImage;
    }

    public List<City> getCityList() {
        return cityList;
    }
}
