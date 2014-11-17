package com.epam.brest.task.service.Exception;

/**
 * Created by fieldistor on 16.11.14.
 */
public class AcademyException extends RuntimeException {

    private Object object;
    private String place;

    public AcademyException(String message, String place, Object object){

        super(message);
        this.object = object;
        this.place = place;
    }

    public String getObject() {
        return object.toString();
    }

    @Override
    public String toString() {
        return "AcademyException{" +
                "Reason = " + super.getMessage() +
                "Object = " + object +
                "Place = " + place +
                '}';
    }
}
