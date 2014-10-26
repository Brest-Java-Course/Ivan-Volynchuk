package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by fieldistor on 24.10.14.
 */
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LogManager.getLogger();
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        LOGGER.debug("addUser({})",user);

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
        LOGGER.debug("getUserByLogin({})",login);

        User user=null;
        Assert.notNull(login,"User login should be not specified.");
        try{
            user=userDao.getUserByLogin(login);
        }catch(EmptyResultDataAccessException e){
            LOGGER.debug("User with ({}) login doesn't exist",login);

        }
        return user;
    }

    @Override
    public List<User> getUsers(){
        LOGGER.debug("getAllUsers()");

        List<User> usr=null;
        try{
            usr=userDao.getUsers();
        }
        catch(EmptyResultDataAccessException e){
            LOGGER.debug("There are not any items.");
        }
        return usr;
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);

        Assert.notNull(userId,"UserId should be not specified.");
        try {
            userDao.removeUser(userId);
        }catch(Throwable e){
            System.out.println(123456);
        }
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);

        User user=null;
        Assert.notNull(userId,"UserId should be not specified.");
        try{
            user=userDao.getUserById(userId);
        }catch(EmptyResultDataAccessException e){
            LOGGER.debug("User with ({}) UserId doesn't exist",userId);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {

    }

}
