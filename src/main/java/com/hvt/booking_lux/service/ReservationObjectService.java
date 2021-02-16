package com.hvt.booking_lux.service;

import com.hvt.booking_lux.enumeration.Category;
import com.hvt.booking_lux.model.City;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.User;

import java.util.List;

public interface ReservationObjectService {
    List<ResObject> listAll();
    List<ResObject> listByCountry(long countryId);
    List<ResObject> listByCity(long cityId);
    ResObject save(String name, String address, String description, Category category, User creator, City city);
    ResObject edit(long resObjectId,String name, String address, String description, Category category, User creator, City city);
    ResObject findResObjectById(long resObjectId);
    ResObject delete(long resObjectId);
}
