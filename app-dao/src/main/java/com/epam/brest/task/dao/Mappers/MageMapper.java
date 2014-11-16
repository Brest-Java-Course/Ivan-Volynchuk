package com.epam.brest.task.dao.Mappers;

import com.epam.brest.task.domain.Mage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fieldistor on 16.11.14.
 */
public class MageMapper implements RowMapper<Mage> {

    private static final String MAGE_ID="mage_id";
    private static final String NAME = "mage_name";
    private static final String SCROLL_AMOUNT = "scroll_amount";
    private static final String AVERAGE_MANACOST = "average_manacost";

    @Override
    public Mage mapRow(ResultSet resultSet,int i) throws SQLException {
        Mage mage=new Mage();
        mage.setMage_id(resultSet.getLong(MAGE_ID));
        mage.setAverage_manacost(resultSet.getLong(AVERAGE_MANACOST));
        mage.setName(resultSet.getString(NAME));
        mage.setScroll_amount(resultSet.getLong(SCROLL_AMOUNT));
        return mage;
    }
}
