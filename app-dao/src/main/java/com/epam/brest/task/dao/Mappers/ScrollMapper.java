package com.epam.brest.task.dao.Mappers;

import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fieldistor on 16.11.14.
 */
public class ScrollMapper implements RowMapper<MagicScroll> {

    private static final String SCROLL_ID="scroll_id";
    private static final String DESCRIPTION = "description";
    private static final String DURABILITY = "durability";
    private static final String DATE = "creation_date";
    private static final String MANA = "mana_cost";
    private static final String MAGE_ID= "mage_id";

    @Override
    public MagicScroll mapRow(ResultSet resultSet,int i) throws SQLException {
        MagicScroll scroll=new MagicScroll();
        scroll.setScroll_id(resultSet.getLong(SCROLL_ID));
        scroll.setDescription(resultSet.getString(DESCRIPTION));
        scroll.setDurability(resultSet.getLong(DURABILITY));
        scroll.setMana_cost(resultSet.getLong(MANA));
        scroll.setMage_id(resultSet.getLong(MAGE_ID));
        scroll.setCreation_date(new LocalDate(resultSet.getDate(DATE)));
        return scroll;
    }
}