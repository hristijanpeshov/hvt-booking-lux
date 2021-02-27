package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.BedTypes;
import com.hvt.booking_lux.model.enumeration.BedType;

import java.util.stream.Stream;

public class BedTypesService {
    public static Integer searchForBedType(BedType bedType, Stream<BedTypes> bedTypesStream)
    {
        return bedTypesStream.filter(x -> x.getBedType().equals(bedType)).findFirst().map(BedTypes::getCount).orElse(0);
    }
}
