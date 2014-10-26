package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import jdk.nashorn.internal.ir.annotations.Ignore;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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
    //TODO: ask about ResourceBundleMessageSource
    private static final String CUSTOM_INCORRECT_STR = "admin";
    private static final String CUSTOM_WRONG_LOGIN = "dd";
    private static final Long CUSTOM_WRONG_ID=55L;
    private static final String USER_LOGIN_2 = "userLogin2";
    private static final String USER_LOGIN_1 = "userLogin1";
    private static final String USER_NAME_1 = "userName1";
    private static final String USER_NAME_2 = "userName2";
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    /****
     *
     * TestCases AddUser
    */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser() throws Exception {
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception {
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception {
        userService.addUser(new User(CUSTOM_WRONG_ID,"",""));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception {
        userService.addUser(new User(null,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
        userService.addUser(new User(null, CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithoutLogin() throws Exception {
        userService.addUser(new User(null,null,CUSTOM_INCORRECT_STR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithoutName() throws Exception {
        userService.addUser(new User(null,CUSTOM_INCORRECT_STR,null));
    }

    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User(null,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
        User user=userService.getUserByLogin(CUSTOM_INCORRECT_STR);
        assertTrue(user.getLogin().equals(CUSTOM_INCORRECT_STR) && user.getUserName().equals(CUSTOM_INCORRECT_STR));
    }
    /****
     *
     * TestCases getUserByLogin
     */
    @Test
    @Rollback(false)
    public void getUserByLogin(){
        User user=userService.getUserByLogin(USER_LOGIN_2);
        assertEquals(user.getLogin(),USER_LOGIN_2);
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback(false)
    public void getUserByNullLogin(){
        User user=userService.getUserByLogin(null);
    }

    @Test
    @Rollback(false)
    public void getUserByWrongLogin(){
        User user=userService.getUserByLogin(CUSTOM_WRONG_LOGIN);
        assertNull(user);
    }

    /****
     *
     * TestCases getAllUsers
     */
    @Test
    @Rollback(false)
    public void getAllUsers(){
        List<User> usr=new LinkedList<User>();
        usr.add(new User(1L,USER_LOGIN_1, USER_NAME_1));
        usr.add(new User(2L,USER_LOGIN_2, USER_NAME_2));

        List<User>nUsr=userService.getUsers();
        assertEquals(usr,nUsr);
    }
    @Test
    public void getAllUsers2(){
        List<User> usr=new LinkedList<User>();
        usr.add(new User(1L,USER_LOGIN_1,USER_NAME_1));
        usr.add(new User(2L,USER_LOGIN_2,USER_NAME_2));

        userService.addUser(new User(null,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
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
     * TestCases removeUserById
     */
    @Test
    public void removeUserById(){
        userService.removeUser(2L);
        assertNull(userService.getUserById(2L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByIncorrectId(){
        userService.removeUser(CUSTOM_WRONG_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeUserByNullId(){
        userService.removeUser(null);
    }

    /****
     *
     * TestCases getUserById
     */
    @Test(expected = IllegalArgumentException.class)
    @Rollback(false)
    public void getUserByNullId(){
        userService.getUserById(null);
    }

    @Test
    @Rollback(false)
    public void getUserById(){
        User us=userService.getUserById(1L);
        assertTrue(us.getUserId().equals(1L));
    }
    @Test
    @Rollback(false)
    public void getUserByIncorrectId(){
        User us=userService.getUserById(CUSTOM_WRONG_ID);
        assertNull(us);
    }
    /****
     *
     * TestCases updateUser
     */
    @Test(expected = IllegalArgumentException.class)
    public void updateNullUser(){
        userService.updateUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithNoId(){
        userService.updateUser(new User(null,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
        userService.updateUser(new User(0L,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserWithIncorrectId(){
        userService.updateUser(new User(CUSTOM_WRONG_ID,CUSTOM_INCORRECT_STR,CUSTOM_INCORRECT_STR));
    }
}