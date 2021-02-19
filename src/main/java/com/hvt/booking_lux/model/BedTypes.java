package com.hvt.booking_lux.model;

import com.hvt.booking_lux.model.enumeration.BedType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class BedTypes {

    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    private BedType bedType;

    int count;

    public BedTypes() {
    }

    public BedTypes(BedType bedType, int count) {
        this.bedType = bedType;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public BedType getBedType() {
        return bedType;
    }

    public int getCount() {
        return count;
    }
}
