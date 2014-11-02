package com.epam.brest.courses.domain;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class UserTest {
    private User user;

    @Before
    public void setUp() throws Exception {
        user=new UserImpl();
    }
    @Test
    public void testGetUserName() throws Exception {
        final String str="Ivan";
        user.setUserName(str);
        assertEquals(user.getUserName(), str);

    }
    @Test
    public void testGetLogin() throws Exception {
        final String str="Ivan";
        user.setLogin(str);
        assertEquals(user.getLogin(), str);

    }
}