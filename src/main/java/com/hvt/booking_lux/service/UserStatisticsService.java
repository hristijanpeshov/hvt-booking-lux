package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import com.hvt.booking_lux.model.statistics.CreatorYearStatistics;
import com.hvt.booking_lux.model.statistics.ResObjectYearStatistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserStatisticsService {

    List<CreatorYearStatistics> findAnnualPropertyReservationCount(User creator, int year);

    List<ResObjectYearStatistics> findAnnualReservationCountForProperty(User creator, int year, long resObjectId);

}
