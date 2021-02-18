package com.hvt.booking_lux.model.exceptions;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(long id) {
        super("Image with " + id + " was not found!");
    }
}
