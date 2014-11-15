package com.epam.brest.task.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fieldistor on 16.11.14.
 */
public class FormatLocal {


    public static void main(String[] args) {
        System.out.println(new LocalDate());
    }

    static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
        return format.format(date);
    }
}
