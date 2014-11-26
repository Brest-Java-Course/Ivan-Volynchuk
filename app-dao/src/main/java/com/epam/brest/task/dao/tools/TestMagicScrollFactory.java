package com.epam.brest.task.dao.tools;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

/**
 * Created by fieldistor on 17.11.14.
 */
//TODO: move to another package
public class TestMagicScrollFactory {

    public static MagicScroll getNewScroll() {

        return new MagicScroll(null, "Invisibility", 100L, new LocalDate(), 0L, null);
    }

    public static MagicScroll getNewScroll(String description) {

        return new MagicScroll(null, description, 100L, new LocalDate(), 0L, null);
    }

    public static MagicScroll getExistScroll(Long id) {

        return new MagicScroll(id, "Invisibility", 100L, new LocalDate(), 0L, null);

    }

    public static MagicScroll getExistScroll(Long id, String description) {

        return new MagicScroll(id, description, 100L, new LocalDate(), 0L, null);

    }


    public static MagicScroll getExistScrollWithoutDescription(Long id) {

        return new MagicScroll(id, null, 100L, new LocalDate(), 0L, null);
    }

    public static MagicScroll getExistScrollWithoutDate(Long id) {

        return new MagicScroll(id, "Invisibility", 100L, null, 0L, null);
    }

    public static MagicScroll getExistScrollWithoutManaCost(Long id) {

        return new MagicScroll(id, "Invisibility", 100L, new LocalDate(), null, null);
    }

    public static MagicScroll getExistScrollWithoutDurability(Long id) {

        return new MagicScroll(id, "Invisibility", null, new LocalDate(), 0L, null);
    }

    public static MagicScroll getNewScrollWithoutDescription() {

        return new MagicScroll(null, null, 100L, new LocalDate(), 0L, null);
    }

    public static MagicScroll getNewScrollWithoutDate() {

        return new MagicScroll(null, "Invisibility", 100L, null, 0L, null);
    }

    public static MagicScroll getNewScrollWithoutManaCost() {

        return new MagicScroll(null, "Invisibility", 100L, new LocalDate(), null, null);
    }

    public static MagicScroll getNewScrollWithoutDurability() {

        return new MagicScroll(null, "Invisibility", null, new LocalDate(), 0L, null);
    }
}
