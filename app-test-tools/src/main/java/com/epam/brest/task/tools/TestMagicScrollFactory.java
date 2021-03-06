package com.epam.brest.task.tools;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fieldistor on 17.11.14.
 */
public class TestMagicScrollFactory {

    private final static String DESCRIPTION1 = "Invisibility";

    private final static String DESCRIPTION2 = "Blink";

    private final static String DESCRIPTION3 = "Helicopter";

    private final static Long DEFAULT_DURABILITY = 100L;

    private final static Long DEFAULT_MANACOST = 0L;

    private final static LocalDate DEFAULT_DATE = new LocalDate(2014,11,30);

    public static MagicScroll getNewScroll() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScroll(String description) {

        return new MagicScroll(null, description, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScroll(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);

    }

    public static MagicScroll getExistScroll(Long id, String description) {

        return new MagicScroll(id, description, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);

    }

    public static MagicScroll getExistScrollWithoutDescription(Long id) {

        return new MagicScroll(id, null, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScrollWithoutDate(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, null, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScrollWithoutManaCost(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, DEFAULT_DATE, null, null);
    }

    public static MagicScroll getExistScrollWithoutDurability(Long id) {

        return new MagicScroll(id, DESCRIPTION1, null, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutDescription() {

        return new MagicScroll(null, null, DEFAULT_DURABILITY, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutDate() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, null, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutManaCost() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, DEFAULT_DATE, null, null);
    }

    public static MagicScroll getNewScrollWithoutDurability() {

        return new MagicScroll(null, DESCRIPTION1, null, DEFAULT_DATE, DEFAULT_MANACOST, null);
    }

    public static List<MagicScroll> getAllExistScrolls() {

        List<MagicScroll> magicScrolls = new LinkedList<MagicScroll>();
        magicScrolls.add(getExistScroll(0L, DESCRIPTION1));
        magicScrolls.add(getExistScroll(1L, DESCRIPTION2));
        magicScrolls.add(getExistScroll(2L, DESCRIPTION3));

        return magicScrolls;
    }
}
