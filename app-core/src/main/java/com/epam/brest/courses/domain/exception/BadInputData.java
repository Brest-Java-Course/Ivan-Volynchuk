package com.epam.brest.courses.domain.exception;

import org.apache.logging.log4j.Logger;

/**
 * Created by fieldistor on 04.11.14.
 */
public class BadInputData extends RuntimeException {


    /**
     * Constructs a new exception with the specified detail message and objectId value.
     *
     * @param message the detail message
     */
    public BadInputData(String message, Logger LOGGER) {
        super(message);
        LOGGER.debug(message);
    }

    public BadInputData(String message) {
        super(message);

    }


}
