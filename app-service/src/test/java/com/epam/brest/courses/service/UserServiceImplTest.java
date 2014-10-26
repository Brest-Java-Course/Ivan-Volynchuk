package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/testServiceApplicationContextSpring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional

public class UserServiceImplTest {

    private static final String customStr = "admin";
    private static final String wrongLogin = "dd";
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    /****
     *
     * Test AddUser
    */
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
        userService.addUser(new User(55L,"",""));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null,customStr,customStr));
        userService.addUser(new User(null, customStr,customStr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithoutLogin() throws Exception {
        userService.addUser(new User(null,null,customStr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithoutName() throws Exception {
        userService.addUser(new User(null,customStr,null));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null,customStr,customStr));
        User user=userService.getUserByLogin(customStr);
        assertTrue(user.getLogin().equals(customStr) && user.getUserName().equals(customStr));
    }
    /****
     *
     * Test getUserByLogin
     */
    @Test
    public void getUserByLogin(){
        User user=userService.getUserByLogin("userLogin2");
        assertEquals(user.getLogin(),"userLogin2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserByNullLogin(){
        User user=userService.getUserByLogin(null);
    }

    @Test
    public void getUserByWrongLogin(){
        User user=userService.getUserByLogin(wrongLogin);
        assertNull(user);
    }

    /****
     *
     * Test getAllUsers
     */
    @Test
    public void getAllUsers(){
        List<User> usr=new LinkedList<User>();
        usr.add(new User(1L,"userLogin1","userName1"));
        usr.add(new User(2L,"userLogin2","userName2"));

        List<User>nUsr=userService.getUsers();
        assertEquals(usr,nUsr);
    }
    @Test
    public void getAllUsers2(){
        List<User> usr=new LinkedList<User>();
        usr.add(new User(1L,"userLogin1","userName1"));
        usr.add(new User(2L,"userLogin2","userName2"));

        userService.addUser(new User(null,customStr,customStr));
        List<User>nUsr=userService.getUsers();
        assertNotEquals(usr, nUsr);
    }

    @Test
    public void getEmptyAllUsers(){
        userService.removeUser(1L);
        userService.removeUser(2L);
        List<User> usr=userService.getUsers();
        assertNull(usr);
    }

    /****
     *
     * Test removeUserById
     */
    @Test
    public void removeUserById(){
        userService.removeUser(2L);
        assertNull(userService.getUserById(2L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByIncorrectId(){
        userService.removeUser(99L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByNullId(){
        userService.removeUser(null);
    }

    /****
     *
     * Test getUserById
     */
    @Test(expected = IllegalArgumentException.class)
    public void getUserByNullId(){
        userService.getUserById(null);
    }
    @Test
    public void getUserById(){
        User us=userService.getUserById(1L);
        assertTrue(us.getUserId().equals(1L));
    }
    @Test
    public void getUserByIncorrectId(){
        User us=userService.getUserById(55L);
        assertNull(us);
    }
    /****
     *
     * Test updateUser
     */
    @Test(expected = IllegalArgumentException.class)
    public void updateNullUser(){
        userService.updateUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNoId(){
        userService.updateUser(new User(null,customStr,customStr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithIncorrectId(){
        userService.updateUser(new User(44L,customStr,customStr));
    }
}