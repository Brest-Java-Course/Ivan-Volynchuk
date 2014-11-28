package com.epam.brest.task.dao.tools;

import com.epam.brest.task.domain.Mage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fieldistor on 17.11.14.
 */
//TODO: move to another package
public class TestMageScrollFactory {

    private final static String MAGE_NAME1 = "Talnidas";

    private final static String MAGE_NAME2 = "Shazaram";

    private final static String MAGE_NAME3 = "Ivan";

    private final static Long DEFAULT_EXP = 55L;

    private final static Long DEFAULT_LEVEL = 10L;

    public static Mage getNewMage() {
        return new Mage(MAGE_NAME1, DEFAULT_LEVEL, DEFAULT_EXP);
    }

    public static Mage getExistMage(Long id) {
        return new Mage(id, MAGE_NAME1, DEFAULT_LEVEL, DEFAULT_EXP);
    }

    public static Mage getExistMage(Long id, String name) {
        return new Mage(id, name, DEFAULT_LEVEL, DEFAULT_EXP);
    }

    public static Mage getNewMageWithoutName() { return new Mage(null, DEFAULT_LEVEL, DEFAULT_EXP);}

    public static Mage getNewMageWithoutLevel() {

        return new Mage(MAGE_NAME1, null, DEFAULT_EXP);
    }

    public static Mage getNewMageWithoutExp() {

        return new Mage(MAGE_NAME1, DEFAULT_LEVEL, null);
    }

    public static Mage getExistMageWithoutName(Long id) { return new Mage(id, null, DEFAULT_LEVEL, DEFAULT_EXP);}

    public static Mage getExistMageWithoutLevel(Long id) {

        return new Mage(id, MAGE_NAME1, null, DEFAULT_EXP);
    }

    public static Mage getExistMageWithoutExp(Long id) {

        return new Mage(id, MAGE_NAME1, DEFAULT_LEVEL, null);
    }

    public static List<Mage> getAllExistMages() {

        List<Mage> mages = new LinkedList<Mage>();
        mages.add(getExistMage(0L, MAGE_NAME1));
        mages.add(getExistMage(1L, MAGE_NAME2));
        mages.add(getExistMage(2L, MAGE_NAME3));
        return mages;
    }
}
