package com.epam.brest.courses.domain.exception;

import org.apache.logging.log4j.Logger;

/**
 * Created by fieldistor on 04.11.14.
 */
public class BadInputData extends RuntimeException {

    private String place;

    /**
     * Constructs a new exception with the specified detail message and objectId value.
     *
     * @param message the detail message
     * @param place  the place of error
     */
    public BadInputData(String message, Logger LOGGER, String place) {
        super(message);
        LOGGER.debug(message);
        this.place=place;
    }

    public BadInputData(String message, String place) {
        super(message);
        this.place=place;
    }

    public String getPlace() {
        return place;
    }
}
