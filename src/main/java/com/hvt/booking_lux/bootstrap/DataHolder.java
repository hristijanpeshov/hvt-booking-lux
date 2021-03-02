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
public class DataHolder {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final UnitRepository unitRepository;
    private final ResObjectRepository resObjectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;
    private final BedTypesRepository bedTypesRepository;
    private final ReviewRepository reviewRepository;
    public static HashMap<String, Integer> peopleNumberMap = new HashMap<>();


    public DataHolder(CityRepository cityRepository, CountryRepository countryRepository, UnitRepository unitRepository, ResObjectRepository resObjectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ReservationRepository reservationRepository, BedTypesRepository bedTypesRepository, ReviewRepository reviewRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.unitRepository = unitRepository;
        this.resObjectRepository = resObjectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationRepository = reservationRepository;
        this.bedTypesRepository = bedTypesRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostConstruct
    public void init() {
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


        User user = userRepository.save(new User("user@user.com", passwordEncoder.encode("user"), "User", "user", Role.ROLE_USER, "Maleshevska 22a", "072 69 69 69"));
        User user2 = userRepository.save(new User("user2@user.com", passwordEncoder.encode("user2"), "User", "user", Role.ROLE_USER, "Velko Vlahovik 20/34", "071 111 111"));
        User admin = userRepository.save(new User("admin@admin.com", passwordEncoder.encode("admin"), "User", "user", Role.ROLE_ADMIN, "Sasa 16/b", "071 234 567"));

        ResObject houseSRB = new ResObject("House in Belgrade", "adresa bb 11 22", "Big house in belgrade with fountain", Category.HOUSE, user, belgrade);
        ResObject apartmentEn = new ResObject("Rooftop apartment", "england 11 22", "Rooftop apartment with wonderful view", Category.APARTMENT, user, london);
        List<String> objectImageList = new ArrayList<>();
        objectImageList.add("/assets/room-4.jpg");
        objectImageList.add("/assets/room-3.jpg");
        objectImageList.add("/assets/room-2.jpg");

        ResObject hotelMKD = new ResObject("Hotel Makedonija", "adresa 11 22", "Hotel in Macedonia", Category.HOTEL, user, skopje);

        hotelMKD.setObjectImages(objectImageList);

        houseSRB.setObjectImages(objectImageList);

        resObjectRepository.save(hotelMKD);
        resObjectRepository.save(houseSRB);
        resObjectRepository.save(apartmentEn);



        Unit unit3 = new Unit(hotelMKD, "Room for 5 people!",70, 5, 100, "Room for 5 people!");
        Unit unit4 = new Unit(hotelMKD, "Room for 2 people!",25, 2, 35, "Room for 2 people!");

        Unit unit1 = unitRepository.save(new Unit(hotelMKD,"Room for 2 people!" ,22, 2, 20, "Room for 2 people!"));
        Unit unit2 = unitRepository.save(new Unit(hotelMKD,"Room for 4 people!" ,40, 3, 55, "Room for 4 people!"));
        Unit unit5 = unitRepository.save(new Unit(houseSRB, "Room for 4 people!",125, 4, 150, "Room for 4 people!"));

        List<String> unitImages = new ArrayList<String>();
        unitImages.add("https://images.unsplash.com/photo-1603794067602-9feaa4f70e0c?ixid=MXwxMjA3fDB8MHxzZWFyY2h8MXx8bGl2aW5nJTIwcm9vbXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60");
        unitImages.add("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8bGl2aW5nJTIwcm9vbXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=900&q=60");
        unitImages.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/living-room-ideas-rds-work-queens-road-01-1594233253.jpg?crop=1.00xw:0.803xh;0,0.176xh&resize=640:*");
        unit1.setUnitImages(unitImages);

        unitRepository.save(unit1);

        unit5.setUnitImages(objectImageList);

        BedTypes bedTypes = bedTypesRepository.save(new BedTypes(BedType.TWIN, 2));
        BedTypes bedTypes1 = bedTypesRepository.save(new BedTypes(BedType.KING, 1));
        List<BedTypes> bedTypesList = new ArrayList<>();
        bedTypesList.add(bedTypes1);
        bedTypesList.add(bedTypes);
        unit5.setBedTypes(bedTypesList);
        unitRepository.save(unit5);

        List<BedTypes> bedTypesListUnit3 = new ArrayList<>();
        bedTypesListUnit3.add(bedTypesRepository.save(new BedTypes(BedType.DOUBLE, 1)));
        bedTypesListUnit3.add(bedTypesRepository.save(new BedTypes(BedType.QUEEN, 1)));
        bedTypesListUnit3.add(bedTypesRepository.save(new BedTypes(BedType.SOFA, 1)));
        unit3.setBedTypes(bedTypesListUnit3);

        List<BedTypes> bedTypesListUnit4 = new ArrayList<>();
        bedTypesListUnit4.add(bedTypesRepository.save(new BedTypes(BedType.QUEEN, 1)));
        unit4.setBedTypes(bedTypesListUnit4);

        List<BedTypes> bedTypesListUnit1 = new ArrayList<>();
        bedTypesListUnit1.add(bedTypesRepository.save(new BedTypes(BedType.KING, 1)));
        unit1.setBedTypes(bedTypesListUnit1);

        List<BedTypes> bedTypesListUnit2 = new ArrayList<>();
        bedTypesListUnit2.add(bedTypesRepository.save(new BedTypes(BedType.QUEEN, 2)));
        unit2.setBedTypes(bedTypesListUnit2);

        unitRepository.save(unit1);
        unitRepository.save(unit2);
        unitRepository.save(unit3);
        unitRepository.save(unit4);


        Reservation reservation = reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberOf(), ZonedDateTime.now().minusDays(10), ZonedDateTime.now().minusDays(6)));
//        reservationRepository.save(new Reservation(user, unit1, unit1.getPrice(), unit1.getNumberOf(), ZonedDateTime.now(), ZonedDateTime.now()));
        reservationRepository.save(new Reservation(user, unit2, unit2.getPrice(), unit2.getNumberOf(), ZonedDateTime.now(), ZonedDateTime.now()));

    }
}
