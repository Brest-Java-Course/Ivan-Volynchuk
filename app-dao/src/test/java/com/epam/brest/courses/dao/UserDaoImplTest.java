package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class UserDaoImplTest {


    private static final String CUSTOM_STRING = "userLogin3";
    private static final String USER_LOGIN_1 = "userLogin1";
    @Autowired
    private UserDao userDao;


    @Test
    public void getUsers() {
        List<User> users = userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    @Rollback(true)
    public void addUser() {
        List<User> users = userDao.getUsers();
        int sizeBefore=users.size();

        User user=new User(3L, CUSTOM_STRING,CUSTOM_STRING);
        userDao.addUser(user);
        users = userDao.getUsers();
        assertEquals(sizeBefore,users.size()-1);
    }
    @Test
    @Rollback(true)
    public void removeUserById(){

        List<User> users = userDao.getUsers();
        int sizeBefore=users.size();

        userDao.removeUser(2L);
        users = userDao.getUsers();
        assertEquals(sizeBefore,users.size()+1);
    }

    @Test
    public void getUserById(){
        User usr=userDao.getUserById(1L);
        assertTrue(usr.getUserId().equals(1L));
    }

    @Test
    public void getUserByLogin(){
        User usr=userDao.getUserByLogin(USER_LOGIN_1);
        assertTrue(usr.getLogin().equals(USER_LOGIN_1));
    }

    @Test
    @Rollback(true)
    public void updateUser(){
        User usr= new User(1L,CUSTOM_STRING,CUSTOM_STRING);
        userDao.updateUser(usr);

        User gUsr=userDao.getUserById(1L);
        assertEquals(usr,gUsr);
    }

}