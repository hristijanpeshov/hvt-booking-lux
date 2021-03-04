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

    public ResObject getResObject() {
        return resObject;
    }

    public void setResObject(ResObject resObject) {
        this.resObject = resObject;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
