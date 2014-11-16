package com.epam.brest.task.dao;

import com.epam.brest.task.domain.MagicScroll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
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

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    //private static final Logger LOGGER = LogManager.getLogger(MagicScrollDAOImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {

        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addMagicScroll(MagicScroll magicScroll) {


        Long scrollid;

        KeyHolder holder=new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        parameterSource.addValue(DESCRIPTION, magicScroll.getDescription());
        parameterSource.addValue(DURABILITY, magicScroll.getDurability());
        parameterSource.addValue(DATE, magicScroll.getCreation_date().toString());
        parameterSource.addValue(MANA, magicScroll.getMana_cost());
        namedJdbcTemplate.update(INSERT_SCROLL, parameterSource,holder);

        scrollid=holder.getKey().longValue();

        return scrollid;
    }

    @Override
    public List<MagicScroll> getAllMagicScrolls() {

        return namedJdbcTemplate.query(SELECT_ALL_SCROLLS,new ScrollMapper());
    }

    @Override
    public void removeMagicScroll(Long id) {

        Map<String, Object> args = new HashMap(1);
        args.put(SCROLL_ID,id);
        namedJdbcTemplate.update(REMOVE_SCROLL_BY_ID,args);
    }

    @Override
    public MagicScroll getMagicScrollById(Long id) {

        Map<String, Object> args = new HashMap(1);
        args.put(SCROLL_ID,id);
        return namedJdbcTemplate.queryForObject(SELECT_SCROLL_BY_ID,args,new ScrollMapper());
    }

    @Override
    public MagicScroll getMagicScrollByDescription(String description) {

        Map<String, Object> args = new HashMap(1);
        args.put(DESCRIPTION,description);
        return namedJdbcTemplate.queryForObject(SELECT_SCROLL_BY_DESCRIPTION, args, new ScrollMapper());
    }

    @Override
    public void updateMagicScroll(MagicScroll magicScroll) {

        Map<String, Object> args = new HashMap(3);
        args.put(SCROLL_ID, magicScroll.getScroll_id());
        args.put(DESCRIPTION, magicScroll.getDescription());
        args.put(DURABILITY, magicScroll.getDurability());
        args.put(DATE, magicScroll.getCreation_date().toString());
        args.put(MANA, magicScroll.getMana_cost());
        namedJdbcTemplate.update(UPDATE_SCROLL, args);
    }

    @Override
    public void setMageById(Long id) {
        throw new NotImplementedException();
    }


    public class ScrollMapper implements RowMapper<MagicScroll> {
        @Override
        public MagicScroll mapRow(ResultSet resultSet,int i) throws SQLException {
            MagicScroll scroll=new MagicScroll();
            scroll.setScroll_id(resultSet.getLong(SCROLL_ID));
            scroll.setDescription(resultSet.getString(DESCRIPTION));
            scroll.setDurability(resultSet.getLong(DURABILITY));
            scroll.setMana_cost(resultSet.getLong(MANA));
            scroll.setCreation_date(new LocalDate(resultSet.getDate(DATE)));
            return scroll;
        }
    }
}