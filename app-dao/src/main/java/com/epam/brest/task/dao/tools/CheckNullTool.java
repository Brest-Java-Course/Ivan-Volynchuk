package com.epam.brest.task.dao.tools;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fieldistor on 17.11.14.
 */
public class CheckNullTool {

    static public Long getLong( ResultSet rs, String strColName ) throws SQLException
    {
        Long nValue = rs.getLong(strColName);
        return rs.wasNull() ? null:nValue;
    }
}
