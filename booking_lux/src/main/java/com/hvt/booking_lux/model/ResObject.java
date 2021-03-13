package com.hvt.booking_lux.model;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.enumeration.Status;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Where(clause = "status='ACTIVE'")
public class ResObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "resObject")
    private List<Unit> units;

    @ElementCollection
    private List<String> objectImages;

    @ManyToOne
    private User creator;

    @Transient
    private double lowestPrice;

    @ManyToOne
    private City city;

    private ZonedDateTime dateCreated;

    public ResObject() {
    }

    public ResObject(String name, String address, String description, Category category, User creator, City city) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.category = category;
        this.creator = creator;
        this.city = city;
        objectImages = new ArrayList<>();
        units = new ArrayList<>();
        this.status = Status.ACTIVE;
        dateCreated = ZonedDateTime.now();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatusDeleted(){
        return status;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    synchronized public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public boolean getStatus(){
        return units.stream().anyMatch(x->x.getStatus().equals(Status.ACTIVE));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }


    public List<Unit> getUnits() {
        return units;
    }

    public List<String> getObjectImages() {
        return objectImages;
    }

    public User getCreator() {
        return creator;
    }

    public City getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void setObjectImages(List<String> objectImages) {
        this.objectImages = objectImages;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }
}
