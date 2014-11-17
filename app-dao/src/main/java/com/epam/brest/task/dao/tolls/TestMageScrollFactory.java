package com.epam.brest.task.dao.tolls;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

/**
 * Created by fieldistor on 17.11.14.
 */
public class TestMageScrollFactory {

    public static Mage getNewMage() {
        return new Mage("Talnidas");
    }
}
