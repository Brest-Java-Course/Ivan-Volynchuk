package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fieldistor on 24.10.14.
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {

        return null;
    }
}
