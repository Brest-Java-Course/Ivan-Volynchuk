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
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    //TODO: ask about ResourceBundleMessageSource
    private static final String INCORRECT_USER_ID = "There isn't user with such userId";
    private static final String NULL_LOGIN = "User login should be not specified.";
    private static final String NULL_NAME = "User name should be not specified.";
    private static final String USER_ID_NULL = "UserId should be not specified.";
    private static final String USER_NULL = "User should be not specified.";
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Long addUser(User user) {
        LOGGER.debug("addUser({})",user);

        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), NULL_LOGIN);
        Assert.notNull(user.getUserName(), NULL_NAME);
        User existingUser=getUserByLogin(user.getLogin());
        if(existingUser!=null){
            throw new IllegalArgumentException("User is already exist");
        }
        Long userid=userDao.addUser(user);
        LOGGER.debug("User id is {}",userid);
        return userid;
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);

        User user=null;
        Assert.notNull(login,NULL_LOGIN);
        try{
            user=userDao.getUserByLogin(login);
        }catch(EmptyResultDataAccessException e){
            LOGGER.error("User with ({}) login doesn't exist",login);

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
            LOGGER.debug("There aren't any items.");
        }
        return usr.size()!=0?usr:null;
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);

        Assert.notNull(userId, USER_ID_NULL);
        if(getUserById(userId)!=null) {
            userDao.removeUser(userId);
        }
        else{
            throw new IllegalArgumentException(INCORRECT_USER_ID);
        }
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);

        User user=null;
        Assert.notNull(userId,USER_ID_NULL);
        try{
            user=userDao.getUserById(userId);
        }catch(EmptyResultDataAccessException e){
            LOGGER.debug("User with ({}) UserId doesn't exist",userId);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Assert.notNull(user, USER_NULL);
        Assert.notNull(user.getUserId(),USER_ID_NULL);
        Assert.isTrue(!user.getUserId().equals(0L));
        if(getUserById(user.getUserId())!=null) {
            userDao.updateUser(user);
        }
        else{
            throw new IllegalArgumentException(INCORRECT_USER_ID);
        }
    }

}
