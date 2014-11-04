package com.epam.brest.courses.domain.exception;

/**
 * Created by fieldistor on 04.11.14.
 */
public class NotFoundException extends RuntimeException {

    private String objectId;

    /**
     * Constructs a new exception with the specified detail message and objectId value.
     *
     * @param message the detail message
     * @param objectId id of the object that cannot be found.
     */
    public NotFoundException(String message, String objectId) {
        super(message);
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }
}