package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
public class UserDaoImplTest {

    public void showUsers(List<User> usr,String msg){
        System.out.println(msg+":");
        for(User us:usr){
            System.out.println(us.getLogin()+" "+us.getUserName()+" "+us.getUserId());
        }
        System.out.println("");
    }
    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers() {
        List<User> users = userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void addUser() {

        List<User> users = userDao.getUsers();
        int sizeBefore=users.size();

        User user=new User();
        user.setLogin("userLogin3");
        user.setUserName("userName3");
        user.setUserId(3L);
        userDao.addUser(user);

        users = userDao.getUsers();
        assertEquals(sizeBefore,users.size()-1);

    }
    @Test
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
        User usr=userDao.getUserByLogin("userLogin1");
        assertTrue(usr.getLogin().equals("userLogin1"));
    }

}