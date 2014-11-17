package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 17.11.14.
 */
public class NotFoundException extends AcademyException {

    public NotFoundException(String message, String place, Object object) {
        super(message, place, object);
    }
}
