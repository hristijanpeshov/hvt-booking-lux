package com.hvt.booking_lux.model;

import com.hvt.booking_lux.model.enumeration.BedType;

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

    private double price;

    private int numberOf;

    @OneToMany
    private List<BedTypes> bedTypes;

    private String description;

    @ManyToOne
    private ResObject resObject;

    @OneToMany(mappedBy = "unit")
    private List<UnitImages> unitImages;

    public Unit() {
    }

    public Unit(ResObject resObject, double size, int numberOf, double price, String description) {
        this.resObject = resObject;
        this.size = size;
        this.price = price;
        this.numberOf = numberOf;
        this.description = description;
        this.status = true;
        this.bedTypes = new ArrayList<>();
        unitImages = new ArrayList<>();
    }

    public Unit(ResObject resObject, double size, int numberOf, double price, String description, List<BedTypes> bedTypes) {
        this.resObject = resObject;
        this.size = size;
        this.price = price;
        this.numberOf = numberOf;
        this.description = description;
        this.status = true;
        this.bedTypes = bedTypes;
        unitImages = new ArrayList<>();
    }

    public List<BedTypes> getBedTypes() {
        return bedTypes;
    }

    public void setBedTypes(List<BedTypes> bedTypes) {
        this.bedTypes = bedTypes;
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

    public int getNumberOf() {
        return numberOf;
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

    public void setSize(double size) {
        this.size = size;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setNumberOf(int numberPeople) {
        this.numberOf = numberPeople;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setResObject(ResObject resObject) {
        this.resObject = resObject;
    }

    public void setUnitImages(List<UnitImages> unitImages) {
        this.unitImages = unitImages;
    }
}
