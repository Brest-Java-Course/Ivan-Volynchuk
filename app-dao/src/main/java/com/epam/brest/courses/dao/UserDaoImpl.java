package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;


import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * Created by fieldistor on 20.10.14.
 */
public class UserDaoImpl implements UserDao{
    private static final String DELETE_USER_BY_USERID = "DELETE FROM USER WHERE userid=:userid";
    private static final String ADD_NEW_USER_SQL = "INSERT INTO USER (userid,login,name) VALUES (:userid, :login, :name)";
    private static final String SELECT_ALL_USERS_SQL = "SELECT userid, login, name from USER";
    private static final String SELECT_USER_BY_ID = "SELECT userid, login, name from USER WHERE userid=:userid";
    private static final String SELECT_USER_BY_LOGIN = "SELECT userid, login, name from USER WHERE login=:login";

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private static final Logger LOGGER = LogManager.getLogger();

    public void setDataSource(DataSource dataSource){

        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {
        LOGGER.debug("addUser({})",user);

        Map<String, Object> args = new HashMap(3);
        args.put("name",user.getUserName());
        args.put("login",user.getLogin());
        args.put("userid",user.getUserId());
        namedJdbcTemplate.update(ADD_NEW_USER_SQL,args);

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
        args.put("userid",userId);
        namedJdbcTemplate.update(DELETE_USER_BY_USERID,args);
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);


        Map<String, Object> args = new HashMap(1);
        args.put("userid",userId);
        return namedJdbcTemplate.queryForObject(SELECT_USER_BY_ID,args,new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);

        Map<String, Object> args = new HashMap(1);
        args.put("login",login);
        return namedJdbcTemplate.queryForObject(SELECT_USER_BY_LOGIN,args,new UserMapper());
    }

    public class UserMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet resultSet,int i) throws SQLException{
            User user=new User();
            user.setUserId(resultSet.getLong("userId"));
            user.setLogin(resultSet.getString("login"));
            user.setUserName(resultSet.getString("name"));
            return user;
        }
    }

}
