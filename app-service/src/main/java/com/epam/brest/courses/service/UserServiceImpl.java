package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

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
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(),"User login should be not specified.");
        Assert.notNull(user.getUserName(),"User name should be not specified.");
        User existingUser=getUserByLogin(user.getLogin());
        if(existingUser!=null){
            throw new IllegalArgumentException("User is already exist");
        }
        userDao.addUser(user);
    }



    @Override
    public User getUserByLogin(String login) {
        User user=null;
        try{
           user=userDao.getUserByLogin(login);
        }catch(EmptyResultDataAccessException e){
            //TODO:add logger
        }
        return user;
    }
}
