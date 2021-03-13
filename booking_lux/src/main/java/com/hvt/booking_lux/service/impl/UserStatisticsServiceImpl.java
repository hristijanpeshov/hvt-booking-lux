package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Reservation;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.statistics.ResObjectYearStatistics;
import com.hvt.booking_lux.model.statistics.ReviewSentimentStatistics;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UserRepository;
import com.hvt.booking_lux.service.UserStatisticsService;
import com.hvt.booking_lux.model.statistics.CreatorYearStatistics;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public UserStatisticsServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<CreatorYearStatistics> findAnnualPropertyReservationCount(User creator, int year) {
        creator = userRepository.findById("user@user.com").orElseThrow(() -> new UsernameNotFoundException("user@user.com"));

        List<Reservation> reservations = reservationRepository.findAllByUnitResObjectCreator(creator);

        List<CreatorYearStatistics> objects = new ArrayList<>();
        objects = reservationRepository.findAnnualPropertyReservationCount(creator.getUsername(), year);
        return objects;
    }

    @Override
    public List<ResObjectYearStatistics> findAnnualReservationCountForProperty(User creator, int year, long resObjectId) {
        List<Reservation> reservations = reservationRepository.findAllByUnitResObjectCreator(creator);

        List<ResObjectYearStatistics> objects = new ArrayList<>();
        objects = reservationRepository.findAnnualReservationCountForProperty(creator.getUsername(), year, resObjectId);
        return objects;
    }

    @Override
    public Map<String, Integer> findSentimentForResObject(Long resObjectId) {
        Map<String, Integer> map = new HashMap<>();
        map.putIfAbsent("POSITIVE", 0);
        map.putIfAbsent("NEGATIVE", 0);
        List<ReviewSentimentStatistics> reviewSentimentStatisticsList =  reservationRepository.findAllReviewsSentiment(resObjectId);
        reviewSentimentStatisticsList.forEach(s-> map.computeIfPresent(s.getSentiment().toString(), (k, v)  -> v + 1));
        return map;
    }
}

//    select extract(month from r.reservation_date) as month, count(distinct ro.id) from Reservation as r join Unit as u on u.id = r.unit_id join RES_OBJECT as ro on ro.id = u.res_object_id where ro.creator_username=?1 and extract(year from now()) = extract(year from r.reservation_date) group by month
