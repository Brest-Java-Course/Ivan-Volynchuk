package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 17.11.14.
 */
public class BadInsertException extends AcademyException {

    public BadInsertException(String message, String place, Object object) {
        super(message, place, object);
    }
}
