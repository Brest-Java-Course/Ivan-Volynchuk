package com.epam.brest.courses.dao;
import com.epam.brest.courses.domain.User;
import java.util.List;
/**
 * Created by fieldistor on 20.10.14.
 */
public interface UserDao {

    /**
     * The method used for adding the user to base.
     *
     * @param user the data(id, login, name) to be added
     * @return id of user
     */
    public Long addUser(User user);

    /**
     * The method returns a list of users that can then be processed.
     * This method always returns immediately, whether or not any users exist.
     *
     * @return a list of users
     */
    public List<User> getUsers();

    /**
     * The method used for deleting the user from base by id.
     *
     * @param userId the id of deleting user
     */
    public void removeUser (Long userId);

    /**
     * The method used for getting the user from base by id.
     *
     * @exception org.springframework.dao.EmptyResultDataAccessException If an id is null or 0
     * @param userId id of getting user
     * @return data of getting user
     */
    public User getUserById(Long userId);

    /**
     * The method used for getting the user from base by login.
     * This method always returns immediately, whether or not such user exists.
     *
     * @exception org.springframework.dao.EmptyResultDataAccessException If login is null or incorrect
     * @param login login of getting user
     * @return data of getting user
     */
    public User getUserByLogin(String login);

    /**
     * The method used for updating user.
     *
     * @param user id, login ,name to update the specific user
     */
    public void updateUser(User user);
}
