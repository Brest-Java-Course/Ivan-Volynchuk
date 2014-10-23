package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;


import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.*;

/**
 * Created by fieldistor on 20.10.14.
 */
public class UserDaoImpl implements UserDao{

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO USER (userid,login,name) VALUES (?,?,?)",user.getUserId(),user.getLogin(),user.getUserName());

    }

    @Override
    public List<User> getUsers() {

        return jdbcTemplate.query("SELECT userid, login, name from USER",new UserMapper());
    }

    @Override
    public void removeUser(Long userId) {
        jdbcTemplate.update("DELETE FROM USER WHERE userid=?",userId);
    }

    @Override
    public User getUserById(Long userId) {
        return jdbcTemplate.queryForObject("SELECT userid, login, name from USER WHERE userid=?",new Object[] {userId},new UserMapper());
    }

    @Override
    public User getUserByLogin(String name) {
        return jdbcTemplate.queryForObject("SELECT userid, login, name from USER WHERE login=?",new Object[] {name},new UserMapper());
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
