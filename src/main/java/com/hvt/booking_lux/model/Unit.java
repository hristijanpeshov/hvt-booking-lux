package com.hvt.booking_lux.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double size;

    private boolean status;

    private int numberPeople;

    private double price;

    private String description;

    @ManyToOne
    private ResObject resObject;

    @OneToMany(mappedBy = "unit")
    private List<UnitImages> unitImages;

    public Unit() {
    }

    public Unit(ResObject resObject, double size, int numberPeople, double price, String description) {
        this.resObject = resObject;
        this.size = size;
        this.numberPeople = numberPeople;
        this.price = price;
        this.description = description;
        this.status = true;
        unitImages = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public double getSize() {
        return size;
    }

    public boolean isStatus() {
        return status;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public ResObject getResObject() {
        return resObject;
    }

    public List<UnitImages> getUnitImages() {
        return unitImages;
    }
}
