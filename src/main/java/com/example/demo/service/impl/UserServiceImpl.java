package com.example.demo.service.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.OrderDao;
import com.example.demo.model.dao.dao.RatingDao;
import com.example.demo.model.dao.dao.UserDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.OrderDaoImpl;
import com.example.demo.model.dao.impl.RatingDaoImpl;
import com.example.demo.model.dao.impl.UserDaoImpl;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.encoder.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class  UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ImageDao imageDao;
    private final RatingDao ratingDao;
    private final PasswordEncoder encoder;

    private UserServiceImpl() {
        userDao = UserDaoImpl.getInstance();
        orderDao = OrderDaoImpl.getInstance();
        imageDao = ImageDaoImpl.getInstance();
        ratingDao = RatingDaoImpl.getInstance();
        encoder = PasswordEncoder.getInstance();
    }
    public UserServiceImpl(UserDao userDao, OrderDao orderDao, ImageDao imageDao, RatingDao ratingDao, PasswordEncoder encoder) {  // for testing purposes
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.imageDao = imageDao;
        this.ratingDao = ratingDao;
        this.encoder = encoder;
    }
    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<User> authenticate(String username, String password) throws ServiceException {
        Optional<User> user;
        try {
            password = encoder.encode(password);
            user = userDao.authenticate(username, password);
        } catch (DaoException | NoSuchAlgorithmException e) {
            logger.warn("failed to authenticate user, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public boolean usernameExists(String username) throws ServiceException {
        boolean exists;
        try {
            List<String> usernames = userDao.findAllUsernames();
            exists = usernames.contains(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return exists;
    }

    @Override
    public Optional<User> create(User user) throws ServiceException {
        Optional<User> optionalUser;
        try {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            userDao.insert(user);
            optionalUser = Optional.of(user);
        } catch (DaoException | NoSuchAlgorithmException e) {
            logger.error("failed to create new user, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalUser;
    }



    @Override
    public void delete(User user) throws ServiceException {
        try {
            userDao.delete(user);
        } catch (DaoException e) {
            logger.error("failed to delete user with id: {}, cause: {}", user.getId(), e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            logger.error("failed to find users, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public Optional<User> edit(User user) throws ServiceException {
        Optional<User> optionalUser;
        try {
            userDao.update(user);
            optionalUser = Optional.of(user);
        } catch (DaoException e) {
            logger.error("failed to create new user, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalUser;
    }
}
