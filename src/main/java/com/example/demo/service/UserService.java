package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void delete(User user) throws ServiceException;
    Optional<User> edit(User user) throws ServiceException;
    Optional<User> create(User user) throws ServiceException;
    boolean usernameExists(String username) throws ServiceException;
    Optional<User> authenticate(String username, String password) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
}
