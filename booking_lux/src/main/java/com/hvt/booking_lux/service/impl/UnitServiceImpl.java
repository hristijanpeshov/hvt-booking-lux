package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.BedTypes;
import com.hvt.booking_lux.model.enumeration.BedType;
import com.hvt.booking_lux.model.enumeration.Status;
import com.hvt.booking_lux.model.exceptions.ResObjectNotFoundException;
import com.hvt.booking_lux.model.exceptions.UnitHasNoBedsException;
import com.hvt.booking_lux.model.exceptions.UnitNotFoundException;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.exceptions.UnitNumberIsZeroException;
import com.hvt.booking_lux.repository.BedTypesRepository;
import com.hvt.booking_lux.repository.ResObjectRepository;
import com.hvt.booking_lux.repository.ReservationRepository;
import com.hvt.booking_lux.repository.UnitRepository;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final ResObjectRepository resObjectRepository;
    private final ReservationRepository reservationRepository;
    private final BedTypesRepository bedTypesRepository;

    public UnitServiceImpl(UnitRepository unitRepository, ResObjectRepository resObjectRepository, BedTypesRepository bedTypesRepository, ReservationRepository reservationRepository) {
        this.unitRepository = unitRepository;
        this.resObjectRepository = resObjectRepository;
        this.bedTypesRepository = bedTypesRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public boolean isUnitFree(long unitId, ZonedDateTime fromDate, ZonedDateTime toDate, int numberOfPeople){
        return reservationRepository.findAll().stream().filter(s -> s.getUnit().getId().equals(unitId))
                .noneMatch(s -> fromDate.isBefore(s.getToDate()) && (s.getFromDate().isBefore(toDate)));
    }

    @Override
    public List<Unit> listAll(Long resObjectId) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        return unitRepository.findAllByResObject(resObject);
    }

    @Override
    public List<Unit> listUniqueUnitsForResObject(long id) {
        return null;
    }

    @Override
    public Unit findTheMostExpensive() {
        Unit unit = unitRepository.findAll().stream().max(Comparator.comparing(Unit::getPrice)).orElse(null);
        return unit;
    }

    @Override
    public Unit findTheLeastExpensive() {
        Unit unit = unitRepository.findAll().stream().min(Comparator.comparing(Unit::getPrice)).orElse(null);
        return unit;
    }

    @Override
    public Unit findTheLargest() {
        Unit unit = unitRepository.findAll().stream().max(Comparator.comparing(Unit::getSize)).orElse(null);
        return unit;
    }

    @Override
    public Unit findTheSmallest() {
        Unit unit = unitRepository.findAll().stream().min(Comparator.comparing(Unit::getSize)).orElse(null);
        return unit;
    }

    @Override
    public Unit save(long resObjectId, String title, double size, int numberPeople, double price, String description, List<BedType> bedTypes,List<Integer> counts,List<String> images) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        List<BedTypes> bedTypesList = this.toBedTypeList(bedTypes,counts);
        Unit unit = new Unit(resObject, title,size, numberPeople, price, description);
        unit.setBedTypes(bedTypesList);
        List<String> finalImages = images.stream().filter(x->!x.equals("")).collect(Collectors.toList());
        unit.setUnitImages(finalImages);
        return unitRepository.save(unit);
    }

    private List<BedTypes> toBedTypeList(List<BedType> bedTypes,List<Integer> counts)
    {
        List<BedTypes> bedTypesList = new ArrayList<>();
        Iterator<BedType> bedTypeIterator = bedTypes.iterator();
        Iterator<Integer> countsIterator = counts.listIterator();
        while(bedTypeIterator.hasNext() && countsIterator.hasNext())
        {
            BedType bedType = bedTypeIterator.next();
            Integer count = countsIterator.next();
            if(count>0)
            {
                bedTypesList.add(bedTypesRepository.save(new BedTypes(bedType,count)));
            }
        }
        if(bedTypesList.size()==0)
        {
            throw new UnitHasNoBedsException();
        }
        return bedTypesList;
    }

    @Override
    public Unit edit(long unitId, String title,double size, int numberPeople, double price, String description, List<BedType> bedTypes,List<Integer> counts,List<String> images) {
        Unit unit = findById(unitId);
        unit.setSize(size);
        unit.setNumberOf(numberPeople);
        unit.setPrice(price);
        unit.setDescription(description);
        unit.setTitle(title);
        List<BedTypes> bedTypesList = toBedTypeList(bedTypes,counts);
        unit.setBedTypes(bedTypesList);
        List<String> finalImages = images.stream().filter(x->!x.equals("")).collect(Collectors.toList());
        unit.setUnitImages(finalImages);
        return unitRepository.save(unit);
    }

    @Override
    public Unit findById(long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new UnitNotFoundException(unitId));
        return unit;
    }

    @Override
    public Unit findByIdFromReservation(long unitId) {
        return unitRepository.findAllById(unitId).orElseThrow(() -> new UnitNotFoundException(unitId));
    }

    @Override
    public Unit delete(long unitId) {
        Unit unit = findById(unitId);
        unit.setStatus(Status.DELETED);
        return unitRepository.save(unit);
    }

    @Override
    public List<Unit> listAllMoreThan(Long resObjectId, Integer number) {
        return this.listAll(resObjectId).stream().filter(x->x.getNumberOfPeople()>number).collect(Collectors.toList());
    }
}
