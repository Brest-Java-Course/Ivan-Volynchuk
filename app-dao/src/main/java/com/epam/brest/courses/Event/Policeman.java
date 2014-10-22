package com.epam.brest.courses.Event;

/**
 * Created by fieldistor on 22.10.14.
 */
public class Policeman {
    private Gun gun;

    public Policeman(Gun gun){
        this.gun=gun;
    }

    public void perfom(){
        gun.Shot();
    }
}
