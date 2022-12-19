package com.example.demo.model.dao.dao;

import com.example.demo.exception.DaoException;
import com.example.demo.model.dao.BaseDao;
import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating User data in table users.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Retrieve user data according to id
     *
     * @param id Long id.
     * @return Optional.of(User) if user with such id exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    @Override
    Optional<User> find(Long id) throws DaoException;


    /**
     * Retrieve all user data
     *
     * @return List of all users.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    List<User> findAll() throws DaoException;


    /**
     * Update existing user data
     *
     * @param user User with updated data.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void update(User user) throws DaoException;


    /**
     * Insert new user.
     *
     * @param user new User.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void insert(User user) throws DaoException;


    /**
     * Delete user
     *
     * @param user User.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void delete(User user) throws DaoException;


    /**
     * Authenticate user
     *
     * @param name User name.
     * @param password User password.
     * @return Optional.of(User) if user with such name and password exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    Optional<User> authenticate(String name, String password) throws DaoException;


    /**
     *
     * @return List of all usernames
     * @throws DaoException  If a database access error occurs.
     */
    List<String> findAllUsernames() throws DaoException;


    /**
     *
     * @return List of all user emails
     * @throws DaoException  If a database access error occurs.
     */
    List<String> findAllUserEmails() throws DaoException;

}
