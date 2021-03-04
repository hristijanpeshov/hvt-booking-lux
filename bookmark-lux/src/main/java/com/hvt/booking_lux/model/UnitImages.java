package com.hvt.booking_lux.model;

import javax.persistence.*;

@Entity
public class UnitImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Unit unit;

    private String url;


    public UnitImages() {
    }

    public UnitImages(Unit unit, String url) {
        this.unit = unit;
        this.url = url;
    }



}
