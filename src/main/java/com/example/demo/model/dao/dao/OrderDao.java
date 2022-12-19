package com.example.demo.model.dao.dao;

import com.example.demo.exception.DaoException;
import com.example.demo.model.dao.BaseDao;
import com.example.demo.model.entity.dto.Order;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating Order data in table orders.
 */
public interface OrderDao extends BaseDao<Order> {
    /**
     * Retrieve order data according to id
     *
     * @param id Long id.
     * @return Optional.of(Order) if order with such id exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    @Override
    Optional<Order> find(Long id) throws DaoException;


    /**
     * Retrieve all order data
     *
     * @return List of all orders.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    List<Order> findAll() throws DaoException;


    /**
     * Update existing order data
     *
     * @param order Order.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void update(Order order) throws DaoException;


    /**
     * Insert new order.
     *
     * @param order Order.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void insert(Order order) throws DaoException;


    /**
     * Unsupported operation
     */
    @Override
    void delete(Order order) throws DaoException;
}
