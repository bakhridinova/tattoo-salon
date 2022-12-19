package com.example.demo.model.dao;

import com.example.demo.exception.DaoException;
import com.example.demo.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Entity> {
    Optional<T> find(Long id) throws DaoException;
    List<T> findAll() throws DaoException;
    void update(T t) throws DaoException;
    void insert(T t) throws DaoException;
    void delete(T t) throws DaoException;
}
