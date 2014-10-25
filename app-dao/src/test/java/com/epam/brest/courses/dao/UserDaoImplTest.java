package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testApplicationContextSpring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class UserDaoImplTest {


    public void showUsers(String msg){
        List<User> usr = userDao.getUsers();
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
        showUsers("getUsers() START");
        List<User> users = userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        showUsers("getUsers() END");
    }

    @Test
    public void addUser() {
        showUsers("AddUser() START");
        List<User> users = userDao.getUsers();
        int sizeBefore=users.size();

        User user=new User(3L,"userLogin3","userName3");
        userDao.addUser(user);
        users = userDao.getUsers();
        assertEquals(sizeBefore,users.size()-1);
        showUsers("AddUser() END");
    }
    @Test
    public void removeUserById(){
        showUsers("removeUserByIF() START");

        List<User> users = userDao.getUsers();
        int sizeBefore=users.size();

        userDao.removeUser(2L);
        users = userDao.getUsers();
        assertEquals(sizeBefore,users.size()+1);
        showUsers("removeUserByIF() END");
    }
    @Test
    public void getUserById(){
        showUsers("getUserById() START");
        User usr=userDao.getUserById(1L);
        assertTrue(usr.getUserId().equals(1L));
        showUsers("getUserById() END");
    }

    @Test
    public void getUserByLogin(){
        showUsers("getUserByLogin() START");
        User usr=userDao.getUserByLogin("userLogin1");
        assertTrue(usr.getLogin().equals("userLogin1"));
        showUsers("getUserByLogin() END");
    }

    @Test
    public void updateUser(){
        showUsers("updateUser() START");
        User usr= new User(1L,"newLogin","newName");
        userDao.updateUser(usr);

        User gUsr=userDao.getUserById(1L);
        assertEquals(usr,gUsr);
        showUsers("updateUser() End");
    }

}