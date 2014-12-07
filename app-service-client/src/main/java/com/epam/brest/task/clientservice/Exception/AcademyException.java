package com.epam.brest.task.clientservice.Exception;

/**
 * Created by fieldistor on 16.11.14.
 */
public class AcademyException extends RuntimeException {

    private String place;

    public AcademyException(String message, String place){

        super(message);
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public String toString() {
        return "AcademyException{" +
                " Reason = " + super.getMessage() +
                " Place = " + place +
                '}';
    }
}
