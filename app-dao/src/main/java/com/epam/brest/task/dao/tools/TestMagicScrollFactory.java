package com.epam.brest.task.dao.tools;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;

/**
 * Created by fieldistor on 17.11.14.
 */
public class TestMagicScrollFactory {

    public static MagicScroll getNewScroll() {
        return new MagicScroll(null, "New_Scroll_Description", 100L, new LocalDate(), 0L, null);
    }
}
