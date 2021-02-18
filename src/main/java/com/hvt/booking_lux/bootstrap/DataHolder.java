package com.hvt.booking_lux.bootstrap;

import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.enumeration.Role;
import com.hvt.booking_lux.model.*;
import com.hvt.booking_lux.repository.*;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;

@Component
@Getter
public class DataHolder  {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final UnitRepository unitRepository;
    private final ResObjectRepository resObjectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;


    public DataHolder(CityRepository cityRepository, CountryRepository countryRepository, UnitRepository unitRepository, ResObjectRepository resObjectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ReservationRepository reservationRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.unitRepository = unitRepository;
        this.resObjectRepository = resObjectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationRepository = reservationRepository;
    }

    @PostConstruct
    public void init(){
        Country countryMKD = countryRepository.save(new Country("Macedonia", "MKD", "flag"));
        Country countryEn = countryRepository.save(new Country("England", "EN", "flag"));
        Country countrySRB = countryRepository.save(new Country("Serbia", "SRB", "flag"));


        City skopje = cityRepository.save(new City("Skopje", countryMKD));
        City bitola = cityRepository.save(new City("Bitola", countryMKD));
        City veles = cityRepository.save(new City("Veles", countryMKD));
        City belgrade = cityRepository.save(new City("Belgrade", countrySRB));
        City london = cityRepository.save(new City("London", countryEn));
        City manchester = cityRepository.save(new City("Manchester", countryEn));


        User user = userRepository.save(new User("user@user.com", passwordEncoder.encode("user"), "User", "user", Role.USER));


        ResObject houseSRB = new ResObject("House in Belgrade", "adresa bb 11 22", "Big house in belgrade with fountain", Category.HOUSE, user, belgrade);
        ResObject apartmentEn = new ResObject("Rooftop apartment", "england 11 22", "Rooftop apartment with wonderful view", Category.APARTMENT, user, london);

        ResObject hotelMKD = resObjectRepository.save( new ResObject("Hotel Makedonija", "adresa 11 22", "Hotel in Macedonia", Category.HOTEL, user, skopje));
        resObjectRepository.save(houseSRB);
        resObjectRepository.save(apartmentEn);



        Unit unit3 = new Unit(hotelMKD, 70, 5, 100, "Room for 5 people!");
        Unit unit4 = new Unit(hotelMKD, 25, 2, 35, "Room for 2 people!");

        Unit unit1 = unitRepository.save(new Unit(hotelMKD, 22, 2, 20, "Room for 2 people!"));
        Unit unit2 = unitRepository.save(new Unit(hotelMKD, 40, 3, 55, "Room for 4 people!"));
        unitRepository.save(unit3);
        unitRepository.save(unit4);


        reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberPeople(), ZonedDateTime.now(), ZonedDateTime.now()));
        reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberPeople(), ZonedDateTime.now(), ZonedDateTime.now()));
        reservationRepository.save(new Reservation(user, unit2, unit2.getPrice(), unit2.getNumberPeople(), ZonedDateTime.now(), ZonedDateTime.now()));

    }

}
