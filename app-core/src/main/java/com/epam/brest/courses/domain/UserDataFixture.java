package com.epam.brest.courses.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fieldistor on 27.10.14.
 */
public class UserDataFixture {

    private static final String LOGIN_FIX = "login";
    private static final String NAME_FIX = "name";

    public static User getNewUser(){
        return new UserImpl(null, LOGIN_FIX, NAME_FIX);
    }

    public static User getExistUser(Long id){
        return new UserImpl(id,LOGIN_FIX,NAME_FIX);
    }

    public static User getExistUserWithoutLogin(Long id) { return new UserImpl(id,null,NAME_FIX);}

    public static User getNewUser(Long id) {
        User user = new UserImpl();
        user.setUserId(id);
        user.setUserName("name" + id);
        user.setLogin("login" + id);
        return user;
    }

    public static List<User> getSampleUserList() {
        List<User> list = new ArrayList(3);
        list.add(UserDataFixture.getNewUser(1L));
        list.add(UserDataFixture.getNewUser(2L));
        list.add(UserDataFixture.getNewUser(3L));
        return list;
    }

    public static User getNewUserWithoutLogin() {
        User user = new UserImpl();
        user.setUserName("name");
        return user;
    }
}
