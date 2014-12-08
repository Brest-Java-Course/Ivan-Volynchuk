package com.epam.brest.task.clientservice.Exception;

/**
 * Created by fieldistor on 12/8/14.
 */
public class BadDataException extends AcademyException {

    public BadDataException(String message, String place) {
        super(message, place);
    }
}
