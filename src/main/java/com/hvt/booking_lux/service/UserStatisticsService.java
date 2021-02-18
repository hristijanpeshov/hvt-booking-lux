package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserStatisticsService {

    List<ResObject> findAllByCreator(User creator);

}
