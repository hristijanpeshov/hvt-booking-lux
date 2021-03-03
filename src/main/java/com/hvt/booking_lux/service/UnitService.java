package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.Unit;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface UnitService {
    List<Unit> listAll(Long resObjectId);
    List<Unit> listUniqueUnitsForResObject(long id);
    boolean isUnitFree(long unitId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople);
    Unit findTheMostExpensive();
    Unit findTheLeastExpensive();
    Unit findTheLargest();
    Unit findTheSmallest();
    Unit save(long resObjectId,double size,int numberPeople,double price,String description);
    Unit edit(long unitId,double size,int numberPeople,double price,String description);
    Unit findById(long unitId);
    Unit delete(long unitId);
}
