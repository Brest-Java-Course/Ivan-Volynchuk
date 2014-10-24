package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;


import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.jdbc.core.*;

/**
 * Created by fieldistor on 20.10.14.
 */
public class UserDaoImpl implements UserDao{
    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource){

        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {
        LOGGER.debug("addUser({})",user);
        String sql="INSERT INTO USER (userid,login,name) VALUES (?,?,?)";
        Object args[]={user.getUserId(),user.getLogin(),user.getUserName()};
        jdbcTemplate.update(sql,args);

    }

    @Override
    public List<User> getUsers() {
        LOGGER.debug("getUsers()");
        String sql="SELECT userid, login, name from USER";
        return jdbcTemplate.query(sql,new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);
        String sql="DELETE FROM USER WHERE userid=?";
        Object args[]={userId};
        jdbcTemplate.update(sql,args);
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);
        String sql="SELECT userid, login, name from USER WHERE userid=?";
        Object args[]={userId};
        return jdbcTemplate.queryForObject(sql,args,new UserMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);
        String sql="SELECT userid, login, name from USER WHERE login=?";
        Object args[]={login};
        return jdbcTemplate.queryForObject(sql,args,new UserMapper());
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
