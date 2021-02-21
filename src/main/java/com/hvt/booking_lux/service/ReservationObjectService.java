package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.enumeration.Category;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.User;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface ReservationObjectService {
    List<ResObject> listAll();
    List<ResObject> listByCountry(long countryId);
    List<Unit> listAllNotAvailable(long resObjectId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople);
    double lowestPriceForUnit(long resObjectId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople);
    List<ResObject> findAllAvailable(ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople, String city  );
    List<ResObject> listByCity(long cityId);
    ResObject save(String name, String address, String description, Category category, User creator, long cityId);
    ResObject edit(long resObjectId,String name, String address, String description, Category category);
    ResObject findResObjectById(long resObjectId);
    ResObject delete(long resObjectId);
    List<ResObject> listByCityName(String city);
}
