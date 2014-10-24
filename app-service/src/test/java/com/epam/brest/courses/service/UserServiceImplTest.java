package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testServiceApplicationContextSpring.xml" })
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    @Test(expected = IllegalArgumentException.class)//Ожидается выбрасывание исключения
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception {
        userService.addUser(new User());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception {
        userService.addUser(new User(12L,"",""));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null,"admin","admin"));
        userService.addUser(new User(null,"admin","admin"));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null,"admin","admin"));
        User user=userService.getUserByLogin("admin");
        assertEquals("admin",user.getLogin());
    }
}