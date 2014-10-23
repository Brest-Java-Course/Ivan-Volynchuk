package com.epam.brest.courses.dao;
import com.epam.brest.courses.domain.User;
import java.util.List;
/**
 * Created by fieldistor on 20.10.14.
 */
public interface UserDao {
    public void addUser(User user);
    public List<User> getUsers();
    public void removeUserById (Long userId);
    public void getUserById(Long userId);
    public void getUserByName(String name);
}
