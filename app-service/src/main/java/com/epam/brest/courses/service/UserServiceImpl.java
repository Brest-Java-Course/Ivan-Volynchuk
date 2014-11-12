package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.exception.BadInputData;
import com.epam.brest.courses.domain.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by fieldistor on 24.10.14.
 */
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    //Constants for Exceptions
    private static final String LOGIN_NOT_NULL = "User login should be specified.";
    private static final String NAME_NOT_NULL = "User name should be specified.";
    private static final String USER_ID_NOT_NULL = "UserId should be specified.";
    private static final String USER_ID_NULL = "UserId should be not specified.";
    private static final String USER_NOT_NULL = "User should be specified.";
    private static final String BAD_DATA = "Bad input user's data. ";
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Long addUser(User user) {
        LOGGER.debug("addUser({})",user);

        try {
            Assert.notNull(user,USER_NOT_NULL);
            Assert.isNull(user.getUserId(),USER_ID_NULL);
            Assert.notNull(user.getLogin(), LOGIN_NOT_NULL);
            Assert.notNull(user.getUserName(), NAME_NOT_NULL);
        }
        catch(IllegalArgumentException e) {
            throw new BadInputData(BAD_DATA+e.getMessage(),LOGGER,"Adding user");
        }

        try {
            User existingUser=getUserByLogin(user.getLogin());
        }catch(NotFoundException e) {
            return userDao.addUser(user);
        }
         throw new BadInputData("User with "+user.getLogin()+" login already exist",LOGGER,"Adding user");


    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({})",login);

        User user=null;
        try{
            Assert.notNull(login,LOGIN_NOT_NULL);
            user=userDao.getUserByLogin(login);
        }catch(EmptyResultDataAccessException e){
            LOGGER.error("User with ({}) login doesn't exist",login);
            throw new NotFoundException("User with "+login+" login doesn't exist",login);
        }catch(IllegalArgumentException e){
            throw new BadInputData(BAD_DATA+e.getMessage(),LOGGER,"Getting user by login");
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
            throw new NotFoundException("No users' data",null);
        }
        return usr;
    }

    @Override
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);

        try {
            Assert.notNull(userId, USER_ID_NOT_NULL);
        }
        catch(IllegalArgumentException e){
            throw new BadInputData(BAD_DATA+e.getMessage(),LOGGER,"Removing user");
        }
        if(getUserById(userId)!=null) {
            userDao.removeUser(userId);
        }
        else{
            LOGGER.debug("No user with {} id",userId);
            throw new NotFoundException("User with "+userId+" userId doesn't exist",userId.toString());
        }
    }

    @Override
    public User getUserById(Long userId) {
        LOGGER.debug("getUserById({})",userId);

        User user=null;
        try{
            Assert.notNull(userId,USER_ID_NOT_NULL);
            user=userDao.getUserById(userId);
        }catch(EmptyResultDataAccessException e){
            LOGGER.debug("User with ({}) UserId doesn't exist",userId);
            throw new NotFoundException("User with "+userId+" userId doesn't exist",userId.toString());
        }catch(IllegalArgumentException e){
            throw new BadInputData(BAD_DATA+e.getMessage(),LOGGER,"Getting user by id");
        }
        return user;
    }

    @Override
    public void updateUser(User user) {

        try {
            Assert.notNull(user, USER_NOT_NULL);
            Assert.notNull(user.getUserId(), USER_ID_NOT_NULL);
            Assert.isTrue(!user.getUserId().equals(0L),USER_ID_NOT_NULL);
        }catch(IllegalArgumentException e) {
            throw new BadInputData(BAD_DATA+e.getMessage(),LOGGER,"Updating user");
        }
        if(getUserById(user.getUserId())!=null) {
            userDao.updateUser(user);
        }
        else{
            LOGGER.debug("No user with {} id",user.getUserId());
            throw new NotFoundException("User with "+user.getUserId()+" userId doesn't exist",user.getUserId().toString());
        }
    }

}
