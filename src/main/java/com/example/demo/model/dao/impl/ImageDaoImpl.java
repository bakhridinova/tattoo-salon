package com.example.demo.model.dao.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.model.connection.ConnectionPool;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.enumerator.ImageStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.dao.DataBaseInfo.*;

public class ImageDaoImpl implements ImageDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_IMAGES =
            "select * from images order by created_at;";
    private static final String SELECT_IMAGE_BY_ID =
            "select * from images where id = ?;";
    private static final String FIND_IMAGE_ORDERS_BY_ID =
            "select count(*) as orders from orders where image_id = ? and status = 'completed';";
    private static final String FIND_IMAGE_RATING_BY_ID =
            "select sum(rating) / count(*) as rating from ratings where image_id = ?";
    private static final String INSERT_INTO_IMAGES =
            "insert into images (user_id, status, short_description, long_description, price, url) values (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_IMAGES =
            "update images set user_id = ?, status = ?, short_description = ?, long_description = ?, price = ?, url = ? where url = ?";
    private static final String DELETE_IMAGE =
            "delete from images where url = ?";
    private static final ImageDao instance = new ImageDaoImpl();
    public static ImageDao getInstance() {
        return instance;
    }
    private ImageDaoImpl() {
    }

    @Override
    public Optional<Image> find(Long id) throws DaoException {
        Optional<Image> image = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_IMAGE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                image = Optional.of(getAllImageParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find image by id: {} {}", id, e.getMessage());
            throw new DaoException("failed to find image: ", e);
        }
        return image;
    }

    @Override
    public List<Image> findAll() throws DaoException {
        List<Image> images = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_IMAGES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                images.add(getAllImageParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find images {}", e.getMessage());
            throw new DaoException("failed to find images", e);
        }
        return images;
    }

    @Override
    public void update(Image image) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_IMAGES)) {
            setAllImageParametersToPreparedStatement(preparedStatement, image);
            preparedStatement.setString(7, image.getUrl());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to update image: {}", e.getMessage());
            throw new DaoException("failed to update image: ", e);
        }
    }

    @Override
    public void insert(Image image) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_IMAGES)) {
            setAllImageParametersToPreparedStatement(preparedStatement, image);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to insert new image: {}", e.getMessage());
            throw new DaoException("failed to insert new image: ", e);
        }
    }

    @Override
    public void delete(Image image) throws DaoException {
    }

    private int findNumberOfOrdersById(Long imageId) throws SQLException {
        int orders = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_IMAGE_ORDERS_BY_ID)) {
            preparedStatement.setLong(1, imageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orders = resultSet.getInt(IMAGES_TABLE_ORDERS_COLUMN);
            }
        } catch (SQLException e) {
            logger.error("failed to find image orders, cause: {}", e.getMessage());
            throw new SQLException("failed to find image orders, cause: ", e);
        }
        return orders;
    }

    private double findImageRatingById(Long imageId) throws SQLException {
        double rating = 0.0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_IMAGE_RATING_BY_ID)) {
            preparedStatement.setLong(1, imageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = resultSet.getDouble(IMAGES_TABLE_RATING_COLUMN);
            }
        } catch (SQLException e) {
            logger.error("failed to find image rating, cause: {}", e.getMessage());
            throw new SQLException("failed to find image rating, cause: ", e);
        }
        return rating;
    }

    private Image getAllImageParametersFromResultSet(ResultSet resultSet) throws SQLException {
        return new Image(
                resultSet.getLong(IMAGES_TABLE_PK_COLUMN),
                resultSet.getLong(IMAGES_TABLE_FK_USER_ID_COLUMN),
                ImageStatus.valueOf(resultSet.getString(IMAGES_TABLE_STATUS_COLUMN).toUpperCase()),
                resultSet.getString(IMAGES_TABLE_SHORT_DESCRIPTION_COLUMN),
                resultSet.getString(IMAGES_TABLE_lONG_DESCRIPTION_COLUMN),
                resultSet.getTimestamp(IMAGES_TABLE_CREATED_AT_COLUMN),
                findNumberOfOrdersById(resultSet.getLong(IMAGES_TABLE_PK_COLUMN)),
                findImageRatingById(resultSet.getLong(IMAGES_TABLE_PK_COLUMN)),
                resultSet.getDouble(IMAGES_TABLE_PRICE_COLUMN),
                resultSet.getString(IMAGES_TABLE_URL_COLUMN)
        );
    }

    private static void setAllImageParametersToPreparedStatement(PreparedStatement preparedStatement, Image image) throws SQLException {
        preparedStatement.setLong(1, image.getUserId());
        preparedStatement.setString(2, image.getStatus().toString().toLowerCase());
        preparedStatement.setString(3, image.getShortDescription());
        preparedStatement.setString(4, image.getLongDescription());
        preparedStatement.setDouble(5, image.getPrice());
        preparedStatement.setString(6, image.getUrl());
    }
}
