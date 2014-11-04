package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Created by fieldistor on 20.10.14.
 */
public class UserDaoImpl implements UserDao{
    //TODO: to sql directory, see DBeaver

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${delete_from_user_path}')).file)}")
    private String DELETE_USER_BY_USERID;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
    private String ADD_NEW_USER_SQL;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_all_users}')).file)}")
    private  String SELECT_ALL_USERS_SQL;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_id}')).file)}")
    private  String SELECT_USER_BY_ID;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_login}')).file)}")
    private  String SELECT_USER_BY_LOGIN;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${update_user}')).file)}")
    private  String UPDATE_USER_SQL;

    private static final String USER_ID = "userid";
    private static final String LOGIN = "login";
    private static final String NAME = "name";

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    public void setDataSource(DataSource dataSource){

        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long addUser(User user) {
        LOGGER.debug("addUser({})",user);

        Long userid;
        KeyHolder holder=new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource= new MapSqlParameterSource();
        parameterSource.addValue(NAME,user.getUserName());
        parameterSource.addValue(LOGIN,user.getLogin());
        parameterSource.addValue(USER_ID,user.getUserId());
        namedJdbcTemplate.update(ADD_NEW_USER_SQL,parameterSource,holder);

        userid=holder.getKey().longValue();
        LOGGER.debug("User with id {} added",userid);
        return userid;
    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");

        return namedJdbcTemplate.query(SELECT_ALL_USERS_SQL,new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);

        Map<String, Object> args = new HashMap(1);
        args.put(USER_ID,userId);
        namedJdbcTemplate.update(DELETE_USER_BY_USERID,args);
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);


        Map<String, Object> args = new HashMap(1);
        args.put(USER_ID,userId);
        return namedJdbcTemplate.queryForObject(SELECT_USER_BY_ID,args,new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);

        Map<String, Object> args = new HashMap(1);
        args.put(LOGIN,login);
        return namedJdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN,args,new UserMapper());
    }

    @Override
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})", user);

        Map<String, Object> args = new HashMap(3);
        args.put(NAME,user.getUserName());
        args.put(LOGIN,user.getLogin());
        args.put(USER_ID,user.getUserId());
        namedJdbcTemplate.update(UPDATE_USER_SQL, args);

    }

    public class UserMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet resultSet,int i) throws SQLException{
            User user=new UserImpl();
            user.setUserId(resultSet.getLong(USER_ID));
            user.setLogin(resultSet.getString(LOGIN));
            user.setUserName(resultSet.getString(NAME));
            return user;
        }
    }

}
