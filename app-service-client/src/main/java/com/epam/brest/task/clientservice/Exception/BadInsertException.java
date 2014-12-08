package com.epam.brest.task.clientservice.Exception;

/**
 * Created by fieldistor on 17.11.14.
 */
public class BadInsertException extends BadDataException {

    private Object object;

    public BadInsertException(String message, String place, Object object) {
        super(message, place);
        this.object = object;
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
