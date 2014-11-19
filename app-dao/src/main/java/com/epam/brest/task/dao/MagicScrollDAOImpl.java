package com.epam.brest.task.dao;

import com.epam.brest.task.dao.tools.CheckNullTool;
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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fieldistor on 15.11.14.
 */
public class MagicScrollDAOImpl implements MagicScrollDAO {

    private static final String SCROLL_ID="scroll_id";
    private static final String DESCRIPTION = "description";
    private static final String DURABILITY = "durability";
    private static final String DATE = "creation_date";
    private static final String MANA = "mana_cost";
    private static final String MAGE_ID= "mage_id";

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_limit_mage_scrolls_path}')).inputStream)}")
    private String SELECT_LIMIT_MAGE_SCROLLS;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_limit_scrolls_without_mage_path}')).inputStream)}")
    private String SELECT_LIMIT_SCROLLS_WITHOUT_MAGE;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_limit_scrolls_path}')).inputStream)}")
    private String SELECT_LIMIT_SCROLLS;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_scrolls_without_mage_path}')).inputStream)}")
    private String SELECT_SCROLLS_WITHOUT_MAGE;

    /*
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${scroll_amount_by_mage_id_path}')).inputStream)}")
    private String SCROLL_AMOUNT_BY_MAGE_ID;
    */

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${clear_mage_id_path}')).inputStream)}")
    private String CLEAR_MAGE_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_mage_scrolls_path}')).inputStream)}")
    private String SELECT_ALL_MAGE_SCROLLS;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${insert_scroll_path}')).inputStream)}")
    private String INSERT_SCROLL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_all_scrolls_path}')).inputStream)}")
    private String SELECT_ALL_SCROLLS;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_scroll_by_id_path}')).inputStream)}")
    private String SELECT_SCROLL_BY_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${select_scroll_by_description_path}')).inputStream)}")
    private String SELECT_SCROLL_BY_DESCRIPTION;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${remove_scroll_by_id_path}')).inputStream)}")
    private String REMOVE_SCROLL_BY_ID;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_scroll_path}')).inputStream)}")
    private String UPDATE_SCROLL;


    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger(MagicScrollDAOImpl.class);

    @Override
    public Long addMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("MagicScrollDAOImpl:addMagicScroll({})", magicScroll);

        Long scrollid;

        KeyHolder holder=new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        parameterSource.addValue(DESCRIPTION, magicScroll.getDescription());
        parameterSource.addValue(DURABILITY, magicScroll.getDurability());
        parameterSource.addValue(DATE, magicScroll.getCreation_date().toString());
        parameterSource.addValue(MANA, magicScroll.getMana_cost());
        parameterSource.addValue(MAGE_ID, magicScroll.getMage_id());
        namedJdbcTemplate.update(INSERT_SCROLL, parameterSource,holder);

        scrollid=holder.getKey().longValue();

        return scrollid;
    }

    @Override
    public List<MagicScroll> getAllMagicScrolls() {

        LOGGER.debug("MagicScrollDAOImpl:getAllMagicScrolls({})");

        return namedJdbcTemplate.query(SELECT_ALL_SCROLLS,new ScrollMapper());
    }

    @Override
    public List<MagicScroll> getLimitScrolls(Long amt, Long n_from) {

        LOGGER.debug("MagicScrollDAOImpl:getLimitScrolls({})",amt+","+n_from);

        Map<String, Object> args = new HashMap(2);
        args.put("n_from", n_from);
        args.put("amt", amt);
        return namedJdbcTemplate.query(SELECT_LIMIT_SCROLLS, args, new ScrollMapper());
    }

    @Override
    public void removeMagicScroll(Long id) {

        LOGGER.debug("MagicScrollDAOImpl:removeMagicScroll({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(SCROLL_ID,id);
        namedJdbcTemplate.update(REMOVE_SCROLL_BY_ID,args);
    }

    @Override
    public MagicScroll getMagicScrollById(Long id) {

        LOGGER.debug("MagicScrollDAOImpl:getMagicScrollById({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(SCROLL_ID,id);
        return namedJdbcTemplate.queryForObject(SELECT_SCROLL_BY_ID,args,new ScrollMapper());
    }

    @Override
    public MagicScroll getMagicScrollByDescription(String description) {

        LOGGER.debug("MagicScrollDAOImpl:getMagicScrollByDescription({})", description);

        Map<String, Object> args = new HashMap(1);
        args.put(DESCRIPTION,description);
        return namedJdbcTemplate.queryForObject(SELECT_SCROLL_BY_DESCRIPTION, args, new ScrollMapper());
    }

    @Override
    public void updateMagicScroll(MagicScroll magicScroll) {

        LOGGER.debug("MagicScrollDAOImpl:updateMagicScroll({})", magicScroll);

        Map<String, Object> args = new HashMap(3);
        args.put(SCROLL_ID, magicScroll.getScroll_id());
        args.put(DESCRIPTION, magicScroll.getDescription());
        args.put(DURABILITY, magicScroll.getDurability());
        args.put(DATE, magicScroll.getCreation_date().toString());
        args.put(MANA, magicScroll.getMana_cost());
        args.put(MAGE_ID, magicScroll.getMage_id());
        namedJdbcTemplate.update(UPDATE_SCROLL, args);
    }

    @Override
    public List<MagicScroll> getMagicScrollsByMageId(Long id) {

        LOGGER.debug("MagicScrollDAOImpl:getMagicScrollsByMageId({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(MAGE_ID,id);
        return namedJdbcTemplate.query(SELECT_ALL_MAGE_SCROLLS, args,new ScrollMapper());


    }

    @Override
    public List<MagicScroll> getLimitMagicScrollsByMageId(Long id, Long amt, Long n_from) {

        LOGGER.debug("MagicScrollDAOImpl:getLimitMagicScrollsByMageId({})",amt+","+n_from);

        Map<String, Object> args = new HashMap(2);
        args.put("n_from", n_from);
        args.put("amt", amt);
        args.put(MAGE_ID, id);
        return namedJdbcTemplate.query(SELECT_LIMIT_MAGE_SCROLLS, args, new ScrollMapper());
    }

    @Override
    public void clearScrollsByMagicId(Long id) {

        LOGGER.debug("MagicScrollDAOImpl:clearScrollsByMagicId({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(MAGE_ID,id);
        namedJdbcTemplate.update(CLEAR_MAGE_ID, args);
    }

    @Override
    public List<MagicScroll> getMagicScrollsWithoutMage() {

        LOGGER.debug("MagicScrollDAOImpl:getMagicScrollsWithoutMage({})");

        return namedJdbcTemplate.query(SELECT_SCROLLS_WITHOUT_MAGE,new ScrollMapper());
    }

    @Override
    public List<MagicScroll> getLimitMagicScrollsWithoutMage(Long amt, Long n_from) {

        LOGGER.debug("MagicScrollDAOImpl:getLimitMagicScrollsWithoutMage({})",amt+","+n_from);

        Map<String, Object> args = new HashMap(2);
        args.put("n_from", n_from);
        args.put("amt", amt);
        return namedJdbcTemplate.query(SELECT_LIMIT_SCROLLS_WITHOUT_MAGE, args, new ScrollMapper());
    }
    /*
    @Override
    public Long getScrollAmountByMageId(Long id) {


        LOGGER.debug("MagicScrollDAOImpl:getScrollAmountByMageId({})", id);

        Map<String, Object> args = new HashMap(1);
        args.put(MAGE_ID,id);
        Long amount = namedJdbcTemplate.queryForLong(SCROLL_AMOUNT_BY_MAGE_ID,args);

        LOGGER.debug("Amount is {}", amount);
        return amount;
    }

    @Override
    public Long getAverageManacostByMageId(Long id) {

        throw new NotImplementedException();
    }
    */
    public class ScrollMapper implements RowMapper<MagicScroll> {

        @Override
        public MagicScroll mapRow(ResultSet resultSet,int i) throws SQLException {

            MagicScroll scroll=new MagicScroll();
            scroll.setScroll_id(resultSet.getLong(SCROLL_ID));
            scroll.setDescription(resultSet.getString(DESCRIPTION));
            scroll.setDurability(resultSet.getLong(DURABILITY));
            scroll.setMana_cost(resultSet.getLong(MANA));
            scroll.setMage_id(CheckNullTool.getLong(resultSet,MAGE_ID));
            scroll.setCreation_date(new LocalDate(resultSet.getDate(DATE)));
            return scroll;
        }
    }

}
