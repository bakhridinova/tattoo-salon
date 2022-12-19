package com.example.demo.model.dao.dao;

import com.example.demo.exception.DaoException;
import com.example.demo.model.dao.BaseDao;
import com.example.demo.model.entity.Image;

import java.util.List;
import java.util.Optional;

/**girt
 * DAO for manipulating Image data in table images.
 */

public interface ImageDao extends BaseDao<Image> {
    /**
     * Retrieve image data according to id
     *
     * @param id Long id.
     * @return Optional.of(Image) if image with such id exists, otherwise Optional.empty().
     * @throws DaoException If a database access error occurs.
     */
    @Override
    Optional<Image> find(Long id) throws DaoException;


    /**
     * Retrieve all image data
     *
     * @return List of all images.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    List<Image> findAll() throws DaoException;


    /**
     * Update existing image data
     *
     * @param image Image.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void update(Image image) throws DaoException;


    /**
     * Insert new image.
     *
     * @param image Image.
     * @throws DaoException If a database access error occurs.
     */
    @Override
    void insert(Image image) throws DaoException;


    /**
     * Unsupported operation
     */
    @Override
    void delete(Image image) throws DaoException;
}
