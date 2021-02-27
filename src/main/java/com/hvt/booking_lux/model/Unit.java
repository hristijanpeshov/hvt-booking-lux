package com.hvt.booking_lux.model;

import com.hvt.booking_lux.bootstrap.DataHolder;
import com.hvt.booking_lux.model.enumeration.BedType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double size;

    private boolean status;

    private double price;

    private int numberOf;

    @OneToMany
    private List<BedTypes> bedTypes;

    private String description;

    @ManyToOne
    private ResObject resObject;

    @ElementCollection
    private List<String> unitImages;

    public Unit() {
    }

    public Unit(ResObject resObject,String title, double size, int numberOf, double price, String description) {
        this.resObject = resObject;
        this.size = size;
        this.price = price;
        this.numberOf = numberOf;
        this.description = description;
        this.status = true;
        this.bedTypes = new ArrayList<>();
        unitImages = new ArrayList<>();
    }

    public Unit(ResObject resObject,String title, double size, int numberOf, double price, String description, List<BedTypes> bedTypes) {
        this.resObject = resObject;
        this.size = size;
        this.price = price;
        this.numberOf = numberOf;
        this.description = description;
        this.status = true;
        this.bedTypes = bedTypes;
        unitImages = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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

    public int getNumberOfPeople() {
        return bedTypes.stream().mapToInt(s-> DataHolder.peopleNumberMap.get(s.getBedType().toString()) * s.getCount()).sum();
    }

    public String getBedsAsString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<bedTypes.size(); i++){
            if(i == bedTypes.size()-1){
                sb.append(bedTypes.get(i).getBedType());
            }
            else{
                sb.append(bedTypes.get(i).getBedType()).append(", ");

            }
        }
        return sb.toString();
    }

    public List<String> getUnitImages() {
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

    public void setUnitImages(List<String> unitImages) {
        this.unitImages = unitImages;
    }
}
