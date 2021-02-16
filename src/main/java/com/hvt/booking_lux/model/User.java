package com.hvt.booking_lux.model;

import com.hvt.booking_lux.enumeration.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
