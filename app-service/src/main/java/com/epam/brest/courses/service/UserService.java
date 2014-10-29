package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

import java.util.List;

/**
 * Created by fieldistor on 24.10.14.
 */
public interface UserService {

    /**
     * The method used for adding the user.
     *
     * @exception java.lang.IllegalArgumentException if
     * the user/user's login/user's name is null
     * or the user's id isn't null
     * or this user is already exist
     *
     * @param user the data(id, login, name) to be added
     */
    public void addUser(User user);

    /**
     * The method returns a list of users that can then be processed.
     * This method always returns immediately, whether or not any users exist.
     *
     * @return a list of users or null
     */
    public List<User> getUsers();

    /**
     * The method used for deleting the user by id.
     *
     * @exception java.lang.IllegalArgumentException if
     * user's id is null or there isn't user with such id
     * @param userId the id of deleting user
     */
    public void removeUser (Long userId);

    /**
     * The method used for getting the user by id.
     * This method always returns immediately, whether or not such user exists.
     *
     * @exception java.lang.IllegalArgumentException if user's id is null
     * @param userId id of getting user
     * @return data of getting user
     */
    public User getUserById(Long userId);

    /**
     * The method used for getting the user by login.
     * This method always returns immediately, whether or not such user exists.
     *
     * @exception java.lang.IllegalArgumentException if user's id is null
     * @param login login of getting user
     * @return data of getting user
     */
    public User getUserByLogin(String login);

    /**
     * The method used for updating user.
     *
     * @exception java.lang.IllegalArgumentException if
     * the user/user's id is null
     * or user's id is zero
     * or such user doesn't exist
     * @param user id, login ,name to update specific user
     */
    public void updateUser(User user);
}
