package com.epam.brest.task.dao.tools;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fieldistor on 17.11.14.
 */
//TODO: move to another package
public class TestMagicScrollFactory {

    private final static String DESCRIPTION1 = "Invisibility";

    private final static String DESCRIPTION2 = "Blink";

    private final static String DESCRIPTION3 = "Helicopter";

    private final static Long DEFAULT_DURABILITY = 100L;

    private final static Long DEFAULT_MANACOST = 0L;

    public static MagicScroll getNewScroll() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScroll(String description) {

        return new MagicScroll(null, description, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScroll(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);

    }

    public static MagicScroll getExistScroll(Long id, String description) {

        return new MagicScroll(id, description, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);

    }

    public static MagicScroll getExistScrollWithoutDescription(Long id) {

        return new MagicScroll(id, null, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScrollWithoutDate(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, null, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getExistScrollWithoutManaCost(Long id) {

        return new MagicScroll(id, DESCRIPTION1, DEFAULT_DURABILITY, new LocalDate(), null, null);
    }

    public static MagicScroll getExistScrollWithoutDurability(Long id) {

        return new MagicScroll(id, DESCRIPTION1, null, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutDescription() {

        return new MagicScroll(null, null, DEFAULT_DURABILITY, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutDate() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, null, DEFAULT_MANACOST, null);
    }

    public static MagicScroll getNewScrollWithoutManaCost() {

        return new MagicScroll(null, DESCRIPTION1, DEFAULT_DURABILITY, new LocalDate(), null, null);
    }

    public static MagicScroll getNewScrollWithoutDurability() {

        return new MagicScroll(null, DESCRIPTION1, null, new LocalDate(), DEFAULT_MANACOST, null);
    }

    public static List<MagicScroll> getAllExistScrolls() {

        List<MagicScroll> magicScrolls = new LinkedList<MagicScroll>();
        magicScrolls.add(getExistScroll(0L, DESCRIPTION1));
        magicScrolls.add(getExistScroll(1L, DESCRIPTION2));
        magicScrolls.add(getExistScroll(2L, DESCRIPTION3));

        return magicScrolls;
    }
}
