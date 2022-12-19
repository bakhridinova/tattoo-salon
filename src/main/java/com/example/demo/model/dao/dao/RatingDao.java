package com.example.demo.model.dao.dao;

import com.example.demo.exception.DaoException;
import com.example.demo.model.dao.BaseDao;
import com.example.demo.model.entity.dto.Rating;

import java.util.List;
import java.util.Optional;

/**
 * DAO for manipulating Rating data in table ratings.
 */
public interface RatingDao extends BaseDao<Rating> {
    /**
     * Retrieves rating data according to id
     *
     * @param id Long id.
     * @return Optional.of(Rating) if rating with such id exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    @Override
    Optional<Rating> find(Long id) throws DaoException;


    /**
     * Retrieve all rating data
     *
     * @return List of all ratings.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    List<Rating> findAll() throws DaoException;


    /**
     * Update existing rating data
     *
     * @param rating Rating.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void update(Rating rating) throws DaoException;


    /**
     * Insert new rating.
     *
     * @param rating Rating.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void insert(Rating rating) throws DaoException;


    /**
     * Unsupported operation
     */
    @Override
    void delete(Rating rating) throws DaoException;
}
