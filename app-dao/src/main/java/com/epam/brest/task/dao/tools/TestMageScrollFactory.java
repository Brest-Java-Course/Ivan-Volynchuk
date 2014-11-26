package com.epam.brest.task.dao.tools;

import com.epam.brest.task.domain.Mage;

/**
 * Created by fieldistor on 17.11.14.
 */
//TODO: move to another package
public class TestMageScrollFactory {

    public static Mage getNewMage() {
        return new Mage("Talnidas", 10L, 55L);
    }

    public static Mage getExistMage(Long id) {
        return new Mage(id, "Talnidas", 10L, 55L);
    }

    public static Mage getExistMage(Long id, String name) {
        return new Mage(id, name, 10L, 55L);
    }

    public static Mage getNewMageWithoutName() { return new Mage(null, 10L, 55L);}

    public static Mage getNewMageWithoutLevel() {

        return new Mage("Talnidas", null, 55L);
    }

    public static Mage getNewMageWithoutExp() {

        return new Mage("Talnidas", 10L, null);
    }

    public static Mage getExistMageWithoutName(Long id) { return new Mage(id, null, 10L, 55L);}

    public static Mage getExistMageWithoutLevel(Long id) {

        return new Mage(id, "Talnidas", null, 55L);
    }

    public static Mage getExistMageWithoutExp(Long id) {

        return new Mage(id, "Talnidas", 10L, null);
    }
}
