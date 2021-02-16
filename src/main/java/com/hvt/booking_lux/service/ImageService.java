package com.hvt.booking_lux.service;

import com.hvt.booking_lux.model.ObjectImage;
import com.hvt.booking_lux.model.UnitImages;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    ObjectImage addImageToResObject(long resObjectId,String imgLink);
    UnitImages addImageToUnit(long unitId,String imgLink);
    ObjectImage removeObjectImage(long resObjectId,long imgId);
    UnitImages removeUnitImage(long unitId,long imgId);
}
