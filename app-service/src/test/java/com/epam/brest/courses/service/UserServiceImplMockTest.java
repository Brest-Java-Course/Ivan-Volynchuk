package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.exception.BadInputData;
import com.epam.brest.courses.domain.exception.NotFoundException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertSame;
/**
 * Created by fieldistor on 27.10.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-services-mock-test.xml" })
public class UserServiceImplMockTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @After
    public void clean(){
        reset(userDao);
    }

    @Test(expected = BadInputData.class)
    public void addUserWithNullLogin() {
        User user= UserDataFixture.getExistUserWithoutLogin(1L);

        replay(userDao);
        userService.addUser(user);
        verify(userDao);
    }

    @Test(expected = BadInputData.class)
    public void getUserByLoginWithoutLogin() {
        User user= UserDataFixture.getExistUserWithoutLogin(1L);

        replay(userDao);
        userService.getUserByLogin(user.getLogin());
        verify(userDao);
    }

    @Test
    public void addUser() {
        User user= UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall().andReturn(new Long(0L));

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
        expectLastCall().andReturn(new Long(0L));

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andReturn(null);
        userDao.addUser(user);
        expectLastCall().andReturn(new Long(0L));



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

    @Test(expected = BadInputData.class)
    public void addUserWithTheSameLogin() {
        User user=UserDataFixture.getNewUser();

        expect(userDao.getUserByLogin(user.getLogin())).andReturn(user);

        replay(userDao);
        userService.addUser(user);

    }

    @Test(expected = NotFoundException.class)
    public void getUserByLogin() {
        User user=UserDataFixture.getNewUser();

        userDao.getUserByLogin(user.getLogin());
        expectLastCall().andThrow(new NotFoundException("User login should be not specified.", user.getLogin()));

        replay(userDao);
        userService.getUserByLogin(user.getLogin());
        verify(userDao);
    }

    @Test
    public void UpdateUser() {
        User user=UserDataFixture.getExistUser(13L);

        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(UserDataFixture.getNewUser());
        userDao.updateUser(user);
        expectLastCall();

        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }

    @Test(expected = NotFoundException.class)
    public void UpdateUser2() {
        User user=UserDataFixture.getExistUser(13L);

        userDao.getUserById(user.getUserId());
        expectLastCall().andReturn(null).andThrow(new NotFoundException("",""));


        replay(userDao);
        userService.updateUser(user);
        verify(userDao);
    }



}
