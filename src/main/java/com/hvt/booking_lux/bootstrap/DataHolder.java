package com.hvt.booking_lux.bootstrap;


import com.hvt.booking_lux.model.*;
import com.hvt.booking_lux.model.enumeration.BedType;
import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.enumeration.Role;
import com.hvt.booking_lux.repository.*;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final BedTypesRepository bedTypesRepository;
    public static HashMap<String, Integer> peopleNumberMap = new HashMap<>();


    public DataHolder(CityRepository cityRepository, CountryRepository countryRepository, UnitRepository unitRepository, ResObjectRepository resObjectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ReservationRepository reservationRepository, BedTypesRepository bedTypesRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.unitRepository = unitRepository;
        this.resObjectRepository = resObjectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationRepository = reservationRepository;
        this.bedTypesRepository = bedTypesRepository;
    }

    @PostConstruct
    public void init(){
        DataHolder.peopleNumberMap.put(BedType.TWIN.toString(), 1);
        DataHolder.peopleNumberMap.put(BedType.DOUBLE.toString(), 2);
        DataHolder.peopleNumberMap.put(BedType.QUEEN.toString(), 2);
        DataHolder.peopleNumberMap.put(BedType.KING.toString(), 2);
        DataHolder.peopleNumberMap.put(BedType.SOFA.toString(), 1);

        Country countryMKD = countryRepository.save(new Country("Macedonia", "MKD", "flag"));
        Country countryEn = countryRepository.save(new Country("England", "EN", "flag"));
        Country countrySRB = countryRepository.save(new Country("Serbia", "SRB", "flag"));


        City skopje = cityRepository.save(new City("Skopje", countryMKD));
        City bitola = cityRepository.save(new City("Bitola", countryMKD));
        City veles = cityRepository.save(new City("Veles", countryMKD));
        City belgrade = cityRepository.save(new City("Belgrade", countrySRB));
        City london = cityRepository.save(new City("London", countryEn));
        City manchester = cityRepository.save(new City("Manchester", countryEn));


        User user = userRepository.save(new User("user@user.com", passwordEncoder.encode("user"), "User", "user", Role.ROLE_USER));
        User user2 = userRepository.save(new User("user2@user.com", passwordEncoder.encode("user2"), "User", "user", Role.ROLE_USER));
        User admin = userRepository.save(new User("admin@admin.com", passwordEncoder.encode("admin"), "User", "user", Role.ROLE_ADMIN));

        ResObject houseSRB = new ResObject("House in Belgrade", "adresa bb 11 22", "Big house in belgrade with fountain", Category.HOUSE, user, belgrade);
        ResObject apartmentEn = new ResObject("Rooftop apartment", "england 11 22", "Rooftop apartment with wonderful view", Category.APARTMENT, user, london);

        ResObject hotelMKD = resObjectRepository.save( new ResObject("Hotel Makedonija", "adresa 11 22", "Hotel in Macedonia", Category.HOTEL, user, skopje));

        List<ObjectImage> objectImageList = new ArrayList<>();
        objectImageList.add(new ObjectImage(houseSRB, "/assets/room-4.jpg"));

        houseSRB.setObjectImages(objectImageList);

        resObjectRepository.save(houseSRB);
        resObjectRepository.save(apartmentEn);



        Unit unit3 = new Unit(hotelMKD, 70, 5, 100, "Room for 5 people!");
        Unit unit4 = new Unit(hotelMKD, 25, 2, 35, "Room for 2 people!");

        Unit unit1 = unitRepository.save(new Unit(hotelMKD, 22, 2, 20, "Room for 2 people!"));
        Unit unit2 = unitRepository.save(new Unit(hotelMKD, 40, 3, 55, "Room for 4 people!"));
        Unit unit5 = unitRepository.save(new Unit(houseSRB, 125, 4, 150, "Room for 4 people!"));

        BedTypes bedTypes = bedTypesRepository.save(new BedTypes(BedType.TWIN, 2));
        BedTypes bedTypes1 = bedTypesRepository.save(new BedTypes(BedType.KING, 1));
        List<BedTypes> bedTypesList = new ArrayList<>();
        bedTypesList.add(bedTypes1);
        bedTypesList.add(bedTypes);
        unit5.setBedTypes(bedTypesList);
        unitRepository.save(unit5);

        unitRepository.save(unit3);
        unitRepository.save(unit4);


        reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberOf(), ZonedDateTime.now(), ZonedDateTime.now()));
//        reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberOf(), ZonedDateTime.now(), ZonedDateTime.now()));
        reservationRepository.save(new Reservation(user, unit2, unit2.getPrice(), unit2.getNumberOf(), ZonedDateTime.now(), ZonedDateTime.now()));

    }

}
