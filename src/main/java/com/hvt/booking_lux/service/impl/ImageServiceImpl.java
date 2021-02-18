package com.hvt.booking_lux.service.impl;

import com.hvt.booking_lux.model.exceptions.ImageNotFoundException;
import com.hvt.booking_lux.model.ObjectImage;
import com.hvt.booking_lux.model.ResObject;
import com.hvt.booking_lux.model.Unit;
import com.hvt.booking_lux.model.UnitImages;
import com.hvt.booking_lux.repository.ObjectImageRepository;
import com.hvt.booking_lux.repository.UnitImagesRepository;
import com.hvt.booking_lux.service.ImageService;
import com.hvt.booking_lux.service.ReservationObjectService;
import com.hvt.booking_lux.service.UnitService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ObjectImageRepository objectImageRepository;
    private final UnitImagesRepository unitImagesRepository;
    private final ReservationObjectService reservationObjectService;
    private final UnitService unitService;

    public ImageServiceImpl(ObjectImageRepository objectImageRepository, UnitImagesRepository unitImagesRepository, ReservationObjectService reservationObjectService, UnitService unitService) {
        this.objectImageRepository = objectImageRepository;
        this.unitImagesRepository = unitImagesRepository;
        this.reservationObjectService = reservationObjectService;
        this.unitService = unitService;
    }


    @Override
    public ObjectImage addImageToResObject(long resObjectId, String imgLink) {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        ObjectImage objectImage = new ObjectImage(resObject, imgLink);
        return objectImageRepository.save(objectImage);
    }

    @Override
    public UnitImages addImageToUnit(long unitId, String imgLink) {
        Unit unit = unitService.findById(unitId);
        UnitImages unitImages = new UnitImages(unit, imgLink);
        return unitImagesRepository.save(unitImages);
    }

    @Override
    public ObjectImage removeObjectImage(long resObjectId, long imgId) {
        ResObject resObject = reservationObjectService.findResObjectById(resObjectId);
        ObjectImage objectImage = objectImageRepository.findById(imgId).orElseThrow(() -> new ImageNotFoundException(imgId));
        resObject.getObjectImages().remove(objectImage);
        return objectImage;
    }

    @Override
    public UnitImages removeUnitImage(long unitId, long imgId) {
        Unit unit = unitService.findById(unitId);
        UnitImages unitImages = unitImagesRepository.findById(imgId).orElseThrow(() -> new ImageNotFoundException(imgId));
        unit.getUnitImages().remove(unitImages);
        return unitImages;
    }
}
