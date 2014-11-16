package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fieldistor on 16.11.14.
 */
public class MageDAOImpl implements MageDAO {

    private static final String MAGE_ID="mage_id";
    private static final String NAME = "mage_name";
    private static final String SCROLL_AMOUNT = "scroll_amount";
    private static final String AVERAGE_MANACOST = "average_manacost";

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_mage_by_name_path}')).inputStream)}")
    private String SELECT_MAGE_BY_NAME;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_mages_path}')).inputStream)}")
    private  String SELECT_ALL_MAGES;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_mage_by_id_path}')).inputStream)}")
    private  String SELECT_MAGE_BY_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_mage_path}')).inputStream)}")
    private String INSERT_MAGE;


    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {

        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Mage getMageById(Long id) {

        Map<String, Object> args = new HashMap(1);
        args.put(MAGE_ID,id);
        return namedJdbcTemplate.queryForObject(SELECT_MAGE_BY_ID,args,new MageMapper());

    }

    @Override
    public Mage getMageByName(String name) {

        Map<String, Object> args = new HashMap(1);
        args.put(NAME,name);
        return namedJdbcTemplate.queryForObject(SELECT_MAGE_BY_NAME, args, new MageMapper());
    }

    @Override
    public List<MagicScroll> getAllScrolls() {

        return null;
    }

    @Override
    public List<Mage> getAllMages() {

        return namedJdbcTemplate.query(SELECT_ALL_MAGES,new MageMapper());
    }

    @Override
    public Long addMage(Mage mage) {

        Long mageid;

        KeyHolder holder=new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        parameterSource.addValue(NAME, mage.getName());
        parameterSource.addValue(SCROLL_AMOUNT, 0);
        parameterSource.addValue(AVERAGE_MANACOST, 0);
        namedJdbcTemplate.update(INSERT_MAGE, parameterSource,holder);

        mageid=holder.getKey().longValue();

        return mageid;
    }

    public class MageMapper implements RowMapper<Mage> {
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
}