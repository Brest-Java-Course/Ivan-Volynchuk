package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import junit.framework.Assert;
import org.junit.After;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.easymock.EasyMock.*;
/**
 * Created by fieldistor on 27.10.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/testServiceMockApplicationContextSpring.xml" })
public class UserServiceImplMockTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @After
    public void clean(){
        reset(userDao);
    }
    @Test
    public void addUser() {
        User user= UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall();

        replay(userDao);
        userService.addUser(user);
        verify(userDao);
    }

    @Test
    public void addUser2() {
        User user= UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall();



        replay(userDao);
        userService.addUser(user);
        userService.addUser(user);
        verify(userDao);
    }

    @Test
    public void UserByLogin() {
        User user=UserDataFixture.getExistUser(55L);

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);


        replay(userDao);
        User result=userService.getUserByLogin(user.getLogin());
        verify(userDao);

        assertSame(user, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserWithTheSameLogin() {
        User user=UserDataFixture.getNewUser();


        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);




        replay(userDao);
        userService.addUser(user);

    }

    @Test(expected = NumberFormatException.class)
    public void addUserException() {
        User user=UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andThrow(new NumberFormatException());

        replay(userDao);
        userService.addUser(user);
    }


}
