package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

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

    }

    @Override
    public List<User> getUsers() {
     return null;
     // return jdbcTemplate.query("SELECT userid, login, name from USER",null);
    }

    @Override
    public void removeUser(Long userId) {

    }
}
