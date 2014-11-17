package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 17.11.14.
 */
public class BadUpdateException extends AcademyException {

    public BadUpdateException(String message, String place, Object object) {
        super(message, place, object);
    }
}
