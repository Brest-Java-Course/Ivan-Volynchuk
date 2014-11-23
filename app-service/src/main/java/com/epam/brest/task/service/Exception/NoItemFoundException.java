package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 23.11.14.
 */
public class NoItemFoundException extends NotFoundException {

    private Object object;

    public NoItemFoundException(String message, String place, Object object) {
        super(message, place);
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "BadInsertException{" +
                " Reason=" + getMessage()+
                " Object=" + object +
                " Place=" + getPlace()+
                '}';
    }
}
