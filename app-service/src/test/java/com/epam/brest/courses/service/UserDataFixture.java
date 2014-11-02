package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;
import com.epam.brest.courses.domain.UserImpl;

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
}
