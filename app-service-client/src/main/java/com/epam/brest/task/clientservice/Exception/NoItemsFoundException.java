package com.epam.brest.task.clientservice.Exception;

/**
 * Created by fieldistor on 23.11.14.
 */
public class NoItemsFoundException extends NotFoundException {

    public NoItemsFoundException(String message, String place) {
        super(message, place);
    }
}
