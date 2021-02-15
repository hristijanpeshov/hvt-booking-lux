package com.hvt.booking_lux.model;

import com.hvt.booking_lux.enumeration.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creator")
    private List<ResObject> resObjects;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public User() {
    }

    public User(String username, Role role){
        this.username = username;
        this.role = role;
        resObjects = new ArrayList<>();
    }
}
