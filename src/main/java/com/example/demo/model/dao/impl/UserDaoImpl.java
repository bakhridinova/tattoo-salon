package com.example.demo.model.dao.impl;

import com.example.demo.model.dao.dao.UserDao;
import com.example.demo.exception.DaoException;
import com.example.demo.model.connection.ConnectionPool;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enumerator.UserRole;
import com.example.demo.model.entity.enumerator.UserStatus;
import com.example.demo.model.entity.enumerator.UserGender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.example.demo.model.dao.DataBaseInfo.*;
import static com.example.demo.model.entity.enumerator.UserStatus.DELETED;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_USER_NAMES =
            "select user_name from users where status <> 'deleted'";
    private static final String SELECT_USER_EMAILS =
            "select email_address from users where status <> 'deleted'";
    private static final String SELECT_USER_BY_ID =
            "select * from users where id = ?;";
    private static final String SELECT_USER_BY_NAME =
            "select * from users where user_name = ?;";
    private static final String SELECT_ALL_USERS =
            "select * from users;";
    private static final String INSERT_INTO_USERS =
            "insert into users (role, status, user_name, password, gender, birth_date, email_address, full_name, contact_number) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_USER =
            "update users set role = ?, status = ?, user_name = ?, password = ?, gender = ?, birth_date = ?, full_name = ?, email_address = ?, contact_number = ? where id = ?;";

    private static final UserDao instance = new UserDaoImpl();
    public static UserDao getInstance() {
        return instance;
    }
    private UserDaoImpl() {
    }

    @Override
    public Optional<User> find(Long id) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(getAllUserParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find user by id: {} : {}", id, e.getMessage());
            throw new DaoException("Failed to find user by id: " + id, e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getAllUserParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find users {}", e.getMessage());
            throw new DaoException("failed to find users", e);
        }
        return users;
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            setAllUserParametersToPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(10, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to update user: {}", e.getMessage());
            throw new DaoException("failed to update user: ", e);
        }
    }



    @Override
    public void insert(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_USERS)) {
            setAllUserParametersToPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to insert new user: {}", e.getMessage());
            throw new DaoException("failed to insert new user: ", e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        Long id = user.getId();
        user.setStatus(DELETED);
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            setAllUserParametersToPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(10, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to delete user: {}",  e.getMessage());
            throw new DaoException("failed to delete user: ", e);
        }

    }

    @Override
    public Optional<User> authenticate(String name, String password) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            String passwordFromDB;
            if (resultSet.next()) {
                passwordFromDB = resultSet.getString(USERS_TABLE_PASSWORD_COLUMN);
                if (password.equals(passwordFromDB) ) {
                    user = Optional.of(getAllUserParametersFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            logger.error("failed to authenticate: {}", e.getMessage());
            throw new DaoException("failed to authenticate: ", e);
        }

        return user;
    }



    @Override
    public List<String> findAllUsernames() throws DaoException {
        List<String> usernames = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_NAMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usernames.add(resultSet.getString(USERS_TABLE_USER_NAME_COLUMN));
            }
        } catch (SQLException e) {
            logger.error("failed to find usernames: {}", e.getMessage());
            throw new DaoException("failed to find usernames: ", e);
        }
        return usernames;
    }

    @Override
    public List<String> findAllUserEmails() throws DaoException {
        List<String> userEmails = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_EMAILS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userEmails.add(resultSet.getString(USERS_TABLE_USER_NAME_COLUMN));
            }
        } catch (SQLException e) {
            logger.error("failed to find user emails: {}", e.getMessage());
            throw new DaoException("failed to find user emails: ", e);
        }
        return userEmails;
    }

    private static User getAllUserParametersFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(USERS_TABLE_PK_COLUMN),
                UserRole.valueOf(resultSet.getString(USERS_TABLE_ROLE_COLUMN).toUpperCase()),
                UserStatus.valueOf(resultSet.getString(USERS_TABLE_STATUS_COLUMN).toUpperCase()),

                resultSet.getString(USERS_TABLE_USER_NAME_COLUMN),
                resultSet.getString(USERS_TABLE_PASSWORD_COLUMN),

                UserGender.valueOf(resultSet.getString(USERS_TABLE_GENDER_COLUMN).toUpperCase()),
                resultSet.getTimestamp(USERS_TABLE_BIRTH_DATE_COLUMN),

                resultSet.getString(USERS_TABLE_FULL_NAME_COLUMN),
                resultSet.getString(USERS_TABLE_EMAIL_ADDRESS_COLUMN),
                resultSet.getString(USERS_TABLE_CONTACT_NUMBER_COLUMN));
    }

    private static void setAllUserParametersToPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getRole().toString().toLowerCase());
        preparedStatement.setString(2, user.getStatus().toString().toLowerCase());

        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getPassword());

        preparedStatement.setString(5, user.getGender().toString().toLowerCase());
        preparedStatement.setTimestamp(6, user.getBirthDate());

        preparedStatement.setString(7, user.getFullName());
        preparedStatement.setString(8, user.getEmailAddress());
        preparedStatement.setString(9, user.getContactNumber());
    }
}
