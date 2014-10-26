package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

import java.util.List;

/**
 * Created by fieldistor on 24.10.14.
 */
public interface UserService {
    public void addUser(User user);
    public List<User> getUsers();
    public void removeUser (Long userId);
    public User getUserById(Long userId);
    public User getUserByLogin(String login);
    public void updateUser(User user);
}
