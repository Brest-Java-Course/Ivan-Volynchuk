package com.epam.brest.task.dao;

import com.epam.brest.task.domain.Mage;
import com.epam.brest.task.domain.MagicScroll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final String MAGEID="mage_id";
    private static final String NAME = "mage_name";
    private static final String LEVEL = "mage_level";
    private static final String EXP = "mage_exp";
    private static final String SCROLLAMOUNT = "scroll_amount";
    private static final String AVERAGEMANACOST = "average_manacost";

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_mage_path}')).inputStream)}")
    private String UPDATE_MAGE;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_amount_mages_path}')).inputStream)}")
    private String GET_AMOUNT_MAGES;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_limit_mages_path}')).inputStream)}")
    private String SELECT_LIMIT_MAGES;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_mage_by_id_path}')).inputStream)}")
    private String REMOVE_MAGE_BY_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_mage_by_name_path}')).inputStream)}")
    private String SELECT_MAGE_BY_NAME;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_mages_path}')).inputStream)}")
    private  String SELECT_ALL_MAGES;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_mage_by_id_path}')).inputStream)}")
    private  String SELECT_MAGE_BY_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_mage_path}')).inputStream)}")
    private String INSERT_MAGE;


    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(MageDAOImpl.class);

    @Override
    public Mage getMageById(Long id) {

        LOGGER.debug("MageDAOImpl:getMageById({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(MAGEID,id);
        return namedJdbcTemplate.queryForObject(SELECT_MAGE_BY_ID,args,new MageMapper());

    }

    @Override
    public Mage getMageByName(String name) {

        LOGGER.debug("MageDAOImpl:getMageByName({})", name);

        Map<String, Object> args = new HashMap(1);
        args.put(NAME,name);
        return namedJdbcTemplate.queryForObject(SELECT_MAGE_BY_NAME, args, new MageMapper());
    }

    @Override
    public List<Mage> getAllMages() {

        LOGGER.debug("MageDAOImpl:getAllMages({})");

        return namedJdbcTemplate.query(SELECT_ALL_MAGES,new MageMapper());
    }

    @Override
    public List<Mage> getLimitMages(Long amt, Long n_from) {

        LOGGER.debug("MageDAOImpl:getLimitMages({})",amt+","+n_from);

        Map<String, Object> args = new HashMap(2);
        args.put("n_from", n_from);
        args.put("amt", amt);
        return namedJdbcTemplate.query(SELECT_LIMIT_MAGES, args, new MageMapper());

    }

    @Override
    public Long amountMages() {

        LOGGER.debug("MageDAOImpl:amountMages()");
        return namedJdbcTemplate.queryForLong(GET_AMOUNT_MAGES, new HashMap(0));
    }

    @Override
    public Long addMage(Mage mage) {

        LOGGER.debug("MageDAOImpl:addMage({})", mage);

        Long mageid;

        KeyHolder holder=new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        parameterSource.addValue(NAME, mage.getName());
        parameterSource.addValue(LEVEL, mage.getLevel());
        parameterSource.addValue(EXP, mage.getExp());
        namedJdbcTemplate.update(INSERT_MAGE, parameterSource,holder);

        mageid=holder.getKey().longValue();

        return mageid;
    }

    @Override
    public void removeMageById(Long id) {

        LOGGER.debug("MageDAOImpl:removeMageById({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(MAGEID,id);
        namedJdbcTemplate.update(REMOVE_MAGE_BY_ID,args);
    }

    @Override
    public void updateMage(Mage mage) {

        LOGGER.debug("MageDAOImpl:updateMage({})", mage);

        Map<String, Object> args = new HashMap(4);
        args.put(MAGEID, mage.getMage_id());
        args.put(NAME, mage.getName());
        args.put(LEVEL, mage.getLevel());
        args.put(EXP, mage.getExp());
        namedJdbcTemplate.update(UPDATE_MAGE,args);


    }

    public class MageMapper implements RowMapper<Mage> {

        @Override
        public Mage mapRow(ResultSet resultSet,int i) throws SQLException {
            Mage mage=new Mage();
            mage.setMage_id(resultSet.getLong(MAGEID));
            mage.setLevel(resultSet.getLong(LEVEL));
            mage.setExp(resultSet.getLong(EXP));
            mage.setAverage_manacost(resultSet.getLong(AVERAGEMANACOST));
            mage.setName(resultSet.getString(NAME));
            mage.setScroll_amount(resultSet.getLong(SCROLLAMOUNT));
            return mage;
        }
    }

}
