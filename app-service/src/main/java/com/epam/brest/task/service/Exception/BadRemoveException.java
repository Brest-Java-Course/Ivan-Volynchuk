package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 17.11.14.
 */
public class BadRemoveException extends AcademyException {

    private Object object;

    public BadRemoveException(String message, String place, Object object) {
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
