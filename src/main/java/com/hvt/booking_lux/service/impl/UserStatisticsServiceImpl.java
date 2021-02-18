package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.statistics.ResObjectYearStatistics;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.UserStatisticsService;
import com.hvt.booking_lux.model.statistics.CreatorYearStatistics;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public UserStatisticsServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ResObject> findAllByCreator(User creator) {
        creator = userRepository.findById("user@user.com").orElseThrow(() -> new UsernameNotFoundException("user@user.com"));

        List<Reservation> reservations = reservationRepository.findAllByUnitResObjectCreator(creator);

        List<ResObjectYearStatistics> objects = reservationRepository.findAnnualPropertyReservationCounta(creator.getUsername(), 2021, 1L);


        return new ArrayList<>();
    }
}

//    select extract(month from r.reservation_date) as month, count(distinct ro.id) from Reservation as r join Unit as u on u.id = r.unit_id join RES_OBJECT as ro on ro.id = u.res_object_id where ro.creator_username=?1 and extract(year from now()) = extract(year from r.reservation_date) group by month
