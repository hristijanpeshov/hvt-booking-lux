package com.hvt.booking_lux.model;

import javax.persistence.*;

@Entity
public class ObjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private ResObject resObject;

    private String url;

    public ObjectImage() {
    }

    public ObjectImage(ResObject resObject, String url) {
        this.resObject = resObject;
        this.url = url;
    }

}
