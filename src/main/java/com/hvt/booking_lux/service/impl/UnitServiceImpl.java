package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.exceptions.ResObjectNotFoundException;
import com.hvt.booking_lux.model.exceptions.UnitNotFoundException;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.exceptions.UnitNumberIsZeroException;
import com.hvt.booking_lux.repository.ResObjectRepository;
import com.hvt.booking_lux.repository.UnitRepository;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final ResObjectRepository resObjectRepository;

    public UnitServiceImpl(UnitRepository unitRepository, ResObjectRepository resObjectRepository) {
        this.unitRepository = unitRepository;
        this.resObjectRepository = resObjectRepository;
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
        Unit unit = unitRepository.findAll().stream().max(Comparator.comparing(Unit::getPrice)).orElseThrow(UnitNumberIsZeroException::new);
        return unit;
    }

    @Override
    public Unit findTheLeastExpensive() {
        Unit unit = unitRepository.findAll().stream().min(Comparator.comparing(Unit::getPrice)).orElseThrow(UnitNumberIsZeroException::new);
        return unit;
    }

    @Override
    public Unit findTheLargest() {
        Unit unit = unitRepository.findAll().stream().max(Comparator.comparing(Unit::getSize)).orElseThrow(UnitNumberIsZeroException::new);
        return unit;
    }

    @Override
    public Unit findTheSmallest() {
        Unit unit = unitRepository.findAll().stream().min(Comparator.comparing(Unit::getSize)).orElseThrow(UnitNumberIsZeroException::new);
        return unit;
    }

    @Override
    public Unit save(long resObjectId, double size, int numberPeople, double price, String description) {
        ResObject resObject = resObjectRepository.findById(resObjectId).orElseThrow(() -> new ResObjectNotFoundException(resObjectId));
        Unit unit = new Unit(resObject, size, numberPeople, price, description);
        return unitRepository.save(unit);
    }

    @Override
    public Unit edit(long unitId, double size, int numberPeople, double price, String description) {
        Unit unit = findById(unitId);
        unit.setSize(size);
        unit.setNumberOf(numberPeople);
        unit.setPrice(price);
        unit.setDescription(description);
        return unitRepository.save(unit);
    }

    @Override
    public Unit findById(long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> new UnitNotFoundException(unitId));
        return unit;
    }

    @Override
    public Unit delete(long unitId) {
        Unit unit = findById(unitId);
        unitRepository.delete(unit);
        return unit;
    }
}
